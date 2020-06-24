package com.github.haseoo.courier.testutlis.generators;

import com.github.haseoo.courier.models.MailModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailGenerator {
    public static MailModel getMailModel() {
        MailModel mailModel = new MailModel();
        Map<String, Object> props = new HashMap<>();
        props.put("testprop", "testprop");
        mailModel.setFrom("test@test.form");
        mailModel.setMailTo("test@test.to");
        mailModel.setSubject("Test subject");
        mailModel.setProps(props);
        mailModel.setAttachments(new ArrayList<>());
        return mailModel;
    }
}
