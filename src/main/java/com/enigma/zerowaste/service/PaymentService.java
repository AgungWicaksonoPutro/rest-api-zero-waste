package com.enigma.zerowaste.service;

import com.enigma.zerowaste.dto.CustomerSearchDTO;
import com.enigma.zerowaste.dto.order.CallbackDTO;
import com.enigma.zerowaste.dto.PaymentSearchDTO;
import com.enigma.zerowaste.dto.invoice.InvoiceDTO;
import com.enigma.zerowaste.entity.Customer;
import com.enigma.zerowaste.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    public Payment makeInvoice(InvoiceDTO invoiceDTO);
    public void cancelInvoice(String id);
    public Payment getInvoiceById(String id);
    public void updatePayment(CallbackDTO callbackDTO);
    public Payment savePayment(Payment payment);
    public List<Payment> getAllPayment();
    public Payment getPaymentById(String id);
    public Page<Payment> getCustomerPerPage(Pageable pageable, PaymentSearchDTO paymentSearchDTO);

}
