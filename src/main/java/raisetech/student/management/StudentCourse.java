package raisetech.student.management;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"courseId", "studentId", "courseName", "courseStart", "courseEnd"})


public class StudentCourse{

  private String courseId;
  private String studentId;
  private String courseName;
  private String courseStart;
  private String courseEnd;


}
