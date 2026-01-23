
package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import raisetech.student.management.Student;
import raisetech.student.management.StudentCourse;

@Mapper
public interface StudentRepository {

  @Select("SELECT id,name,kana_name,nick_name,email,area,age, sex FROM students")
  List<Student> search();


  //@Select("SELECT course_id, student_id, course_name, course_start, course_end FROM student_courses")
  //List<StudentCourse> searchSC();
  @Select("SELECT course_id, student_id, course_name, DATE_FORMAT(course_start,'%Y-%m-%d %H:%i:%s') AS courseStart, DATE_FORMAT(DATE_ADD(course_start, INTERVAL 3 MONTH),'%Y-%m-%d %H:%i:%s') AS courseEnd FROM student_courses")
  List<StudentCourse> searchSC();


}
