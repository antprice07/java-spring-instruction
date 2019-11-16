package com.student.demo.db;

import org.springframework.data.repository.CrudRepository;

import com.student.demo.business.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

}
