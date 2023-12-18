package com.cryptovaultdoo.cryptovault.api.controller;

import com.cryptovaultdoo.cryptovault.api.dto.CryptocurrencyPaymentDto;
import com.cryptovaultdoo.cryptovault.api.enumerator.PaymentType;
import com.cryptovaultdoo.cryptovault.services.PaymentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payment")
@Tag(name = "Payment API", description = "Endpoints used to execute payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Operation(summary = "Cryptocurrency payment",
            description = "Performs payment with any cryptocurrency via chosen payment type")
    public void payWithCryptocurrency(@RequestParam PaymentType paymentType,
                                      @Valid @RequestBody CryptocurrencyPaymentDto payment) {
        paymentService.payWithCryptocurrency(paymentType, payment);
    }

}
