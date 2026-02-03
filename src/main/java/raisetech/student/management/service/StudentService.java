package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生情報の検索や登録・更新処理を行います。
 */

@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository,StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }


  /**
   * 受講生一覧検索です。
   * 全権検索を行うので、条件指定は行いません。
   * @return 受講生一覧（全件）
   */

  public List<StudentDetail> searchStudentList(){
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCoursesList = repository.searchSC();
    return converter.convertStudentDetails(studentList, studentCoursesList);
  }


  /**
   * 受講生検索です。
   * IDに紐づく受講生情報を取得した後、その受講生に紐づくコース情報を取得して設定します。
   * @param id　受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String id){
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourse);
  }


  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail){
    repository.registerStudent(studentDetail.getStudent());
    for(StudentCourse studentCourse : studentDetail.getStudentCourse()) {
      studentCourse.setStudentId(studentDetail.getStudent().getId());
      studentCourse.setCourseStart(LocalDateTime.now());
      studentCourse.setCourseEnd(LocalDateTime.now().plusYears(1));
      repository.registerStudentCourse(studentCourse);
    }

    return studentDetail;
  }

  //生徒情報の更新処理
  @Transactional
  public void updateStudent(StudentDetail studentDetail){
    repository.updateStudent(studentDetail.getStudent());
    for(StudentCourse studentCourse : studentDetail.getStudentCourse()) {
      repository.updateStudentCourse(studentCourse);
    }
  }











}
