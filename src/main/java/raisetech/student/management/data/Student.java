
package raisetech.student.management.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonPropertyOrder({"id", "name", "kanaName", "nickName", "email","area","age","sex"})


public class Student{

  private String id;
  private String name;
  private String kanaName;
  private String nickName;
  private String email;
  private String area;
  private int age;
  private String sex;
  private String remark;
  private boolean isDeleted;


}
