package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.models.ParcelModel;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {

    String createPayment(ParcelModel parcelModel) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
