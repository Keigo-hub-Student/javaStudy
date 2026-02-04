
package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;


/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくリポジトリです。
 */
@Mapper
public interface StudentRepository {


  /**
   * 受講生の全件検索を行います。
   * @return 受講生一覧（全件）
   */
  @Select("SELECT id,name,kana_name,nick_name,email,area,age, sex,remark,is_deleted FROM students")
  List<Student> search();

  /**
   * 受講生検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  /**
   * 受講生のコース情報の全件検索を行います。
   * @return 受講生のコース情報（全件）
   */
  @Select("SELECT id, student_id, course_name, " +
      "course_start AS courseStart, " +
      "DATE_ADD(course_start, INTERVAL 3 MONTH) AS courseEnd " +
      "FROM student_courses")
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   * @param studentId 受講生ID
   * @return 受講生IDni紐づく受講生コース情報。
   */
  @Select("SELECT * FROM student_courses WHERE student_id = #{studentId}")
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
  @Insert("INSERT INTO students(name,kana_name,nick_Name,email,area,age,sex,remark,is_Deleted)"
      +"VALUES(#{name},#{kanaName},#{nickName},#{email},#{area},#{age},#{sex},#{remark},false)")
  @Options(useGeneratedKeys = true,keyProperty = "id")
  void registerStudent(Student student);


  /**
   * 受講生を新規登録します。IDに完成ては関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */
  @Insert("INSERT INTO student_courses(student_id,course_Name,course_start,course_end)"
      +"VALUES(#{studentId},#{courseName},#{courseStart},#{courseEnd})")
  @Options(useGeneratedKeys = true,keyProperty = "id")
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  @Update("UPDATE students SET name = #{name},kana_name = #{kanaName},nick_name = #{nickName},"
      + " email = #{email},area = #{area},age = #{age},sex = #{sex},remark = #{remark},is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  @Update("UPDATE student_courses SET course_name =#{courseName} WHERE id = #{id}")
  void updateStudentCourse(StudentCourse studentCourse);

}
