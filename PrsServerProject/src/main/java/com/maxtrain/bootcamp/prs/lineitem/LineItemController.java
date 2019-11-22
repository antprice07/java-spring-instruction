package com.maxtrain.bootcamp.prs.lineitem;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.bootcamp.prs.util.JsonResponse;

@CrossOrigin
@RestController
@RequestMapping("/line-items")
public class LineItemController {
	
	@Autowired
	private LineItemRepository lineRepo;
	
	@GetMapping()
	public JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(lineRepo.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<LineItem> line = lineRepo.findById(id);
			if(!line.isPresent()) return JsonResponse.getInstance("User not found");
			return JsonResponse.getInstance(line);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	private JsonResponse save(LineItem l) {
		try {
			LineItem l2 = lineRepo.save(l);
			return JsonResponse.getInstance(l2);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PostMapping()
	public JsonResponse insert(@RequestBody LineItem l) {
		try {
			return save(l);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping()
	public JsonResponse update(@RequestBody LineItem l) {
		try {
			Optional<LineItem> l2 = lineRepo.findById(l.getId());
			if(l2.isPresent()) return save(l);
			return JsonResponse.getInstance("Parameter ID doesn't match an existing line item");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse delete(@PathVariable Integer id) {
		try {
			Optional<LineItem> l = lineRepo.findById(id);
			if(l.isPresent()) {
				lineRepo.deleteById(id);
			}
			return JsonResponse.getInstance("User does not exist!");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
