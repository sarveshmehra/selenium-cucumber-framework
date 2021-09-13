package com.bisnode.common.login.sms;

import com.bisnode.common.login.sms.client.SmsClient;
import com.bisnode.common.login.sms.client.SmsClientImpl;
import com.bisnode.common.login.sms.client.VirtualSms;
import configs.AppUrls;

public class SmsReader {

    private static final SmsClient client = new SmsClientImpl(AppUrls.SMS_URL);

    public static VirtualSms lastMessageSentTo(String phoneNumber) {
        return client.getSms(phoneNumber);
    }

    public static void deleteAllMessagesTo(String phoneNumber) {
        client.deleteAllMessages(phoneNumber);
    }
}
