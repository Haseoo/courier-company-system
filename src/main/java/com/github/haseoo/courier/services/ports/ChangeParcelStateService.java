package com.github.haseoo.courier.services.ports;

import com.paypal.api.payments.Payment;

public interface ChangeParcelStateService {
    void changeParcelState(Payment payment);
}
