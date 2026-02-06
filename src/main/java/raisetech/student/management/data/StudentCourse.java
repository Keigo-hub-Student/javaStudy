

package raisetech.student.management.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"courseId", "studentId", "courseName", "courseStart", "courseEnd"})

@Schema(description = "受講生コース情報")
public class StudentCourse {

  private String id;

  private String studentId;

  @NotBlank
  private String courseName;

  private LocalDateTime courseStart;
  private LocalDateTime courseEnd;


}
