package com.token.controller;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.token.entity.Desk;
import com.token.entity.Token;
import com.token.repository.DeskRepository;
import com.token.repository.TokenRepository;

@RestController
@RequestMapping("/tokens")
public class TokenController {
	private final DeskRepository deskRepository;
	private final TokenRepository tokenRepository;

	public TokenController(DeskRepository deskRepository, TokenRepository tokenRepository) {
		this.deskRepository = deskRepository;
		this.tokenRepository = tokenRepository;
	}

	@PostMapping("/generate")
	public ResponseEntity<?> generateToken(@RequestParam String customerMobileNo) {

		List<Desk> desks = deskRepository.findAll();


		    // Find the desk with the minimum numPersonsBefore

		    Desk deskWithMinPersonsBefore = desks.stream()

		            .min(Comparator.comparingInt(desk ->

		                    tokenRepository.countByDeskNoAndTimestampBefore(desk.getDeskNo(), LocalDateTime.now())))

		            .orElse(null);


		    if (deskWithMinPersonsBefore == null) {

		        return ResponseEntity.status(HttpStatus.NOT_FOUND)

		                .body("No available desks to generate tokens."); // No desks available

		    }


		    // Check the capacity of the selected desk

		    int tokensCount = tokenRepository.countByDeskNoAndTimestampBefore(deskWithMinPersonsBefore.getDeskNo(), LocalDateTime.now());

		    if (tokensCount >= 10) {

		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)

		                .body(null); // Selected desk is already at capacity

		    }
		String primaryAccount = generateUniquePrimaryAccount();
// Generate the token
		Token token = new Token();
		token.setTokenNo(generateTokenNumber());
		token.setDeskNo(deskWithMinPersonsBefore.getDeskNo());
		token.setCustomerMobileNumber(customerMobileNo);
		token.setCustomerPrimaryAccount(primaryAccount);
		token.setNumPersonBefore(tokensCount);
		token.setTimestamp(LocalDateTime.now());
		token.setRequestStatus("In Process");

// Save the token details
		Token savedToken = tokenRepository.save(token);
	
		return ResponseEntity.ok(savedToken);
	}

	private String generateTokenNumber() {
		// Retrieve the last generated token number from the database or any other
		// source
		String lastTokenNumber = tokenRepository.findLastTokenNumber();

		// Extract the numeric part of the last token number
		int lastTokenIndex = Integer.parseInt(lastTokenNumber.substring(1));

		// Increment the token index
		int newTokenIndex = lastTokenIndex + 1;

		// Generate the new token number by concatenating 'T' and the new index
		String newTokenNumber = "T" + newTokenIndex;

		return newTokenNumber;
	}

	private AtomicInteger primaryAccountCounter = new AtomicInteger(1);

	private String generateUniquePrimaryAccount() {
		int accountNumber = primaryAccountCounter.getAndIncrement();
		return String.format("AC-%03d", accountNumber);
	}
	@ExceptionHandler
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
