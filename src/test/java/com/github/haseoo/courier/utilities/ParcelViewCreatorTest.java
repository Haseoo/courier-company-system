package com.github.haseoo.courier.utilities;

import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.models.ParcelModel;
import com.github.haseoo.courier.security.UserDetailsImpl;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualData;
import com.github.haseoo.courier.services.ports.ClientCompanyService;
import com.github.haseoo.courier.services.ports.ClientIndividualService;
import com.github.haseoo.courier.views.parcels.ParcelView;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.github.haseoo.courier.testutlis.constants.Constants.UNIT_TEST;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelAtSender;
import static com.github.haseoo.courier.testutlis.generators.ParcelDataGenerator.getParcelInMagazine;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCompanyClientModel;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getIndividualClientModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag(UNIT_TEST)
class ParcelViewCreatorTest {
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private ClientIndividualService clientIndividualService;
    @Mock
    private ClientCompanyService clientCompanyService;

    @InjectMocks
    private ParcelViewCreator sut;

    @Test
    void should_return_anonymous_view() {
        //given
        ParcelData in = getParcelData();
        when(clientCompanyService.getById(any())).thenReturn(ClientCompanyData.of(getCompanyClientModel()));
        //when & then
        ParcelView out = sut.createParcelView(in);
        Assertions.assertThat(out).isExactlyInstanceOf(ParcelView.ParcelAnonymousView.class);
        ParcelView.ParcelAnonymousView cOut = (ParcelView.ParcelAnonymousView) out;
        Assertions.assertThat(cOut.getId()).isEqualTo(in.getId());
        Assertions.assertThat(cOut.getParcelType().getId()).isEqualTo(in.getParcelType().getId());
        Assertions.assertThat(cOut.getExpectedCourierArrivalDate()).isEqualTo(in.getExpectedCourierArrivalDate());
        Assertions.assertThat(cOut.getParcelPrice()).isEqualTo(in.getParcelType().getPrice());
        Assertions.assertThat(cOut.getParcelFee()).isEqualTo(in.getParcelFee());
        Assertions.assertThat(cOut.getPaid()).isEqualTo(in.getPaid());
        Assertions.assertThat(cOut.getPriority()).isEqualTo(in.getPriority());
        Assertions.assertThat(cOut.getToReturn()).isEqualTo(in.getToReturn());
    }

    @Test
    void should_return_admin_view() {
        //given
        ParcelModel parcelModel = getParcelAtSender();
        parcelModel.setSender(getIndividualClientModel());
        parcelModel.getSender().setObservedParcels(new ArrayList<>());
        parcelModel.getSender().setSentParcels(new ArrayList<>());
        ParcelData parcelData = ParcelData.of(parcelModel);
        when(clientIndividualService.getById(any())).thenReturn(ClientIndividualData.of(getIndividualClientModel()));
        when(userDetailsService.currentUser()).thenReturn(new UserDetailsImpl(1L, "", null, null, UserType.ADMIN, true));
        //when & then
        Assertions.assertThat(sut.createParcelView(parcelData)).isExactlyInstanceOf(ParcelView.ParcelAdminView.class);
    }

    @Test
    void should_return_courier_view_at_sender() {
        //given
        ParcelData parcelData = getParcelData();
        when(clientCompanyService.getById(any())).thenReturn(ClientCompanyData.of(getCompanyClientModel()));
        when(userDetailsService.currentUser()).thenReturn(new UserDetailsImpl(1L, "", null, null, UserType.COURIER, true));

        //when & then
        Assertions.assertThat(sut.createParcelView(parcelData)).isExactlyInstanceOf(ParcelView.ParcelCourierView.class);
    }

    @Test
    void should_return_courier_view_in_magazine() {
        //given
        ParcelModel parcelModel = getParcelInMagazine();
        parcelModel.getSender().setObservedParcels(new ArrayList<>());
        parcelModel.getSender().setSentParcels(new ArrayList<>());
        when(clientCompanyService.getById(any())).thenReturn(ClientCompanyData.of(getCompanyClientModel()));
        when(userDetailsService.currentUser()).thenReturn(new UserDetailsImpl(1L, "", null, null, UserType.COURIER, true));

        //when & then
        Assertions.assertThat(sut.createParcelView(ParcelData.of(parcelModel))).isExactlyInstanceOf(ParcelView.ParcelCourierView.class);
    }

    @Test
    void should_return_logistician_view() {
        //given
        ParcelData parcelData = getParcelData();
        when(clientCompanyService.getById(any())).thenReturn(ClientCompanyData.of(getCompanyClientModel()));
        when(userDetailsService.currentUser()).thenReturn(new UserDetailsImpl(1L, "", null, null, UserType.LOGISTICIAN, true));

        //when & then
        Assertions.assertThat(sut.createParcelView(parcelData)).isExactlyInstanceOf(ParcelView.ParcelLogisticianView.class);
    }

    @Test
    void should_return_client_view() {
        //given
        ParcelData parcelData = getParcelData();
        when(clientCompanyService.getById(any())).thenReturn(ClientCompanyData.of(getCompanyClientModel()));
        when(userDetailsService.currentUser()).thenReturn(new UserDetailsImpl(1L, "", null, null, UserType.COMPANY_CLIENT, true));

        //when & then
        Assertions.assertThat(sut.createParcelView(parcelData)).isExactlyInstanceOf(ParcelView.ParcelClientView.class);
    }

    private static ParcelData getParcelData() {
        ParcelModel parcelModel = getParcelAtSender();
        parcelModel.getSender().setObservedParcels(new ArrayList<>());
        parcelModel.getSender().setSentParcels(new ArrayList<>());
        return ParcelData.of(parcelModel);
    }
}