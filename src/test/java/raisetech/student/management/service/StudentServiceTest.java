package raisetech.student.management.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
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

  private  StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること(){
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
  void 受講生検索(){
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
  void 受講生詳細の新規登録(){
    Student student = new Student();
    StudentCourse c1 = new StudentCourse();
    StudentCourse c2 = new StudentCourse();
    StudentDetail detail = new StudentDetail(student, List.of(c1,c2));

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
  void 受講生詳細の更新(){
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
  void 受講生の物理削除_指定IDで削除される(){
    int id = 1;

    sut.deleteStudent(id);

    verify(repository, times(1)).deleteStudent(id);
  }
}