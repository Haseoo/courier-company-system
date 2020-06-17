package com.github.haseoo.courier.services.ports;

import ch.qos.logback.core.net.server.Client;
import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualEditData;


public interface ClientIndividualService {
    ClientIndividualData getById(Long id);

    ClientIndividualData add(ClientIndividualAddData addData);

    ClientIndividualData edit(Long id, ClientIndividualEditData editData);

    String signUp(ClientIndividualModel clientIndividualModel);
}
