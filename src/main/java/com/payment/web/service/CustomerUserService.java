package com.payment.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.web.beans.Customer;
import com.payment.web.beans.CustomerUser;
import com.payment.web.beans.Transaction;
import com.payment.web.repository.CustomerUserRepository;
import com.payment.web.repository.TransactionRepository;

@SuppressWarnings("unused")
@Service
public class CustomerUserService {
	CustomerUser obj = new CustomerUser();
	@Autowired
	private CustomerUserRepository custUserRepository;
	
	public List<CustomerUser> getAllByName(String name) {
		List<CustomerUser> user = new ArrayList<CustomerUser>();
		this.custUserRepository.findAll().forEach(t -> user.add(t));
	        return user.stream().filter(t -> t.getUsername().equals(name))
	                .collect(Collectors.toList());
	   }
	
	public Customer checkUser(CustomerUser c) {
		String uname = c.getUsername();
		String pass = c.getUserpassword();
		try {
			List<CustomerUser> u = this.getAllByName(uname);
			CustomerUser test = u.get(0);
			if (pass.equals(test.getUserpassword())) {
				return test.getCustomerid();
			}
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
		return null;
		
	}

}
