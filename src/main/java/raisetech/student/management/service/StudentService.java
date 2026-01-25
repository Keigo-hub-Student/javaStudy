package raisetech.student.management.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList(){
    //検索処理
    List<Student> allStudent = repository.search();
    List<Student> in30s = new ArrayList<>();

    //絞り込み検索。年齢が30代の人のみ抽出
    //抽出したリポジトリをコントローラに返す
    for(Student s : allStudent){
      if(s.getAge() >= 30 && s.getAge() < 40){
        in30s.add(s);
      }
    }
    return in30s;
  }

  public List<StudentCourse> searchStudentCourseList(){
    //javaコースのコース情報のみ抽出
    List<StudentCourse> allSC = repository.searchSC();
    List<StudentCourse> javaStu = new ArrayList<>();

    for(StudentCourse s : allSC){
      if (s.getCourseName().equals("JAVA")){
        javaStu.add(s);
      }
    }
    return javaStu;
  }


}
