package com.stuffy.web;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stuffy.business.Stuffy;
import com.stuffy.db.StuffyRepository;

@CrossOrigin
@RestController
@RequestMapping("/stuffies")
public class StuffyController {
	@Autowired
	private StuffyRepository stuffyRepo;
	
	@GetMapping("/")
	public Iterable<Stuffy> listStuffies(){
		return stuffyRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Stuffy getStuffy(@PathVariable Integer id) {
		Optional<Stuffy> s = stuffyRepo.findById(id);
		if(!s.isPresent()) {
			return null;
		}
		return s.get();
	}
	
	@PostMapping("/")
	public JsonResponse addStuffy(@RequestBody Stuffy s) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(stuffyRepo.save(s));
		} catch (Exception e) {
			e.printStackTrace();
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PutMapping("/{id}")
	public Stuffy updateStuffy(@RequestBody Stuffy s,@PathVariable int id) {
		return stuffyRepo.save(s);
	}
	
	@DeleteMapping("/{id}")
	public void deleteStuffy(@PathVariable int id) {
		stuffyRepo.deleteById(id);
	}

}
