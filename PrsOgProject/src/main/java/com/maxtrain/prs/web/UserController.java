package com.maxtrain.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.prs.business.User;
import com.maxtrain.prs.db.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/")
	public JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(userRepo.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@GetMapping("/{id}")
	public JsonResponse getUser(@PathVariable Integer id) {
		if(id==null)return JsonResponse.getInstance("Please enter a valid key for type ID.");
		try {
			Optional<User> user = userRepo.findById(id);
			if(!user.isPresent())return JsonResponse.getInstance("No User found with ID provided.");
			return JsonResponse.getInstance(user.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	private JsonResponse save(User user) {
		try {
			User u = userRepo.save(user);
			return JsonResponse.getInstance(u);
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
		catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PostMapping("/")
	public JsonResponse addUser(@RequestBody User user) {
		try {
			return save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/")
	public JsonResponse updateUser(@RequestBody User user) {
		try {
			Optional<User> u = userRepo.findById(user.getId());
			if(!u.isPresent())return JsonResponse.getInstance("No such User for ID "+user.getId()+".");
			return save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse deleteUser(@PathVariable Integer id) {
		if(id==null)return JsonResponse.getInstance("Please enter a valid key for type ID.");
		try {
			Optional<User> u = userRepo.findById(id);
			if(!u.isPresent())return JsonResponse.getInstance("No such User for ID "+id+".");
			userRepo.deleteById(id);
			return JsonResponse.getInstance(u.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@GetMapping("/authenticate")
	public JsonResponse authenticate(@RequestBody User u) {
		String username = u.getUsername();
		String password = u.getPassword();
		try {
			User u2 = userRepo.findByUsernameAndPassword(username,password);
			if(u2==null)return JsonResponse.getInstance("Username and Password combination not found.");
			return JsonResponse.getInstance(u2);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
