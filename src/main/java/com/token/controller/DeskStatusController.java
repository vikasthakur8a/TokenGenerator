package com.token.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.token.entity.Desk;
import com.token.repository.DeskRepository;
import com.token.repository.TokenRepository;
import com.token.util.DeskStatus;

@RestController

@RequestMapping("/api/desk-status")
public class DeskStatusController {

	private final DeskRepository deskRepository;
	private final TokenRepository tokenRepository;

	public DeskStatusController(DeskRepository deskRepository, TokenRepository tokenRepository) {

		this.deskRepository = deskRepository;
		this.tokenRepository = tokenRepository;

	}

	@GetMapping
	public ResponseEntity<List<DeskStatus>> getDeskStatus() {

		List<Desk> desks = deskRepository.findAll();

		List<DeskStatus> deskStatusList = new ArrayList<>();

		for (Desk desk : desks) {

			DeskStatus deskStatus = new DeskStatus();

			deskStatus.setDeskNo(desk.getDeskNo());

			if (!desk.isStatus()) {

				deskStatus.setStatus("OFF");

			} else {

				// Check if the desk is busy handling a customer issue

				int tokensCount = tokenRepository.countByDeskNoAndTimestampBefore(desk.getDeskNo(), LocalDateTime.now());

				if (tokensCount >= 10) {

					deskStatus.setStatus("BUSY");

				} else {

					deskStatus.setStatus("FREE");

				}

			}

			deskStatusList.add(deskStatus);

		}

		return ResponseEntity.ok(deskStatusList);

	}
	@ExceptionHandler
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}