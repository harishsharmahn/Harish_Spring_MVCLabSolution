package com.greatlearning.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.student.entity.Student;
import com.greatlearning.student.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	// add mapping for "/list"

	@RequestMapping("/list")
	public String listStudents(Model theModel) {

		// get Books from db
		List<Student> thestudent = studentService.findAll();

		// add to the spring model
		theModel.addAttribute("Student", thestudent);

		return "list-Students";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Student theStudent = new Student();

		theModel.addAttribute("Student", theStudent);

		return "Student-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("id") int theId, Model theModel) {

		// get the Book from the service
		Student theStudent = studentService.findById(theId);

		// set Book as a model attribute to pre-populate the form
		theModel.addAttribute("Student", theStudent);

		// send over to our form
		return "Student-form";
	}

	@PostMapping("/save")
	public String saveBook(@RequestParam("id") int id, @RequestParam("name")
	String name,
	@RequestParam("department") String department, @RequestParam("country")
    String country) {
	 
	System.out.println(id);
	Student theStudent;
	if (id != 0) {
	theStudent = studentService.findById(id);
	theStudent.setName(name);
	theStudent.setDepartment(department);
	theStudent.setCountry(country);
	} else
	theStudent = new Student(name, department, country);
	//save theBook
	studentService.save(theStudent);
	
	// use a redirect to prevent duplicate submissions
	return "redirect:/students/list";
	
	 }

	@RequestMapping("/delete")
	public String delete(@RequestParam("Student_Id") int theId) {
	
	//delete the Book
	studentService.deleteById(theId);
	
	//redirect to /Books/list
	return "redirect:/students/list";
	
 
}
	@RequestMapping("/search")
	public String search(@RequestParam("name") String name,
	@RequestParam("Deparment") String Department, Model theModel) {
	
	// // check names, if both are empty then just give list of all Books
	//
	if (name.trim().isEmpty() && Department.trim().isEmpty()) {
	return "redirect:/students/list";
	} else {
	// // else, search by first name and last name
	List<Student> theStudents = studentService.searchBy(name, Department);
	//
	// // add to the spring model
	theModel.addAttribute("Students", theStudents);
	//
	// send to list-Books
	return "list-Students";
	}

	}
}
