package com.maxtrain.bootcamp.product;

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
@RequestMapping(path="/products")
public class ProductController {

	@Autowired
	private ProductRepository prodRepo;
	
	@GetMapping()
	public JsonResponse getAll() {
		return JsonResponse.getInstance(prodRepo.findAll());
	}
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable Integer id) {
		try {
			if(id==null)return JsonResponse.getInstance("Parameter ID cannot be null");
			Optional<Product> p	= prodRepo.findById(id);
			if(!p.isPresent()) {
				return JsonResponse.getInstance("Product not found");
			}
			return JsonResponse.getInstance(p.get());
		}catch(Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	private JsonResponse save(Product p) {
		try {
			Product p2 = prodRepo.save(p);
			return JsonResponse.getInstance(p2);
		}catch(IllegalArgumentException e) {
			return JsonResponse.getInstance("Parameter id can't be null!");
		}
		catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PostMapping()
	public JsonResponse add(@RequestBody Product p) {
		try {
			return save(p);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/{id}")
	public JsonResponse update(@RequestBody Product p,@PathVariable int id) {
		try {
			if(id!=p.getId()) return JsonResponse.getInstance("Parameter id does not match!");
			return save(p);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	
	@DeleteMapping("/{id}")
	public JsonResponse delete(@PathVariable Integer id) {
		try {
			if(id==null) return JsonResponse.getInstance("Parameter id cannot be null!");
			Optional<Product> p = prodRepo.findById(id);
			if(!p.isPresent()) {
				return JsonResponse.getInstance("Product not found!");
			}
			prodRepo.deleteById(id);
			return JsonResponse.getInstance(p.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
}
}