package com.github.haseoo.courier.models;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MailModel {

    private String from;
    private String mailTo;
    private String subject;
    private List<Object> attachments;
    private Map<String, Object> props;

}
