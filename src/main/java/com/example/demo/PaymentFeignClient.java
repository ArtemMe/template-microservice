package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "payment-client", url = "http://localhost:8080/payments")
public interface PaymentFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/")
    List<Payment> getAllTransactions();
}
