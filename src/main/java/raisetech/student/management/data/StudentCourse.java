

package raisetech.student.management.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id", "studentId", "courseName", "courseStart", "courseEnd"})

@Schema(description = "受講生コース情報")
public class StudentCourse {

  @Schema(description = "コースID(自動設定)", example = "1", accessMode = AccessMode.READ_ONLY)
  private String id;

  @Schema(description = "受講生ID(自動設定)", example = "1", accessMode = AccessMode.READ_ONLY)
  private String studentId;

  @Schema(description = "受講コース名", example = "Java")
  @NotBlank
  private String courseName;

  @Schema(description = "受講開始日(自動設定)", example = "2026-03-01T00:00:00", accessMode = AccessMode.READ_ONLY)
  private LocalDateTime courseStart;

  @Schema(description = "受講終了日(自動設定)", example = "2027-03-01T00:00:00", accessMode = AccessMode.READ_ONLY)
  private LocalDateTime courseEnd;


}
