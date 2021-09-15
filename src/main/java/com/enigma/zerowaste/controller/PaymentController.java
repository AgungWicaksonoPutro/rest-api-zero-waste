package com.enigma.zerowaste.controller;

import com.enigma.zerowaste.constant.ApiUrlConstant;
import com.enigma.zerowaste.dto.order.CallbackDTO;
import com.enigma.zerowaste.dto.PaymentSearchDTO;
import com.enigma.zerowaste.entity.Payment;
import com.enigma.zerowaste.service.PaymentService;
import com.enigma.zerowaste.utils.PageResponseWrapper;
import com.enigma.zerowaste.utils.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrlConstant.PAYMENT)
public class PaymentController {

    @Autowired
    PaymentService service;

    Response<Payment> response = new Response<>();

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{paymentId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public Payment getPaymentById(@PathVariable String paymentId) {
        return service.getPaymentById(paymentId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    public PageResponseWrapper<Payment> getPayment(@RequestParam(name = "status", required = false) String status,
                                                   @RequestParam(name = "paymentMethod", required = false) String paymentMethod,
                                                   @RequestParam(name = "amount", required = false) Double amount,
                                                   @RequestParam(name = "paidAt", required = false) String paidAt,
                                                   @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(name = "size", defaultValue = "3") Integer sizePerPage,
                                                   @RequestParam(name = "sortBy", defaultValue = "customerName") String sortBy,
                                                   @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        PaymentSearchDTO paymentSearchDTO = new PaymentSearchDTO();
        paymentSearchDTO.setSearchStatus(status);
        paymentSearchDTO.setSearchAmount(amount);
        paymentSearchDTO.setSearchPaymentMethod(paymentMethod);
        paymentSearchDTO.setSearchPaidAt(paidAt);
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, sizePerPage, sort);
        Page<Payment> paymentPage = service.getCustomerPerPage(pageable, paymentSearchDTO);
        return new PageResponseWrapper<Payment>(paymentPage);
    }

    @PostMapping("/callback")
    public void receiveCallback(@RequestBody CallbackDTO callbackDTO) {
        service.updatePayment(callbackDTO);
    }
}
