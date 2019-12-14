package com.github.haseoo.courier.views.receiver.info;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@SuperBuilder
public class ReceiverInfoView {
    private Long id;
    private String name;
    private String surname;
    private String emailAddress;
    private String phoneNumber;
}
