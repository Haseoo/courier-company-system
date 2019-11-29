package com.github.haseoo.courier.querydata;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiverInfoQueryData {
    private String name;
    private String surname;
    private String emailAddress;
    private String phoneNumber;
}
