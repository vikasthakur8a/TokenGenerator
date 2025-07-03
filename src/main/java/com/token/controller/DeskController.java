package com.token.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.token.entity.Desk;
import com.token.repository.DeskRepository;

@RestController
@RequestMapping("/desk")
public class DeskController {
	private final DeskRepository deskRepository;

	public DeskController(DeskRepository deskRepository) {

		this.deskRepository = deskRepository;
	}

	@PostMapping("/{deskNo}/toggle")
	public ResponseEntity<String> toggleDeskStatus(@PathVariable String deskNo) {
		Optional<Desk> optionalDesk = deskRepository.findByDeskNo(deskNo);
		if (optionalDesk.isPresent()) {
			Desk desk = optionalDesk.get();
			desk.setStatus(!desk.isStatus());

			deskRepository.save(desk);
			return ResponseEntity.ok("Desk status toggled successfully.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@ExceptionHandler
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
