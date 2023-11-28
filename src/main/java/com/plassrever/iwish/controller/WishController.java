package com.plassrever.iwish.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plassrever.iwish.exceptions.NotFoundException;

@RestController
@RequestMapping("wish")
public class WishController {
	
	private int counter = 4;
	private List<Map<String, String>> wishes = new ArrayList<Map<String, String>>() {{
		add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First wish"); }});
		add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second wish"); }});
		add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third wish"); }});
	}};
	
	@GetMapping
	public List<Map<String, String>> list() {
		return wishes;
	}
	
	@GetMapping("{id}")
	public Map<String, String> getOne(@PathVariable String id) {
		return getWish(id);
	}
	
	public Map<String, String> getWish(@PathVariable String id) {
		return wishes.stream()
				.filter(wish -> wish.get("id").equals(id))
				.findFirst()
				.orElseThrow(NotFoundException::new);
	}
	
	@PostMapping()
	public Map<String, String> create(@RequestBody Map<String, String> wish) {
		wish.put("id", String.valueOf(counter++));
		wishes.add(wish);
		
		return wish;
	}
	
	@PutMapping("{id}")
	public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> wish) {
		Map<String, String> wishFromDb = getWish(id);
		
		wishFromDb.putAll(wish);
		wishFromDb.put("id", id);
		
		return wishFromDb;
	}
	
	@DeleteMapping("{id}") 
	public void delete(@PathVariable String id) {
		Map<String, String> wish = getWish(id);
		wishes.remove(wish);
	}
	
	
}
