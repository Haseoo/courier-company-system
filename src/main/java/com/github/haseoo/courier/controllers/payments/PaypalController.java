package com.github.haseoo.courier.controllers.payments;

import com.github.haseoo.courier.models.ParcelModel;
import com.github.haseoo.courier.services.ports.PaypalService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaypalController {

    private final PaypalService paypalService;


    @PostMapping("/paypal")
    public String payment(
            @RequestBody ParcelModel parcelModel
    ) {
        String approvalLink = null;
        try {
            approvalLink = paypalService.createPayment(parcelModel);
            parcelModel.setPaid(true);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        return approvalLink;
    }


    @GetMapping("/paypal/success")
    public String handlePaypalSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            paypalService.executePayment(paymentId,payerId);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "Success";
    }

    @GetMapping("/paypal/cancel")
    public String handlePaypalCancel(@RequestParam String token){
        return "cancel";
    }


}
