package com.example.practice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/like")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class LikeController {

	private static final Map<Integer, Integer> likeCountMap = new HashMap<>();

	@PostMapping("/add")
	public Map<String, Object> add(@RequestBody Map<String, Integer> requestBody) {
		Integer articleId = requestBody.get("articleId");
		likeCountMap.merge(articleId, 1, Integer::sum);

		Map<String, Object> response = new HashMap<>();
		response.put("articleId", articleId);
		response.put("likeCount", likeCountMap.get(articleId));
		return response;
	}

	@GetMapping("/list")
	public Map<String, Object> list() {
		Map<String, Object> response = new HashMap<>();
		response.put("likes", likeCountMap);
		return response;
	}

}
