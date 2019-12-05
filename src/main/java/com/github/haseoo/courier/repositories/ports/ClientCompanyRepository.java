package com.github.haseoo.courier.repositories.ports;

import com.github.haseoo.courier.models.ClientCompanyModel;

public interface ClientCompanyRepository {
    ClientCompanyModel saveAndFlush(ClientCompanyModel clientCompanyModel);
}
