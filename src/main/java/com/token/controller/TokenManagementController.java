package com.token.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.token.entity.Token;
import com.token.repository.TokenRepository;

@RestController

@RequestMapping("/api/token-management")

public class TokenManagementController {

	private final TokenRepository tokenRepository;

	public TokenManagementController(TokenRepository tokenRepository) {

		this.tokenRepository = tokenRepository;

	}
	
	@PutMapping("/{tokenId}")
	public ResponseEntity<String> updateTokenStatus(@PathVariable Long tokenId, @RequestParam String decision) {

		Optional<Token> optionalToken = tokenRepository.findById(tokenId);

		if (optionalToken.isPresent()) {

			Token token = optionalToken.get();

			if (decision.equalsIgnoreCase("accept")) {

				token.setRequestStatus("Accepted");

			} else if (decision.equalsIgnoreCase("reject")) {

				token.setRequestStatus("Rejected");

			} else {

				return ResponseEntity.badRequest().body("Invalid decision. Please specify 'accept' or 'reject'.");

			}

			tokenRepository.save(token);

			return ResponseEntity.ok("Token status updated successfully.");

		} else {

			return ResponseEntity.notFound().build();

		}

	}
	@ExceptionHandler
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}