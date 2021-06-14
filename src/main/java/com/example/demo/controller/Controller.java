package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepository;

@RestController
public class Controller {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/users")
	public List<User> getUsers(){
		return userRepo.findAll();
	}
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable Long id) throws Exception{
		return userRepo.findById(id).orElseThrow(() -> new Exception("user not existe"));
	}
	
	@PostMapping("/users")
	public User saveUser(@RequestBody User user) {
		return userRepo.save(user);
	}
	
	@PutMapping("/users/{id}")
	public User updateUser(@RequestBody User user, @PathVariable Long id) {
		
		return  userRepo.findById(id).map( usr ->{
			usr.setAge(user.getAge());
			usr.setName(user.getName());
			return userRepo.save(usr);
		}).orElseGet(()->{
			user.setId(id);
			return userRepo.save(user);
		});
		
	}
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable Long id) {
		return userRepo.findById(id).map(usr ->{
			userRepo.delete(usr);
			return "delete successful";
		}).orElseGet(()->{
			return "user not existe";
		});
	
			
		
	}
}
