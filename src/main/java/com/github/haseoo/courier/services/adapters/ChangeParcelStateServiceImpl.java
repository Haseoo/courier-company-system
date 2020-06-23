package com.github.haseoo.courier.services.adapters;

import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.ParcelNotFound;
import com.github.haseoo.courier.models.ParcelModel;
import com.github.haseoo.courier.repositories.ports.ParcelRepository;
import com.github.haseoo.courier.services.ports.ChangeParcelStateService;
import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeParcelStateServiceImpl implements ChangeParcelStateService {

    private final ParcelRepository parcelRepository;
    private ParcelModel parcelModel;

    @Override
    public void changeParcelState(Payment payment) {
        String parcelId = payment.getTransactions()
                .get(0)
                .getItemList()
                .getItems()
                .get(0)
                .getName();

        parcelModel = parcelRepository.getById(Long.valueOf(parcelId.substring(parcelId.indexOf(' ') + 1)))
                .orElseThrow(() -> new ParcelNotFound(Long.valueOf(parcelId.substring(parcelId.indexOf(' ') + 1))));
        parcelModel.setPaid(true);
        parcelRepository.saveAndFlush(parcelModel);
    }
}
