package raisetech.student.management.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるコントローラです。
 */
@RestController
public class StudentController {

  private StudentService service;



  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生一覧検索です。
   * 全権検索を行うので、条件指定は行いません。
   * @return 受講生一覧（全件）
   */
  @GetMapping("/studentsList")
  public List<StudentDetail> getStudentList(){
    return service.searchStudentList();
  }


  /**
   * 受講生検索です。
   * IDに紐づく任意の受講生情報を取得します。
   * @param id　受講生ID
   * @return
   */
  @GetMapping("student/{id}")
  public StudentDetail getStudent(@PathVariable String id){
    return service.searchStudent(id);
  }

  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent (studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail){
    service.updateStudent(studentDetail);
    //コース情報も一緒に登録できるように実装する。コースは単体でいい。
    return ResponseEntity.ok("更新処理が成功しました。");
  }


}
