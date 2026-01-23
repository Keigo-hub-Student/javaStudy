
package raisetech.student.management;

import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.Student;
import raisetech.student.management.StudentCourse;
import raisetech.student.management.StudentRepository;

@SpringBootApplication
@RestController
@MapperScan("raisetech.student.management")

public class Application {

  @Autowired
  private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

  @GetMapping("/studentsList")
  public List<Student> getStudentList(){

    return repository.search();


  }
  @GetMapping("/studentCoursesList")
  public List<StudentCourse> getStudentCourseList(){
    return repository.searchSC();
  }
}
