package com.example.octopus.repository;

import com.example.octopus.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * @author ：shadow
 * @date ：Created in 2021/5/12 3:17 下午
 * @modified By：
 */
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    Student findById(long id);
}
