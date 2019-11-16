package com.maxtrain.bootcamp.request;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxtrain.bootcamp.prs.util.JsonResponse;

@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {

	@Autowired
	private RequestRepository reqRepo;
	
	@GetMapping()
	public JsonResponse getAll() {
		return JsonResponse.getInstance(reqRepo.findAll());
	}
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		Optional<Request> r = reqRepo.findById(id);
		if(!r.isPresent()) {
			return JsonResponse.getInstance("Request does not exist!");
		}
		return JsonResponse.getInstance(r.get());
	}
	private JsonResponse save(Request r) {
		try {
			Request r2 = reqRepo.save(r);
			return JsonResponse.getInstance(r2);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PostMapping()
	public JsonResponse add(@RequestBody Request r) {
		try {
			return save(r);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/{id}")
	public JsonResponse update(@RequestBody Request r,@PathVariable Integer id) {
		try {
			if(id!=r.getId()) return JsonResponse.getInstance("Parameter id does not match!");
			return save(r);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse delete(@PathVariable Integer id) {
		if(id==null) return JsonResponse.getInstance("Parameter id must not be null!");
		Optional<Request> r = reqRepo.findById(id);
		if(!r.isPresent()) return JsonResponse.getInstance("No user found with parameter id!");
		reqRepo.deleteById(id);
		return JsonResponse.getInstance(r);
	}
	
	
}
