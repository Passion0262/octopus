package com.example.octopus.service.impl;

import com.example.octopus.dao.*;
import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.StudentCourse;
import com.example.octopus.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/9 4:15 下午
 */
@Service
public class StudentCourseServiceImpl implements StudentCourseService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	CourseMapper courseMapper;

	@Autowired
	MajorMapper majorMapper;

	@Autowired
	ClassMapper classMapper;

	@Autowired
	StudentCourseMapper studentCourseMapper;

//	@Autowired
//	TeacherCourseMapper teacherCourseMapper;

	@Override
	public List<StudentCourse> listStudentCourses() {
		return studentCourseMapper.listStudentCourses();
	}

	@Override
	public StudentCourse getById(long id) {
		return studentCourseMapper.getById(id);
	}

	@Override
	public List<Long> listCourseIdsByStuNumber(long stuNumber) {
		return studentCourseMapper.listCourseIdsByStuNumber(stuNumber);
	}

	@Override
	public List<StudentCourse> listStudentCoursesByTeaNumber(long teaNumber) {
		return studentCourseMapper.listByTeacherId(teaNumber);
//		ArrayList<StudentCourse> studentCourseList = new ArrayList<>();
//		List<Long> courseIdList = teacherCourseMapper.listCourseIdsByTeaNumber(teaNumber);
//		System.out.println(courseIdList);
//		for (long courseId : courseIdList) {
//			studentCourseList.addAll(studentCourseMapper.listByCourseId(courseId));
//		}
//		return studentCourseList;
	}

	@Override
	public List<StudentCourse> listStudentCoursesByStuNumber(long stuNumber) {
		return studentCourseMapper.listByStudentId(stuNumber);
	}

	@Override
	public boolean isChosen(long stuNumber, long courseId) {
		return studentCourseMapper.getByStuNumAndCourseId(stuNumber, courseId) != null;
	}

	@Override
	public boolean insertStudentCourse(long stuNumber, long courseId) {
		StudentCourse studentCourse = new StudentCourse(stuNumber, courseId);
		return insertStudentCourse(studentCourse);
	}

	@Override
	public boolean insertStudentCourse(StudentCourse studentCourse) {
		// 一个学生只能选择一门静态课程
		StudentCourse sc = studentCourseMapper.getByStaticId(studentCourseMapper.getStaticId(studentCourse.getCourseId()), studentCourse.getStuNumber());
//		if (isChosen(stuNumber, courseId))
		if (sc != null) {
			deleteStudentCourse(studentCourse.getStuNumber(), studentCourse.getCourseId());
		}
		return studentCourseMapper.insertStudentCourse(studentCourse);
	}

	@Override
	public boolean deleteStudentCourse(long stuNumber, long courseId) {
		return studentCourseMapper.deteleStudentCourse(stuNumber, courseId);
	}

	@Override
	public boolean deleteStudentCourse(long studentCourseId) {
		return studentCourseMapper.deleteStudentCourse(studentCourseId);
	}

	@Override
	public boolean insertByClass(String className, long course_id) {
		List<Student> studentList = userMapper.listByClassName(className);
		for (Student s : studentList) {
			StudentCourse studentCourse = new StudentCourse(s.getStuNumber(), course_id);
			studentCourseMapper.insertStudentCourse(studentCourse);
		}
		return true;
	}
}
