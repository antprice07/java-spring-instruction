package com.maxtrain.bootcamp.request;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.maxtrain.bootcamp.prs.util.JsonResponse;

@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {
	
	public static final String REQUEST_STATUS_NEW = "NEW";
	public static final String REQUEST_STATUS_REVIEW = "REVIEW";
	public static final String REQUEST_STATUS_APPROVE = "APPROVED";
	public static final String REQUEST_STATUS_REJECT = "REJECTED";
	public static final String REQUEST_STATUS_EDIT = "EDIT";
	
	@Autowired
	private RequestRepository reqRepo;
	
	@GetMapping()
	public JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(reqRepo.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
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
		}catch(IllegalArgumentException e) {
			return JsonResponse.getInstance(e);
		}
		catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PostMapping()
	public JsonResponse add(@RequestBody Request r) {
		try {
			r.setStatus(REQUEST_STATUS_NEW);
			r.setTotal(0);
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
	
	private JsonResponse setRequestStatus(Request r, String status) {
		try {
			r.setStatus(status);
			return save(r);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/review/{id}")
	public JsonResponse review(@RequestBody Request r, @PathVariable Integer id) {
		try {
			if(id!=r.getId()) return JsonResponse.getInstance("Parameter id does not match!");
			r.setSubmittedDate(new Date(System.currentTimeMillis()));
			if(r.getTotal() <= 50) {
				return setRequestStatus(r,REQUEST_STATUS_APPROVE);
			}
			return setRequestStatus(r, REQUEST_STATUS_REVIEW);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/approve/{id}")
	public JsonResponse approve(@RequestBody Request r,@PathVariable Integer id) {
		try {
			if(id!=r.getId()) return JsonResponse.getInstance("Parameter id doesn't match request entered!");
			return setRequestStatus(r,REQUEST_STATUS_APPROVE);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/reject/{id}")
	public JsonResponse reject(@RequestBody Request r,@PathVariable Integer id) {
		try {
			if(id!=r.getId()) return JsonResponse.getInstance("Parameter id doesn't match request entered!");
			return setRequestStatus(r,REQUEST_STATUS_REJECT);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@GetMapping("/reviews/{id}")
	public JsonResponse getReviews(@PathVariable Integer id) {
		return JsonResponse.getInstance(reqRepo.getRequestByStatusAndUserIdNot(REQUEST_STATUS_REVIEW, id));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
