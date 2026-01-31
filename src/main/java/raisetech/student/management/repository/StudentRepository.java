
package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT id,name,kana_name,nick_name,email,area,age, sex,remark,is_deleted FROM students")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  @Select("SELECT id, student_id, course_name, " +
      "course_start AS courseStart, " +
      "DATE_ADD(course_start, INTERVAL 3 MONTH) AS courseEnd " +
      "FROM student_courses")
  List<StudentCourse> searchSC();

  @Select("SELECT * FROM student_courses WHERE student_id = #{studentId}")
  List<StudentCourse> searchStudentCourse(String id);

  //登録情報をデータベースに反映
  @Insert("INSERT INTO students(name,kana_name,nick_Name,email,area,age,sex,remark,is_Deleted)"
      +"VALUES(#{name},#{kanaName},#{nickName},#{email},#{area},#{age},#{sex},#{remark},false)")
  @Options(useGeneratedKeys = true,keyProperty = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO student_courses(student_id,course_Name,course_start,course_end)"
      +"VALUES(#{studentId},#{courseName},#{courseStart},#{courseEnd})")
  @Options(useGeneratedKeys = true,keyProperty = "id")
  void registerStudentCourse(StudentCourse studentCourse);


  //受講生情報の更新
  @Update("UPDATE students SET name = #{name},kana_name = #{kanaName},nick_name = #{nickName},"
      + " email = #{email},area = #{area},age = #{age},sex = #{sex},remark = #{remark},is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Update("UPDATE student_courses SET course_name =#{courseName} WHERE id = #{id}")
  void updateStudentCourse(StudentCourse studentCourse);

  //生徒情報の論理削除
  /*
  @Update("UPDATE students SET is_deleted = true WHERE id = #{id}")
  void logicalDeleteStudent(String id);*/

  //@Update("UPDATE student_courses SET course_name =#{courseName} WHERE id = #{id}")
  //void logicalDeleteStudentCourse(StudentCourse studentCourse);





}
