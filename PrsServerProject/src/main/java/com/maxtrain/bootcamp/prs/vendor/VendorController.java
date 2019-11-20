package com.maxtrain.bootcamp.prs.vendor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.maxtrain.bootcamp.prs.util.JsonResponse;

@CrossOrigin
@RestController
@RequestMapping(path="/vendors")
public class VendorController {

	@Autowired
	private VendorRepository vendRepo;
	
	@GetMapping()
	public @ResponseBody JsonResponse getAll() {
		return JsonResponse.getInstance(vendRepo.findAll());
	}
	
	@GetMapping("/{id}")
	public @ResponseBody JsonResponse get(@PathVariable Integer id) {
		try {
			if(id==null)return JsonResponse.getInstance("Parameter id cannot be null");
			Optional<Vendor> v = vendRepo.findById(id);
			if(!v.isPresent()) {
				return JsonResponse.getInstance("Vendor not found!");
			}
			return JsonResponse.getInstance(v.get());
		}catch(Exception e) {
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	private JsonResponse save(Vendor ven) {
		try {
			Vendor v = vendRepo.save(ven);
			return JsonResponse.getInstance(v);
		}catch(Exception e) {
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	
	@PostMapping()
	public @ResponseBody JsonResponse insert(@RequestBody Vendor v) {
		try {
			return save(v);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	@PutMapping("/{id}")
	public @ResponseBody JsonResponse update(@RequestBody Vendor v, @PathVariable Integer id) {
		try {
			if(id==null) return JsonResponse.getInstance("Parameter id cannot be null");
			return save(v);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	@DeleteMapping("/{id}")
	public @ResponseBody JsonResponse delete(@PathVariable Integer id) {
		try {
			if(id==null) return JsonResponse.getInstance("Parameter id cannot be null");
			Optional<Vendor> v = vendRepo.findById(id);
			if(!v.isPresent()) {
				return JsonResponse.getInstance("Vendor not found!");
			}
			vendRepo.deleteById(id);
			return JsonResponse.getInstance(v.get());
		}catch(Exception e) {
			return JsonResponse.getInstance(e.getMessage());
		}
	}
}
