package com.maxtrain.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.prs.business.Vendor;
import com.maxtrain.prs.db.VendorRepository;

@CrossOrigin
@RestController
@RequestMapping("/vendors")
public class VendorController {
	
	@Autowired
	private VendorRepository venRepo;
	
	@GetMapping("/")
	public JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(venRepo.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@GetMapping("/{id}")
	public JsonResponse getVendor(@PathVariable Integer id) {
		if(id==null)return JsonResponse.getInstance("ID cannot be null");
		try {
			Optional<Vendor> v = venRepo.findById(id);
			if(!v.isPresent())return JsonResponse.getInstance("No Vendor with that ID exists");
			return JsonResponse.getInstance(v.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

}
