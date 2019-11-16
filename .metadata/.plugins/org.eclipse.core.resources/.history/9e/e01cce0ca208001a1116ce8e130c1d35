package com.maxtrain.bootcamp.prs.vendor;

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
		return save(v);
	}
	@PutMapping("/{id}")
	public @ResponseBody JsonResponse update(@RequestBody Vendor v, @PathVariable Integer id) {
		return save(v);
	}
	@DeleteMapping("/{id}")
	public @ResponseBody JsonResponse delete(@PathVariable Integer id) {
		try {
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
