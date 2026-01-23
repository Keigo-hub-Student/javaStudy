package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT id, name, kana_name, nick_name, email, area, age, sex FROM students")
  List<Student> search();



  @Select("SELECT course_id, student_id, course_name, course_start, course_end FROM student_courses")
  List<StudentCourse> searchSC();


}