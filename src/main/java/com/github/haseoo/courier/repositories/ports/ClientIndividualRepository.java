package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.ClientIndividualModel;

public interface ClientIndividualRepository {
    ClientIndividualModel saveAndFlush(ClientIndividualModel clientCompanyModel);
}
