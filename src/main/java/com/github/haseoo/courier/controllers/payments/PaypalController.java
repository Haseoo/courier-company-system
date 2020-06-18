package com.github.haseoo.courier.controllers.payments;

import com.github.haseoo.courier.services.ports.ChangeParcelStateService;
import com.github.haseoo.courier.services.ports.PaypalService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import static com.github.haseoo.courier.utilities.Constants.FAILURE_REDIRECT;
import static com.github.haseoo.courier.utilities.Constants.SUCCESS_REDIRECT;

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
    public RedirectView  handlePaypalSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, RedirectAttributes attributes) throws PayPalRESTException {
        changeParcelStateService.changeParcelState(paypalService.executePayment(paymentId,payerId));
        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        return new RedirectView(SUCCESS_REDIRECT);
    }

    @GetMapping("/paypal/cancel")
    public RedirectView handlePaypalCancel(@RequestParam String token, RedirectAttributes attributes){
        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        return new RedirectView(FAILURE_REDIRECT);
    }


}
