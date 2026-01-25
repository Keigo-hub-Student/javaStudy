
package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT id,name,kana_name,nick_name,email,area,age, sex FROM students")
  List<Student> search();

  @Select("SELECT course_id, student_id, course_name, " +
      "course_start AS courseStart, " +
      "DATE_ADD(course_start, INTERVAL 3 MONTH) AS courseEnd " +
      "FROM student_courses")
  List<StudentCourse> searchSC();


}
