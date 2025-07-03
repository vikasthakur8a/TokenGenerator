package com.token.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String tokenNo;
	@Column(nullable = false)
	private String deskNo;
	@Column(nullable = false)
	private String customerMobileNumber;
	@Column(nullable = false)
	private String customerPrimaryAccount;
	@Column(nullable = false)
	private int numPersonBefore;
	@Column(nullable = false)
	private LocalDateTime timestamp;
	@Column(nullable = false)
	private String requestStatus;
	public Token() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTokenNo() {
		return tokenNo;
	}
	public void setTokenNo(String tokenNo) {
		this.tokenNo = tokenNo;
	}
	public String getDeskNo() {
		return deskNo;
	}
	public void setDeskNo(String deskNo) {
		this.deskNo = deskNo;
	}
	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}
	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}
	public int getNumPersonBefore() {
		return numPersonBefore;
	}
	public void setNumPersonBefore(int numPersonBefore) {
		this.numPersonBefore = numPersonBefore;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public String getCustomerPrimaryAccount() {
		return customerPrimaryAccount;
	}
	public void setCustomerPrimaryAccount(String customerPrimaryAccount) {
		this.customerPrimaryAccount = customerPrimaryAccount;
	}
	
	

}
