package com.example.octopus.repository;

import com.example.octopus.entity.user.Class_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/20 14:49 下午
 * @modified By：
 */
public interface ClassRepository extends JpaRepository<Class_, Long>, JpaSpecificationExecutor<Class_> {
//    List<Class_> findAll();
//
//    Class_ findClass_Byid(long classId);
//
//    Class_ findClass_ByClassName(String className);
//
//    void updateClass_Info(Class_ class_);
}
