package com.maxtrain.bootcamp.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public JsonResponse get(@PathVariable int id) {
		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PostMapping()
	public JsonResponse add(Product p) {
		return save(p);
	}
	
	@PutMapping("/{id}")
	public JsonResponse update(Product p,@PathVariable int id) {
		return save(p);
	}
	@DeleteMapping("/{id}")
	public JsonResponse delete(@PathVariable int id) {
		try {
			Optional<Product> p = prodRepo.findById(id);
			if(!p.isPresent()) {
				return JsonResponse.getInstance("Product not found");
			}
			prodRepo.deleteById(id);
			return JsonResponse.getInstance(p.get());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
}
}