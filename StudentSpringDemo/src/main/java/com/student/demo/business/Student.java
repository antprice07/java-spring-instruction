package com.student.demo.business;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=50,nullable=false)
	private String name;
	@Column(length=100,nullable=false)
	private String email;
	@Column(length=12,nullable=false)
	private String phone;
	@Column(columnDefinition="date")
	private LocalDate birthDate;
	

	
	public Student() {}



	public Student(int id, String name, String email, String phone, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.birthDate = birthDate;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public LocalDate getBirthDate() {
		return birthDate;
	}



	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	

}
