package com.student.demo.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.demo.business.Student;
import com.student.demo.db.StudentRepository;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentRepository studRepo;
	
	@GetMapping()
	public JsonResponse getAll() {
		return JsonResponse.getInstance(studRepo.findAll());
	}

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable Integer id) {
		if(id==null)return JsonResponse.getInstance("Parameter id cannot be null");
		Optional<Student> s = studRepo.findById(id);
		if(!s.isPresent()) return JsonResponse.getInstance("No such student exists");
		return JsonResponse.getInstance(s.get());
	}
}
