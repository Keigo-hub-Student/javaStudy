package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
//import raisetech.student.management.repository.RegisterRepository;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;
  //private RegisterRepository registerRepository;

  @Autowired
  public StudentService(StudentRepository repository/*RegisterRepository registerRepository*/) {
    this.repository = repository;
    //this.registerRepository = registerRepository;
  }

  public List<Student> searchStudentList(){
    //検索処理
    return repository.search();
  }

  public StudentDetail searchStudent(String id){
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourse(studentCourse);
    return studentDetail;
  }

  public List<StudentCourse> searchStudentCourseList(){
    //javaコースのコース情報のみ抽出

    return repository.searchSC();
  }

  //データベースに登録処理
  //コントローラーからの情報の受け取りと分解
  @Transactional
  public void registerStudent(StudentDetail studentDetail){
    repository.registerStudent(studentDetail.getStudent());
    for(StudentCourse studentCourse : studentDetail.getStudentCourse()) {
      studentCourse.setStudentId(studentDetail.getStudent().getId());
      studentCourse.setCourseStart(LocalDateTime.now());
      studentCourse.setCourseEnd(LocalDateTime.now().plusYears(1));
      repository.registerStudentCourse(studentCourse);
    }
  }

  //生徒情報の更新処理
  @Transactional
  public void updateStudent(StudentDetail studentDetail){
    repository.updateStudent(studentDetail.getStudent());
    for(StudentCourse studentCourse : studentDetail.getStudentCourse()) {
      repository.updateStudentCourse(studentCourse);
    }
  }

  /*
  //生徒情報の論理削除
  @Transactional
  public void logicalDeleteStudent(List<String> deleteIds){
    for(String id : deleteIds){
      repository.logicalDeleteStudent(id);
    }


  }*/











}
