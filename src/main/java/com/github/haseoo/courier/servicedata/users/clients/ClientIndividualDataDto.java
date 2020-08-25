package com.github.haseoo.courier.servicedata.users.clients;

import com.github.haseoo.courier.models.ClientIndividualModel;
import lombok.Data;

@Data
public class ClientIndividualDataDto extends ClientIndividualModel {
    private String idDto;
    private String name;
    private String email;
}
