package com.maxtrain.bootcamp.prs.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maxtrain.bootcamp.prs.util.JsonResponse;

@CrossOrigin
@Controller
@RequestMapping(path="/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping()
	public @ResponseBody JsonResponse getAll() {
		return JsonResponse.getInstance(userRepo.findAll());
	}
	
	@GetMapping("/{id}")
	public @ResponseBody JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<User> u = userRepo.findById(id);
			if(!u.isPresent()) {
				return JsonResponse.getInstance("User not found!");
			}
			return JsonResponse.getInstance(u.get());
		}catch(Exception e) {
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	private JsonResponse save(User u) {
		try {
			User newU = userRepo.save(u);
			return JsonResponse.getInstance(newU);
		}catch(Exception e) {
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	@PostMapping()
	public @ResponseBody JsonResponse insert(@RequestBody User u) {
		return save(u);
	}
	@PutMapping("/{id}")
	public @ResponseBody JsonResponse update(@RequestBody User u,@PathVariable Integer id){
		return save(u);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody JsonResponse delete(@PathVariable Integer id) {
		try {
			Optional<User> u = userRepo.findById(id);
			if(!u.isPresent()) {
				return JsonResponse.getInstance("User not found");
			}
			userRepo.deleteById(id);
			return JsonResponse.getInstance(u.get());
		}catch(Exception e) {
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	@GetMapping("/authenticate")
	public @ResponseBody JsonResponse authenticate(@RequestBody User u) {
		String username = u.getUsername();
		String password = u.getPassword();
		try {
			User u2 = userRepo.findByUsernameAndPassword(username, password);
			if(u2==null) {
				return JsonResponse.getInstance("User not found");
			}
			return JsonResponse.getInstance(u2);
		}catch(Exception e) {
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
