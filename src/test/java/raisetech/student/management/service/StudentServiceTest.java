package raisetech.student.management.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void searchStudentList_正常_リポジトリとコンバーターが呼ばれ一覧が返る() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    List<StudentDetail> expected = new ArrayList<>();

    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    when(converter.convertStudentDetails(studentList, studentCourseList)).thenReturn(expected);

    List<StudentDetail> actual = sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

    Assertions.assertSame(expected, actual);
  }

  @Test
  void searchStudentList_students取得で例外発生_例外が伝播し後続が呼ばれない(){
    when(repository.search()).thenThrow(new RuntimeException("DB error"));

    Assertions.assertThrows(RuntimeException.class, () -> sut.searchStudentList());

    verify(repository, times(1)).search();
    verify(repository, times(0)).searchStudentCourseList();
    verify(converter, times(0)).convertStudentDetails(any(), any());
  }

  @Test
  void searchStudentList_courses取得で例外発生_例外が伝播しconverterが呼ばれない(){
    List<Student> studentList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenThrow(new RuntimeException("DB error"));

    Assertions.assertThrows(RuntimeException.class, () -> sut.searchStudentList());

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(0)).convertStudentDetails(any(), any());
  }

  @Test
  void searchStudentList_converterで例外発生_例外が伝播する(){
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> courseList = new ArrayList<>();

    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(courseList);
    when(converter.convertStudentDetails(studentList, courseList))
        .thenThrow(new RuntimeException("convert error"));

    Assertions.assertThrows(RuntimeException.class, () -> sut.searchStudentList());

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, courseList);
  }




  @Test
  void searchStudent_正常_IDに紐づく受講生詳細が取得できる() {
    String id = "1";
    Student student = new Student();
    student.setId(id);

    List<StudentCourse> studentCourse = new ArrayList<>();

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(studentCourse);

    StudentDetail actual = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(student.getId());

    Assertions.assertSame(student, actual.getStudent());
    Assertions.assertSame(studentCourse, actual.getStudentCourseList());

  }

  @Test
  void searchStudent_存在しないIDでstudentがnull_NullPointerExceptionが発生する() {
    String id = "999";
    when(repository.searchStudent(id)).thenReturn(null);

    assertThrows(NullPointerException.class, () -> sut.searchStudent(id));

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(0)).searchStudentCourse(any());
  }

  @Test
  void searchStudent_student取得で例外発生_例外が伝播しcourse取得が呼ばれない() {
    String id = "1";
    when(repository.searchStudent(id)).thenThrow(new RuntimeException("DB error"));

    assertThrows(RuntimeException.class, () -> sut.searchStudent(id));

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(0)).searchStudentCourse(any());
  }

  @Test
  void earchStudent_course取得で例外発生_例外が伝播する() {
    String id = "1";
    Student student = new Student();
    student.setId(id);

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenThrow(new RuntimeException("DB error"));

    assertThrows(RuntimeException.class, () -> sut.searchStudent(id));

    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourse(id);


  }

  @Test
  void registerStudent_正常_受講生とコースが登録できる() {
    Student student = new Student();
    StudentCourse c1 = new StudentCourse();
    StudentCourse c2 = new StudentCourse();
    StudentDetail detail = new StudentDetail(student, List.of(c1, c2));

    doAnswer(inv -> {
      Student s = inv.getArgument(0);
      s.setId("1");
      return null;
    }).when(repository).registerStudent(any(Student.class));

    StudentDetail actual = sut.registerStudent(detail);

    verify(repository, times(1)).registerStudent(student);

    ArgumentCaptor<StudentCourse> captor = ArgumentCaptor.forClass(StudentCourse.class);
    verify(repository, times(2)).registerStudentCourse(captor.capture());

    List<StudentCourse> called = captor.getAllValues();
    Assertions.assertTrue(called.contains(c1));
    Assertions.assertTrue(called.contains(c2));

    Assertions.assertSame(detail, actual);
  }

  @Test
  void registerStudent_コースリストがnull_NullPointerExceptionが発生する(){
    StudentDetail detail = new StudentDetail(new Student(), null);

    Assertions.assertThrows(NullPointerException.class, () -> sut.registerStudent(detail));

    verify(repository, times(1)).registerStudent(any(Student.class));
    verify(repository, times(0)).registerStudentCourse(any());
  }
  @Test
  void registerStudent_student登録で例外発生_例外が伝播しcourse登録しない(){
    Student student = new Student();
    StudentCourse c1 = new StudentCourse();
    StudentDetail detail = new StudentDetail(student, List.of(c1));

    doAnswer(inv -> { throw new RuntimeException("DB error"); })
        .when(repository).registerStudent(any(Student.class));

    Assertions.assertThrows(RuntimeException.class, () -> sut.registerStudent(detail));

    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(0)).registerStudentCourse(any());
  }
  @Test
  void registerStudent_course登録で例外発生_途中で中断し例外が伝播する(){
    Student student = new Student();
    StudentCourse c1 = new StudentCourse();
    StudentCourse c2 = new StudentCourse();
    StudentDetail detail = new StudentDetail(student, List.of(c1, c2));

    doAnswer(inv -> {
      inv.getArgument(0, Student.class).setId("1");
      return null;
    }).when(repository).registerStudent(any(Student.class));

    doAnswer(inv -> { throw new RuntimeException("insert failed"); })
        .when(repository).registerStudentCourse(any(StudentCourse.class));

    Assertions.assertThrows(RuntimeException.class, () -> sut.registerStudent(detail));

    verify(repository, times(1)).registerStudent(student);
    // forEachの途中で落ちるので最大でも1回しか呼ばれない（1件目で例外になる想定）
    verify(repository, times(1)).registerStudentCourse(any(StudentCourse.class));
  }



  @Test
  void updateStudent_正常_受講生とコースが更新できる() {
    Student student = new Student();
    StudentCourse c1 = new StudentCourse();
    StudentCourse c2 = new StudentCourse();
    StudentDetail detail = new StudentDetail(student, List.of(c1, c2));
    ArgumentCaptor<StudentCourse> captor = ArgumentCaptor.forClass(StudentCourse.class);

    sut.updateStudent(detail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(2)).updateStudentCourse(captor.capture());

    List<StudentCourse> called = captor.getAllValues();
    Assertions.assertTrue(called.contains(c1));
    Assertions.assertTrue(called.contains(c2));

  }
  @Test
  void updateStudent_コースリストがnull_NullPointerExceptionが発生する(){
    Student student = new Student();
    StudentDetail detail = new StudentDetail(student, null);

    Assertions.assertThrows(NullPointerException.class, () -> sut.updateStudent(detail));

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(0)).updateStudentCourse(any());
  }

  @Test
  void updateStudent_student更新で例外発生_例外が伝播しcourse更新しない(){
    Student student = new Student();
    StudentCourse c1 = new StudentCourse();
    StudentDetail detail = new StudentDetail(student, List.of(c1));

    doAnswer(inv -> { throw new RuntimeException("DB error"); })
        .when(repository).updateStudent(any(Student.class));

    Assertions.assertThrows(RuntimeException.class, () -> sut.updateStudent(detail));

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(0)).updateStudentCourse(any());
  }

  @Test
  void updateStudent_course更新で例外発生_途中で中断し例外が伝播する(){
    Student student = new Student();
    StudentCourse c1 = new StudentCourse();
    StudentCourse c2 = new StudentCourse();
    StudentDetail detail = new StudentDetail(student, List.of(c1, c2));

    doAnswer(inv -> { throw new RuntimeException("DB error"); })
        .when(repository).updateStudentCourse(any(StudentCourse.class));

    Assertions.assertThrows(RuntimeException.class, () -> sut.updateStudent(detail));

    verify(repository, times(1)).updateStudent(student);
    // forEachの1件目で例外になる想定なので、最大1回
    verify(repository, times(1)).updateStudentCourse(any(StudentCourse.class));
  }


  @Test
  void deleteStudent_正常_ID指定で削除できる() {
    int id = 1;

    sut.deleteStudent(id);

    verify(repository, times(1)).deleteStudent(id);
  }
  @Test
  void deleteStudent_repositoryで例外発生_例外が伝播する(){
    int id = 1;

    doThrow(new RuntimeException("DB error"))
        .when(repository).deleteStudent(id);

    Assertions.assertThrows(RuntimeException.class, () -> sut.deleteStudent(id));

    verify(repository, times(1)).deleteStudent(id);
  }
  @Test
  void deleteStudent_idが0でもrepositoryが呼ばれる_現状仕様(){
    int id = 0;

    sut.deleteStudent(id);

    verify(repository, times(1)).deleteStudent(id);
  }


}