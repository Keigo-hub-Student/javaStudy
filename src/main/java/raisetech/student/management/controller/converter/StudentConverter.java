package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーターです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に基づく受講生コース情報をマッピングする。
   * 受講生コース情報は、受講生に対して複数存在するので、ループを回して受講生詳細情報を組み立てる。
   * @param studentList 受講生一覧
   * @param studentsCourseList 受講生コース情報のリスト
   * @return 受講生詳細の情報リスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> studentsCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentCourseList = studentsCourseList.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
