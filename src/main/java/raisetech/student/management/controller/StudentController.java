package raisetech.student.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるコントローラです。
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;



  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。
   * 全権検索を行うので、条件指定は行いません。
   * @return 受講生一覧（全件）
   */
  @Operation(
      summary = "受講生詳細の一覧検索",
      description = "登録された全ての受講生詳細の一覧を検索します。"
  )
  @GetMapping("/students")
  public List<StudentDetail> getStudentList(){
    if (service.searchStudentList().isEmpty()) {
      throw new TestException("例外が発生しました。");
    }
    return service.searchStudentList();
  }


  /**
   * 受講生詳細検索です。
   * IDに紐づく任意の受講生情報を取得します。
   * @param id　受講生ID
   * @return 受講生詳細
   */
  @Operation(
      summary = "受講生詳細検索",
      description = "取得したIDに紐づく受講生の詳細情報を検索します。"
  )
  @GetMapping("/students/{id}")
  public StudentDetail getStudent(
      @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id){
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の新規登録を行います。
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(
      summary = "受講生詳細の新規登録",
      description = "受講生の情報(ID,名前,なまえ,ニックネーム,メールアドレス,地域,年齢,性別,備考)と"
          + "受講生のコース情報(コース名)を新規登録します。"
  )
  @PostMapping("/students")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent (studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }


  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新もここで行います。（論理削除）
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(
      summary = "受講生詳細の更新",
      description = "取得したIDに紐づく受講生の詳細情報を更新します。"
          + "ここで isDeleted を true 論理削除を行うこともできます。"
  )
  @PutMapping("/students")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail){
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }


  /**
   * 受講生の物理削除
   * @param id 削除する受講生のID
   * @return 実行結果
   */
  @Operation(
      summary = "受講生詳細の物理削除",
      description = "取得したIDに紐づく受講生情報を物理削除します。"
          + "受講生のIDに紐づく受講生コース情報も自動で物理削除します。"
  )
  @DeleteMapping("/students/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable int id){
    if (id <= 0){
      throw new TestException("不正なIDです：" + id);
    }
    service.deleteStudent(id);
    return ResponseEntity.noContent().build();
  }
  /*
  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }*/


}
