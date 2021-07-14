package com.example.octopus.service;

import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.StudentCourse;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/26 6:26 下午
 * @modified By：
 */
public interface CourseService {

	/**
	 * 根据课程id查询课程
	 *
	 * @param id 课程id
	 * @return 课程实体
	 */
	Course getCourseById(long id);

	/**
	 *	根据实验id获取课程
	 */
	Long getCourseIdByExperimentId(long experimentId);

	/**
	 * 查询所有的课程
	 *
	 * @return 返回所有课程实体
	 */
	List<Course> listCourses();

	/**
	 * 根据学生学号查询该学生选的课
	 *
	 * @param stuNumber 学号
	 * @return 课程list
	 */
	List<Course> listCoursesByStuNumber(long stuNumber);

	/**
	 *  根据学生id查询已选课程数
	 */
	int countCourseChosen(long stuNumber);

	/**
	 *  获取学生完成的课程的名字
	 */
	List<String> listCompletedCourses(long stuNumber);

	/**
	 * 根据教师编号查询该教师教授的课程list
	 *
	 * @param teaNumber 教师编号
	 * @return courseList
	 */
	List<Course> listCoursesByTeaNumber(long teaNumber);

	/**
	 * 查询选课人数是否已达上限
	 *
	 * @param courseId 课程id
	 * @return 满了为true，没满为false
	 */
	boolean isFull(long courseId);

	/**
	 * 新增课程
	 *
	 * @param course 课程实体
	 * @return 成功为true，失败为false
	 */
	boolean insertCourse(Course course);

	/**
	 * 更新课程
	 *
	 * @param course 课程实体
	 * @return 是否成功更新
	 */
	boolean updateCourse(Course course);

	/**
	 * 根据courseid删除课程
	 *
	 * @param courseid 课程id
	 * @return 成功为true，失败为false
	 */
	boolean deleteCourseById(long courseid);

}
