package com.payment.web.rest;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.web.beans.Customer;
import com.payment.web.beans.CustomerUser;
import com.payment.web.beans.ResponsePage;
import com.payment.web.beans.Transaction;
import com.payment.web.service.CustomerUserService;
import com.payment.web.service.TransactionService;

@RestController
@CrossOrigin()
@RequestMapping("/user")
public class CustomerUserRestController {
	@Autowired
	private CustomerUserService custUserService;
	
	@PostMapping
	public ResponseEntity<Object> checkUser(@RequestBody CustomerUser user) {
		Customer customer;
		if ((customer = this.custUserService.checkUser(user)) != null) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(customer);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponsePage("Failed","User with "+user.getUsername()+" does not exists"));
		}
	}

}
