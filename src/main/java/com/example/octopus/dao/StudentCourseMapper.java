package com.example.octopus.dao;

import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.StudentCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/3 2:27 下午
 */
@Mapper
public interface StudentCourseMapper {

	/**
	 * 通过tea_course_id获取对应的course_static_id
	 * 用于后面检查学生是否只选了一类课程下的一门课，即只能选同一course_static_id下的一门course_id
	 */
	@Select("SELECT c.course_static_id FROM course c WHERE c.id=#{teaCourseId}")
	long getStaticId(long teaCourseId);

	/**
	 * 通过course_static_id和stu_id获取实体
	 * 与上一条共同使用，确保学生只能选同一course_static_id下的一门course_id
	 */
	@Select("SELECT sc.* FROM student_course sc, course c " +
			"WHERE #{stuNumber}=stu_number and #{courseStaticId}=c.course_static_id and c.id=sc.course_id")
	StudentCourse getByStaticId(long courseStaticId, long stuNumber);

	/**
	 * 返回所有学生-课程信息
	 *
	 * @return 学生课程 list
	 */
	@Select("SELECT sc.*, c.tea_number, c.tea_name FROM student_course sc, course c WHERE sc.course_id=c.id")
	List<StudentCourse> listStudentCourses();

	/**
	 * 学生选课
	 *
	 * @param studentCourse 学生-课程实体
	 * @return 成功为true，失败为false
	 */
	@Insert("INSERT INTO student_course (stu_number,course_id,apply_time) " +
			"VALUES (#{stuNumber},#{courseId},CURRENT_TIMESTAMP)")
	boolean insertStudentCourse(StudentCourse studentCourse);

	/**
	 * 根据id返回学生选课记录
	 *
	 * @param scId student-course表id
	 * @return student-course实体类
	 */
	@Select("SELECT sc.*, c.tea_number, c.tea_name FROM student_course sc, course c " +
			"WHERE sc.course_id=c.id and sc.id = #{scId}")
	StudentCourse getById(long scId);

	@Select("SELECT sc.*, c.tea_number, c.tea_name FROM student_course sc, course c " +
			"WHERE sc.course_id=c.id and sc.course_id = #{courseId} and sc.stu_number = #{stuNumber}")
	StudentCourse getByStuNumAndCourseId(long stuNumber, long courseId);

	/**
	 * 根据courseId返回对应的student-course list
	 *
	 * @param courseId 课程id
	 * @return student-course list
	 */
	@Select("SELECT sc.*, c.tea_number, c.tea_name FROM student_course sc, course c " +
			"WHERE sc.course_id=c.id and sc.course_id = #{courseId}")
	List<StudentCourse> listByCourseId(long courseId);

	@Select("SELECT sc.*, c.tea_number, c.tea_name FROM student_course sc, course c " +
			"WHERE c.tea_number=#{teaNumber} and c.id=sc.course_id")
	List<StudentCourse> listByTeacherId(long teaNumber);

	@Select("SELECT sc.*, c.tea_number, c.tea_name FROM student_course sc, course c " +
			"WHERE sc.stu_number=#{stuNumber} and c.id=sc.course_id")
	List<StudentCourse> listByStudentId(long stuNumber);

	/**
	 * 根据学生学号返回所有该学生选的课的courseId
	 *
	 * @param stuNumber 学生学号
	 * @return 该学生选的所有课程的courseId list
	 */
	@Select("SELECT course_id FROM student_course WHERE stu_number = #{stuNumber}")
	List<Long> listCourseIdsByStuNumber(long stuNumber);

	/**
	 * 根据courseId返回所有选了该课程的学生的stuNumber
	 *
	 * @param courseId 课程id
	 * @return 所有选了该课程的学生的stuNumber list
	 */
	@Select("SELECT stu_number FROM student_course WHERE course_id = #{courseId}")
	List<Long> listStuNumbersByCourseId(long courseId);

	/**
	 * 根据id删除学生-课程记录
	 *
	 * @param id 记录id
	 * @return 成功为true，失败为false
	 */
	@Delete("DELETE FROM student_course WHERE id = #{id}")
	boolean deleteStudentCourse(long id);

	/**
	 * 根据学号和课程id删除学生-课程记录
	 *
	 * @param stuNumber 学号
	 * @param courseId  课程id
	 * @return 成功为true，失败为false
	 */
	@Delete("DELETE FROM student_course WHERE stu_number = #{stuNumber} AND course_id = #{courseId}")
	boolean deteleStudentCourse(long stuNumber, long courseId);

}
