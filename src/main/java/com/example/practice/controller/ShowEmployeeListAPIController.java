package com.example.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/showEmployeeListApi")
public class ShowEmployeeListAPIController {

	@GetMapping("")
	public String index() {
		return "practise/show_employeeList";
	}

}
