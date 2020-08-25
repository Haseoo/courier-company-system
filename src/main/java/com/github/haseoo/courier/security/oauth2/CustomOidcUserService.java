package com.github.haseoo.courier.security.oauth2;

import com.github.haseoo.courier.enums.ClientType;
import com.github.haseoo.courier.models.ClientIndividualModel;
import com.github.haseoo.courier.repositories.jpa.ClientIndividualJPARepository;
import com.github.haseoo.courier.repositories.ports.ClientIndividualRepository;
import com.github.haseoo.courier.security.RandomText;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final ClientIndividualRepository clientIndividualRepository;

    private final ClientIndividualJPARepository clientIndividualJPARepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        ClientIndividualDataDto clientIndividualDataDto = new ClientIndividualDataDto();
        clientIndividualDataDto.setEmailAddress((String) attributes.get("email"));
        clientIndividualDataDto.setIdDto((String) attributes.get("sub"));
        clientIndividualDataDto.setImageUrl((String) attributes.get("picture"));
        clientIndividualDataDto.setName((String) attributes.get("given_name"));
        clientIndividualDataDto.setSurname((String) attributes.get("family_name"));
        updateUser(clientIndividualDataDto);
        return oidcUser;
    }


    private void updateUser(ClientIndividualDataDto clientIndividualDataDto) {
        ClientIndividualModel clientIndividualModel = clientIndividualRepository.getByEmailAddress(clientIndividualDataDto.getEmailAddress());
        if (clientIndividualModel == null) {
            clientIndividualModel = new ClientIndividualModel();
        }

        clientIndividualModel.setEmailAddress(clientIndividualDataDto.getEmailAddress());
        clientIndividualModel.setUserName(clientIndividualDataDto.getEmailAddress() + RandomText.uniqueUsernamePostfix());
        clientIndividualModel.setName(clientIndividualDataDto.getName());
        clientIndividualModel.setSurname(clientIndividualDataDto.getSurname());
        clientIndividualModel.setClientType(ClientType.INDIVIDUAL);
        clientIndividualModel.setImageUrl(clientIndividualDataDto.getImageUrl());
        clientIndividualModel.setActive(true);
        clientIndividualJPARepository.save(clientIndividualModel);
    }
}
