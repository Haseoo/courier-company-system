package com.github.haseoo.courier.views.parcels;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.IllegalParcelState;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import com.github.haseoo.courier.utilities.ParcelStateDataComparator;
import com.github.haseoo.courier.views.parcels.type.ParcelTypeView;
import com.github.haseoo.courier.views.places.AddressView;
import com.github.haseoo.courier.views.places.PlaceView;
import com.github.haseoo.courier.views.receiver.info.ReceiverInfoView;
import com.github.haseoo.courier.views.users.clients.ClientView;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.haseoo.courier.enums.ParcelStateType.AT_SENDER;
import static com.github.haseoo.courier.enums.PlaceType.*;
import static com.github.haseoo.courier.enums.UserType.COMPANY_CLIENT;
import static com.github.haseoo.courier.enums.UserType.LOGISTICIAN;
import static com.github.haseoo.courier.utilities.Constants.PRIORITY_MULTILAYER;
import static com.github.haseoo.courier.utilities.Utils.isParcelMoveable;
import static lombok.AccessLevel.PRIVATE;

@Getter
@SuperBuilder
@NoArgsConstructor(access = PRIVATE)
public abstract class ParcelView {
    private Long id;
    private ParcelTypeView parcelType;
    private ReceiverInfoView receiverInfoView;
    private LocalDate expectedCourierArrivalDate;
    private BigDecimal parcelPrice;
    private BigDecimal parcelFee;
    private Boolean paid;
    private Boolean priority;
    private Boolean toReturn;

    @EqualsAndHashCode(callSuper = true)
    @Getter
    @SuperBuilder
    @NoArgsConstructor(access = PRIVATE)
    public static class ParcelClientView extends ParcelView {
        private List<ParcelStateView> parcelStatesView;
        private AddressView deliveryAddress;
        private Boolean moveable;

