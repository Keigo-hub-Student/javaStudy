
package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Schema(description = "受講生情報")
public class Student{

  @Schema(description = "ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
  @Pattern(regexp = "^\\d+$")
  private String id;

  @Schema(description = "名前", example = "田中太郎")
  @NotBlank
  private String name;

  @Schema(description = "なまえ", example = "たなかたろう")
  @NotBlank
  @Pattern(regexp = "^[ぁ-んー]+$")
  private String kanaName;

  @Schema(description = "ニックネーム", example = "タロウ")
  @NotBlank
  private String nickName;

  @Schema(description = "メールアドレス", example = "aaaaaaa@gmail.com")
  @NotBlank
  @Email
  private String email;

  @Schema(description = "地域", example = "東京都渋谷区")
  @NotBlank
  private String area;

  @Schema(description = "年齢", example = "25")
  private Integer age;

  @Schema(description = "性別", example = "男")
  @NotBlank
  private String sex;

  @Schema(description = "備考", example = "特になし")
  private String remark;

  @Schema(description = "論理削除フラグ", example = "false")
  private Boolean isDeleted;


}
