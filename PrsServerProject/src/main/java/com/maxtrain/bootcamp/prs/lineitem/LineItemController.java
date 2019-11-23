package com.maxtrain.bootcamp.prs.lineitem;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.bootcamp.prs.util.JsonResponse;
import com.maxtrain.bootcamp.request.Request;
import com.maxtrain.bootcamp.request.RequestController;
import com.maxtrain.bootcamp.request.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/line-items")
public class LineItemController {

	@Autowired
	private RequestRepository reqRepo;
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
			if (id == null)
				return JsonResponse.getInstance("Parameter id cannot be null");
			Optional<LineItem> line = lineRepo.findById(id);
			if (!line.isPresent())
				return JsonResponse.getInstance("Line item not found");
			return JsonResponse.getInstance(line.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	private JsonResponse save(LineItem l) {
		try {
			LineItem l2 = lineRepo.saveAndFlush(l);
			return JsonResponse.getInstance(l2);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	@PostMapping()
	public JsonResponse insert(@RequestBody LineItem l) {
		try {
			JsonResponse jr = save(l);
			recalcLines(l.getRequest().getId());
			return jr;
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	@PutMapping()
	public JsonResponse update(@RequestBody LineItem l) {
		try {
			Optional<LineItem> l2 = lineRepo.findById(l.getId());
			if (l2.isPresent()) {
				JsonResponse jr = save(l);
				recalcLines(l.getRequest().getId());
				return jr;
			}
			return JsonResponse.getInstance("Parameter ID doesn't match an existing line item");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	@DeleteMapping("/{id}")
	public JsonResponse delete(@PathVariable Integer id) {
		try {
			if (id == null)
				return JsonResponse.getInstance("Parameter id cannot be null");
			Optional<LineItem> l = lineRepo.findById(id);
			if (l.isPresent()) {
				lineRepo.deleteById(id);
				lineRepo.flush();
				recalcLines(l.get().getRequest().getId());
				return JsonResponse.getInstance(l.get());
			}
			return JsonResponse.getInstance("Line item does not exist!");
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
		Iterable<LineItem> lines = lineRepo.getLineItemByRequestId(requestID);
		double total = 0;
		for (LineItem l : lines) {
			double subtotal = (l.getProduct().getPrice() * l.getQuantity());
			total += subtotal;
		}
		Request r = request.get();
		r.setTotal(total);
		r.setStatus(RequestController.REQUEST_STATUS_EDIT);
		reqRepo.save(r);
	}

	@GetMapping("/request/{id}")
	public JsonResponse getByRequest(@PathVariable Integer id) {
		try {
			Iterable<LineItem> lines = lineRepo.getLineItemByRequestId(id);
			return JsonResponse.getInstance(lines);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

}
