package com.maxtrain.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.prs.business.LineItem;
import com.maxtrain.prs.business.Request;
import com.maxtrain.prs.db.LineItemRepository;
import com.maxtrain.prs.db.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/line-items")
public class LineItemController {

	@Autowired
	private LineItemRepository lineRepo;
	@Autowired
	private RequestRepository reqRepo;

	@GetMapping("/")
	public JsonResponse getAllLineItems() {
		try {
			return JsonResponse.getInstance(lineRepo.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	@GetMapping("/{id}")
	public JsonResponse getLineItem(@PathVariable Integer id) {
		if (id == null)
			return JsonResponse.getInstance("Path ID cannot be null.");
		try {
			Optional<LineItem> l = lineRepo.findById(id);
			if (!l.isPresent())
				return JsonResponse.getInstance("No line item match for ID.");
			return JsonResponse.getInstance(l.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	private JsonResponse save(LineItem line) {
		try {
			LineItem l = lineRepo.saveAndFlush(line);
			return JsonResponse.getInstance(l);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	private void recalcLines(int requestID) throws Exception {
		Optional<Request> request = reqRepo.findById(requestID);
		if (!request.isPresent()) {
			throw new Exception("Cannot recalculate total because request id is not found. ID: " + requestID);
		}
		Iterable<LineItem> lines = lineRepo.findLineItemByRequestId(requestID);
		double total = 0;
		for (LineItem l : lines) {
			double subtotal = (l.getProduct().getPrice() * l.getQuantity());
			total += subtotal;
		}
		Request r = request.get();
		r.setTotal(total);
		reqRepo.save(r);
	}

	@PostMapping("/")
	public JsonResponse addLineItem(@RequestBody LineItem l) {
		try {
			recalcLines(l.getRequest().getId());
			return save(l);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	@PutMapping("/")
	public JsonResponse updateLine(@RequestBody LineItem line) {
		try {
			Optional<LineItem> l = lineRepo.findById(line.getId());
			if (!l.isPresent())
				return JsonResponse.getInstance("Path ID doesn't match existing line item.");
			recalcLines(line.getRequest().getId());
			return save(line);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteLine(@PathVariable Integer id) {
		if (id == null)
			return JsonResponse.getInstance("Path ID cannot be null.");
		try {
			Optional<LineItem> l = lineRepo.findById(id);
			if (!l.isPresent())
				return JsonResponse.getInstance("Line item does not exist.");
			lineRepo.deleteById(id);
			lineRepo.flush();
			recalcLines(l.get().getRequest().getId());
			return JsonResponse.getInstance(l.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	@GetMapping("/lines-for-pr/{id}")
	public JsonResponse getByRequest(@PathVariable Integer id) {
		try {
			Iterable<LineItem> lines = lineRepo.findLineItemByRequestId(id);
			return JsonResponse.getInstance(lines);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

}
