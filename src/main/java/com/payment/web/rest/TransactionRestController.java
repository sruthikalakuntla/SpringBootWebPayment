package com.payment.web.rest;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.web.beans.Customer;
import com.payment.web.beans.ResponsePage;
import com.payment.web.beans.Transaction;
import com.payment.web.service.TransactionService;

@RestController
@CrossOrigin()
@RequestMapping("/transaction")
public class TransactionRestController {
	
	@Autowired
	private TransactionService tranService;
	
	@GetMapping
	public List<Transaction> getAllTransactions() {
		return this.tranService.getAllTransactions();
	}
	
	@GetMapping("/{customerid}")
	public List<Transaction> findTransactionById(@PathVariable String customerid)
	{
		return this.tranService.getAllTransactionsById(customerid);
		
	}
	
	
	@PostMapping
	public ResponseEntity<Object> insertTransaction(@RequestBody Transaction transaction) {
		ResponsePage r = new ResponsePage();
		
		if (this.tranService.insertTransaction(transaction,r)) {
			ResponsePage r1 = new ResponsePage();
			r1.setDescription("Success");
			r1.setMessage("Success");
			return ResponseEntity.status(HttpStatus.OK)
					.body(r1);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponsePage("Failed",r.getDescription()));
		}
	}
	

}
