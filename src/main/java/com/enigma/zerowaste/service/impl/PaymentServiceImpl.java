package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.constant.ResponseMessage;
import com.enigma.zerowaste.dto.order.CallbackDTO;
import com.enigma.zerowaste.dto.PaymentSearchDTO;
import com.enigma.zerowaste.dto.invoice.InvoiceDTO;
import com.enigma.zerowaste.entity.Order;
import com.enigma.zerowaste.entity.Payment;
import com.enigma.zerowaste.entity.Product;
import com.enigma.zerowaste.repository.PaymentRepository;
import com.enigma.zerowaste.service.OrderService;
import com.enigma.zerowaste.service.PaymentService;
import com.enigma.zerowaste.specification.PaymentSpecification;
import com.enigma.zerowaste.specification.ProductSpecification;
import com.xendit.Xendit;
import com.xendit.exception.XenditException;
import com.xendit.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository repository;

    @Autowired
    OrderService orderService;

    @Value("${zerowaste.app.paymentKey}")
    private String apiKey;

    public void paymentValidation(String id) {
        if (repository.existsById(id)) {
            String message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "payment", id);
        }
    }


    @Override
    public Payment makeInvoice(InvoiceDTO invoiceDTO) {
        Xendit.apiKey = apiKey;
        Payment payment = new Payment();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("external_id", "zero-waste-" + invoiceDTO.getExternalId());
            params.put("amount", invoiceDTO.getAmount());
            params.put("payer_email", invoiceDTO.getPayerEmail());
            params.put("description", invoiceDTO.getDescriptionInvoice());
            params.put("should_send_email", true);
            Invoice invoice = Invoice.create(params);
            //class invoice diubah menjadi entity payment sekaligus disave ke database
            payment = repository.save(recordPayment(invoice));
        } catch (XenditException e) {
            e.printStackTrace();
        }
        return payment;
    }

    //payment ada entity // karena dari xendit yang kita terima itu invoice jadi di casting ke payment entity
    //casting dari invoce ke payment entity
    //save ke repository payment utk save ke database
    public Payment recordPayment(Invoice invoice){
        Payment payment = new Payment();
        payment.setId(invoice.getId());
        payment.setExternalId(invoice.getExternalId());
        payment.setUserId(invoice.getUserId());
        payment.setStatus(invoice.getStatus());
        payment.setMerchantName(invoice.getMerchantName());
        payment.setMerchantProfilePictureUrl(invoice.getMerchantProfilePictureUrl());
        payment.setAmount(invoice.getAmount().doubleValue());
        payment.setBankCode(invoice.getBankCode());
        payment.setPaidAt(invoice.getPaidAt());
        payment.setPayerEmail(invoice.getPayerEmail());
        payment.setDescription(invoice.getDescription());
        payment.setExpiryDate(invoice.getExpiryDate());
        payment.setInvoiceUrl(invoice.getInvoiceUrl());
        payment.setShouldExcludeCreditCard(invoice.getShouldExcludeCreditCard());
        payment.setShouldSendEmail(invoice.getShouldSendEmail());
        payment.setCreated(invoice.getCreated());
        payment.setUpdated(invoice.getUpdated());
        payment.setCurrency(invoice.getCurrency());
        payment.setPaymentChannel(invoice.getPaymentChannel());
        payment.setPaymentDestination(invoice.getPaymentDestination());
        return payment;
    }

    //nanti buat batalin order kalau user mau batalin order
    @Override
    public void cancelInvoice(String id) {
        Xendit.apiKey = apiKey;
        Payment payment = new Payment();
        try {
            Invoice invoice = Invoice.expire(id);
            repository.save(recordPayment(invoice));
        } catch (XenditException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Payment getInvoiceById(String id) {
        return null;
    }

    @Override
    public void updatePayment(CallbackDTO callbackDTO) {
        Payment payment = repository.findById(callbackDTO.getId()).get();
        payment.setAdjustedReceivedAmount(callbackDTO.getAdjusted_received_amount());
        payment.setBankCode(callbackDTO.getBank_code());
        payment.setPaidAmount(callbackDTO.getPaid_amount());
        payment.setPaidAt(callbackDTO.getPaid_at());
        payment.setPaymentMethod(callbackDTO.getPayment_method());
        payment.setPaymentChannel(callbackDTO.getPayment_channel());
        payment.setPaymentDestination(callbackDTO.getPayment_destination());
        payment.setFeesPaidAmount(callbackDTO.getFees_paid_amount());
        payment.setUpdated(callbackDTO.getUpdated());
        payment.setStatus(callbackDTO.getStatus());
        //ketika custome bayar kita update status order, payment -> paid
        //customer jg diubah status order paid, kalau order udah dibayar -> callback xendit
        Order order = orderService.getOrderById(payment.getExternalId());
        order.setStatus(callbackDTO.getStatus());
        orderService.updateOrder(order);
        repository.save(payment);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return repository.save(payment);
    }

    @Override
    public List<Payment> getAllPayment() {
        return repository.findAll();
    }

    @Override
    public Payment getPaymentById(String id) {
        Xendit.apiKey = apiKey;
        Payment payment = new Payment();
        try {
            Invoice invoice = Invoice.getById(id);
            payment.setId(invoice.getId());
            payment.setExternalId(invoice.getExternalId());
            payment.setUserId(invoice.getUserId());
            payment.setStatus(invoice.getStatus());
            payment.setMerchantName(invoice.getMerchantName());
            payment.setMerchantProfilePictureUrl(invoice.getMerchantProfilePictureUrl());
            payment.setAmount(invoice.getAmount().doubleValue());
            payment.setBankCode(invoice.getBankCode());
            System.out.println(invoice.getBankCode());
            payment.setPaidAt(invoice.getPaidAt());
            payment.setPayerEmail(invoice.getPayerEmail());
            payment.setDescription(invoice.getDescription());
            payment.setExpiryDate(invoice.getExpiryDate());
            payment.setInvoiceUrl(invoice.getInvoiceUrl());
            payment.setShouldExcludeCreditCard(invoice.getShouldExcludeCreditCard());
            payment.setShouldSendEmail(invoice.getShouldSendEmail());
            payment.setCreated(invoice.getCreated());
            payment.setUpdated(invoice.getUpdated());
            payment.setCurrency(invoice.getCurrency());
            payment.setPaymentChannel(invoice.getPaymentChannel());
            payment.setPaymentDestination(invoice.getPaymentDestination());
            payment.setPaidAmount(invoice.getPaidAmount().doubleValue());
            payment.setAdjustedReceivedAmount(invoice.getAdjustedReceivedAmount().doubleValue());
            payment.setPaymentMethod(invoice.getPaymentMethod());
            payment.setPaymentDestination(invoice.getPaymentDestination());
        } catch (XenditException e) {
            e.printStackTrace();
        }
        return payment;
    }

    @Override
    public Page<Payment> getCustomerPerPage(Pageable pageable, PaymentSearchDTO paymentSearchDTO) {
        Specification<Payment> paymentSpecification = PaymentSpecification.getSpecification(paymentSearchDTO);
        return repository.findAll(paymentSpecification, pageable);
    }
}
