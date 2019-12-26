package com.github.haseoo.courier.utilities;

import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualData;
import com.github.haseoo.courier.services.ports.ClientCompanyService;
import com.github.haseoo.courier.services.ports.ClientIndividualService;
import com.github.haseoo.courier.views.parcels.ParcelView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.github.haseoo.courier.enums.ClientType.COMPANY;
import static com.github.haseoo.courier.enums.ClientType.INDIVIDUAL;

@RequiredArgsConstructor
@Component
public class ParcelViewCreator {
    private final UserDetailsServiceImpl userDetailsService;
    private final ClientIndividualService clientIndividualService;
    private final ClientCompanyService clientCompanyService;

    public ParcelView createParcelView(ParcelData parcelData) {
        UserType userType = ((userDetailsService.currentUser() != null)
                ? userDetailsService.currentUser().getUserType() : null);
        String clientDetails = getClientDetails(parcelData);
        if (userType == null) {
            userType = UserType.ANONYMOUS;
        }
        return ParcelView.of(parcelData, userType, clientDetails);
    }

    private String getClientDetails(ParcelData parcelData) {
        String clientDetails = null;
        if (parcelData.getSender().getClientType() == COMPANY) {
            clientDetails = clientCompanyService
                    .getById(parcelData.getSender().getId())
                    .getCompanyName();
        }
        if (parcelData.getSender().getClientType() == INDIVIDUAL) {
            ClientIndividualData individualData = clientIndividualService.getById(parcelData.getSender().getId());
            clientDetails = String.format("%s %s", individualData.getName(), individualData.getSurname());
        }
        return clientDetails;
    }
}
