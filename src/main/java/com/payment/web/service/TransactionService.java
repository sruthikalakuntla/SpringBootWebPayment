package com.payment.web.service;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.payment.web.beans.Currency;
import com.payment.web.beans.Customer;
import com.payment.web.beans.Message;
import com.payment.web.beans.ResponsePage;
import com.payment.web.beans.Transaction;
import com.payment.web.beans.Transfertypes;
import com.payment.web.repository.TransactionRepository;
@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private CustomerService custService;
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private TransfertypesService transfertypesService;
	@Autowired
	private MessageService messageService;
	
	public List<Transaction> getAllTransactions() {
		List<Transaction> t = new ArrayList<Transaction>();
		t = (List<Transaction>) this.transactionRepository.findAll();
		return t;
	}
	
	public List<Transaction> getAllTransactionsById(String id) {
		List<Transaction> transac = new ArrayList<Transaction>();
		this.transactionRepository.findAll().forEach(t -> transac.add(t));
	        return transac.stream().filter(t -> t.getCustomerid().getCustomerid().equals(id))
	                .collect(Collectors.toList());
	        //return this.transactionRepository.findAllByCustomerId(id);
	       // return transac;
	   }
	
	
	
	public boolean insertTransaction(Transaction transaction, ResponsePage r) {
        if (this.transactionRepository.findById(transaction.getTransactionid()).isPresent())
            return false;
        else {
            SdnChecker sc=new SdnChecker();
            try {
                if(sc.checker(transaction.getReceiveraccountholdername())){
                    Customer c = transaction.getCustomerid();
                    Customer c1 = custService.findCustomerById(c.getCustomerid());
                    
                    if (!custService.checkReceiverData(transaction)) {
                    	System.out.println("Incorrect Receiver Details!Please Check!!");
                    	r.setDescription("Incorrect Receiver Details");
                    	return false;
                    	}
                 
                    
                    Message m = transaction.getMessagecode();
                    Message m1 = messageService.getMessageByCode(m.getMessagecode());
                    
                    Currency currency = currencyService.getCurrencyByCode("INR");
                  
                    Transfertypes type = transfertypesService.getTransferTypesByCode(c1.getCustomertype());
                    
                    String receiverAccID = transaction.getReceiveraccountholdernumber();
                    Customer receiver = this.custService.findCustomerById(receiverAccID);
                   
                    double amount = transaction.getInramount() ;
                    double fee = transaction.getInramount() * 0.025;
                    if (custService.sendMoney(c1, receiver, amount, fee)) {
              
                        transaction.setTransferfees(transaction.getInramount()*0.025);
                        transaction.setTransferdate(LocalDateTime.now());
                        transaction.setSenderBIC(c1.getcustomerBIC());
                        transaction.setCurrencycode(currency);
                        transaction.setCurrencyamount(currency.getConversionrate());
                        transaction.setTransfertypecode(type);
                        transaction.setCustomerid(c1);
                        transaction.setMessagecode(m1);
                        this.transactionRepository.save(transaction);
                        return true;
                    } else {
                    	r.setDescription("Check your balance");
                        return false;
                    }
                }
                else {
                    System.out.println("RECEIVER *"+(transaction.getReceiveraccountholdername()).toUpperCase()+
                    "* IS PRESENT IN SPECIALLY DESIGNATED NATIONALS AND BLOCKEDPERSONS (SDN list)");
                    r.setDescription("RECEIVER *" + (transaction.getReceiveraccountholdername()).toUpperCase() + 
                    		"* IS PRESENT IN SPECIALLY DESIGNATED NATIONALS AND BLOCKEDPERSONS (SDN list)" );
                    return false;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
            	r.setDescription("Sender does not exist");
                e.printStackTrace();
            }
            return false;
        }
    }
	
		
}
	