        public static ParcelClientView of(ParcelData parcelData) {
            return ParcelClientView
                    .builder()
                    .id(parcelData.getId())
                    .parcelType(ParcelTypeView.of(parcelData.getParcelType()))
                    .receiverInfoView(ReceiverInfoView.of(parcelData.getReceiverContactData()))
                    .expectedCourierArrivalDate(parcelData.getExpectedCourierArrivalDate())
                    .parcelPrice(calculatePrice(parcelData))
                    .parcelFee(parcelData.getParcelFee())
                    .paid(parcelData.getPaid())
                    .priority(parcelData.getPriority())
                    .toReturn(parcelData.getToReturn())
                    .parcelStatesView(parcelData.getParcelStates()
                            .stream()
                            .map(parcelStateData -> ParcelStateView.of(parcelStateData, COMPANY_CLIENT))
                            .collect(Collectors.toList()))
                    .deliveryAddress(AddressView.of(parcelData.getDeliveryAddress()))
                    .moveable(isParcelMoveable(parcelData))
                    .build();
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    @SuperBuilder
    @AllArgsConstructor(access = PRIVATE)
    public static class ParcelAnonymousView extends ParcelClientView {
        private String sender;
        private AddressView senderAddress;

        public static ParcelAnonymousView of(ParcelData parcelData, String sender) {
            return ParcelAnonymousView
                    .builder()
                    .id(parcelData.getId())
                    .parcelType(ParcelTypeView.of(parcelData.getParcelType()))
                    .receiverInfoView(ReceiverInfoView.of(parcelData.getReceiverContactData()))
                    .expectedCourierArrivalDate(parcelData.getExpectedCourierArrivalDate())
                    .parcelPrice(calculatePrice(parcelData))
                    .parcelFee(parcelData.getParcelFee())
                    .paid(parcelData.getPaid())
                    .priority(parcelData.getPriority())
                    .toReturn(parcelData.getToReturn())
                    .parcelStatesView(parcelData.getParcelStates()
                            .stream()
                            .map(parcelStateData -> ParcelStateView.of(parcelStateData, COMPANY_CLIENT))
                            .collect(Collectors.toList()))
                    .deliveryAddress(AddressView.of(parcelData.getDeliveryAddress()))
                    .moveable(isParcelMoveable(parcelData))
                    .sender(sender)
                    .senderAddress(AddressView.of(parcelData.getSenderAddress()))
                    .build();
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    @SuperBuilder
    @AllArgsConstructor(access = PRIVATE)
    public static class ParcelCourierView extends ParcelView {
        private PlaceView source;
        private PlaceView destination;
        private ParcelStateType currentState;

        public static ParcelCourierView of(ParcelData parcelData) {
            return ParcelCourierView
                    .builder()
                    .id(parcelData.getId())
                    .parcelType(ParcelTypeView.of(parcelData.getParcelType()))
                    .receiverInfoView(ReceiverInfoView.of(parcelData.getReceiverContactData()))
                    .expectedCourierArrivalDate(parcelData.getExpectedCourierArrivalDate())
                    .parcelPrice(calculatePrice(parcelData))
                    .parcelFee(parcelData.getParcelFee())
                    .paid(parcelData.getPaid())
                    .priority(parcelData.getPriority())
                    .toReturn(parcelData.getToReturn())
                    .source(getSource(parcelData))
                    .destination(getDestination(parcelData))
                    .currentState(parcelData.getCurrentState().getState())
                    .build();
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    @SuperBuilder
    @AllArgsConstructor(access = PRIVATE)
    public static class ParcelLogisticianView extends ParcelView {
        private List<ParcelStateView> parcelStatesView;
        private PlaceView source;
        private PlaceView destination;
        private ParcelStateView currentState;
        private ClientView clientView;
        private Boolean dateMoved;

        public static ParcelLogisticianView of(ParcelData parcelData) {
            return ParcelLogisticianView
                    .builder()
                    .id(parcelData.getId())
                    .parcelType(ParcelTypeView.of(parcelData.getParcelType()))
                    .receiverInfoView(ReceiverInfoView.of(parcelData.getReceiverContactData()))
                    .expectedCourierArrivalDate(parcelData.getExpectedCourierArrivalDate())
                    .parcelPrice(calculatePrice(parcelData))
                    .parcelFee(parcelData.getParcelFee())
                    .paid(parcelData.getPaid())
                    .priority(parcelData.getPriority())
                    .toReturn(parcelData.getToReturn())
                    .parcelStatesView(parcelData.getParcelStates()
                            .stream()
                            .map(parcelStateData -> ParcelStateView.of(parcelStateData, LOGISTICIAN))
                            .collect(Collectors.toList()))
                    .source(getSource(parcelData))
                    .destination(getDestination(parcelData))
                    .currentState(ParcelStateView.of(parcelData.getCurrentState(), LOGISTICIAN))
                    .clientView(ClientView.ofWithoutParcels(parcelData.getSender()))
                    .dateMoved(parcelData.getDateMoved())
                    .build();
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    @SuperBuilder
    @AllArgsConstructor(access = PRIVATE)
    public static class ParcelAdminView extends ParcelView {
        private char[] pin;
        private List<ParcelStateView> parcelStatesView;
        private PlaceView source;
        private PlaceView destination;
        private ParcelStateView currentState;
        private ClientView clientView;
        private Boolean dateMoved;

        public static ParcelAdminView of(ParcelData parcelData) {
            return ParcelAdminView
                    .builder()
                    .id(parcelData.getId())
                    .parcelType(ParcelTypeView.of(parcelData.getParcelType()))
                    .receiverInfoView(ReceiverInfoView.of(parcelData.getReceiverContactData()))
                    .expectedCourierArrivalDate(parcelData.getExpectedCourierArrivalDate())
                    .parcelPrice(calculatePrice(parcelData))
                    .parcelFee(parcelData.getParcelFee())
                    .paid(parcelData.getPaid())
                    .priority(parcelData.getPriority())
                    .toReturn(parcelData.getToReturn())
                    .pin(parcelData.getPin())
                    .parcelStatesView(parcelData.getParcelStates()
                            .stream()
                            .map(parcelStateData -> ParcelStateView.of(parcelStateData, LOGISTICIAN))
                            .collect(Collectors.toList()))
                    .source(getSource(parcelData))
                    .destination(getDestination(parcelData))
                    .currentState(ParcelStateView.of(parcelData.getCurrentState(), LOGISTICIAN))
                    .clientView(ClientView.ofWithoutParcels(parcelData.getSender()))
                    .dateMoved(parcelData.getDateMoved())
                    .build();
        }
    }

    public static ParcelView of(ParcelData parcelData, UserType userType, String sender) {
        switch (userType) {
            case COMPANY_CLIENT:
            case INDIVIDUAL_CLIENT:
                return ParcelClientView.of(parcelData);
            case LOGISTICIAN:
                return ParcelLogisticianView.of(parcelData);
            case COURIER:
                return ParcelCourierView.of(parcelData);
            case ADMIN:
                return ParcelAdminView.of(parcelData);
            case ANONYMOUS:
            default:
                return ParcelAnonymousView.of(parcelData, sender);
        }
    }

    private static BigDecimal calculatePrice(ParcelData parcelData) {
        BigDecimal price = parcelData.getParcelType().getPrice();
        if (parcelData.getPriority()) {
            price = price.multiply(PRIORITY_MULTILAYER);
        }
        return price;
    }

    protected static PlaceView getSource(ParcelData parcelData) {
        if (!parcelData.wasInMagazine()) {
            return PlaceView
                    .builder()
                    .address(AddressView.of(parcelData.getSenderAddress()))
                    .placeType(SENDER)
                    .build();
        }
        return PlaceView
                .builder()
                .placeType(MAGAZINE)
                .address(AddressView.of(parcelData.getParcelStates()
                        .stream()
                        .filter(parcelStateData -> parcelStateData.getState()
                                .equals(ParcelStateType.IN_MAGAZINE))
                        .max(new ParcelStateDataComparator())
                        .orElseThrow(IllegalParcelState::new)
                        .getMagazine()
                        .getAddress()))
                .build();
    }

    protected static PlaceView getDestination(ParcelData parcelData) {
        if (!parcelData.wasInMagazine()) {
            return PlaceView
                    .builder()
                    .placeType(MAGAZINE)
                    .address(AddressView.of(parcelData.getParcelStates()
                            .stream()
                            .filter(parcelStateData -> parcelStateData.getState().equals(AT_SENDER))
                            .findFirst().orElseThrow(IllegalParcelState::new)
                            .getMagazine().getAddress()))
                    .build();
        }
        return PlaceView
                .builder()
                .placeType(RECEIVER)
                .address(AddressView.of(parcelData.getDeliveryAddress()))
                .build();
    }
}
