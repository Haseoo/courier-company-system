package com.github.haseoo.courier.services.ports;

import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyData;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyEditData;


public interface ClientCompanyService {
    ClientCompanyData getById(Long id);

    ClientCompanyData add(ClientCompanyAddData addData);

    ClientCompanyData edit(Long id, ClientCompanyEditData editData);
}
