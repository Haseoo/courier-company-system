package com.github.haseoo.courier.controllers.payments;

import com.github.haseoo.courier.services.ports.ChangeParcelStateService;
import com.github.haseoo.courier.services.ports.PaypalService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaypalController {

    private final PaypalService paypalService;
    private final ChangeParcelStateService changeParcelStateService;


    @PostMapping("/paypal")
    public String payment(@RequestParam("id") Long id) throws PayPalRESTException {
        String approvalLink = paypalService.createPayment(id);
        return approvalLink;
    }


    @GetMapping("/paypal/success")
    public String handlePaypalSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws PayPalRESTException {
        changeParcelStateService.changeParcelState(paypalService.executePayment(paymentId,payerId));
        return "Success";
    }

    @GetMapping("/paypal/cancel")
    public String handlePaypalCancel(@RequestParam String token){
        return "cancel";
    }


}
