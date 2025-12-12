package raisetech.student.management;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("raisetech.student.management")
public class Application {

  @Autowired
  private StudentRepository repository;

  private String name = "Enamikouzi";
  private String age ="37";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

  @GetMapping("/studentInfo")
  public String getStudentInfo(){
    Student student = repository.searchByName("EndoHaru");
    return student.getName() + " " + student.getAge() + "歳";
  }
  @PostMapping("/studentInfo")
  public void  studentInfo(String name,String age){
    this.name = name;
    this.age = age;
  }

}
