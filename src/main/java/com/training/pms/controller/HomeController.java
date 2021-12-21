package com.training.pms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping
	public String home()
	{
		return "Welcome to Revature JWA Training";
	}
	
	@GetMapping("message")
	public String message()
	{
		return "-- Revature Training App --";
	}
}
