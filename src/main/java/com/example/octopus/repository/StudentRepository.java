package com.example.octopus.repository;

import com.example.octopus.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author ：shadow
 * @date ：Created in 2021/5/12 3:17 下午
 * @modified By：
 */
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    @Query(value = "select * from student", nativeQuery = true)
    List<Student> findAll();

    Student findStudentByStuNumber(String stuNumber);

    @Query(value = "select * from student where stuNumber = :stuNumber and password = :password", nativeQuery = true)
    Student findStudentByStuNumberAndPassword(@Param("stuNumber")String stuNumber, @Param("password") String password);

    @Modifying
    @Query(value = "update student set loginNumber = loginNumber+1, lastLoginTime = CURRENT_TIMESTAMP where stuNumber = :stuNumber", nativeQuery = true)
    void updateLoginInfo(@Param("stuNumber") String stuNumber);

}
