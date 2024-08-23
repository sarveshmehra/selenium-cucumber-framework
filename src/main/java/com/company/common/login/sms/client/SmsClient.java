package com.company.common.login.sms.client;

public interface SmsClient {

    VirtualSms getSms(String number);

    void deleteAllMessages(String phoneNumber);
}
