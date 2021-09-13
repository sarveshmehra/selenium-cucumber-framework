package com.bisnode.common.login.mail;

import configs.AppUrls;
import com.bisnode.mail.smtp.test.client.MailService;
import com.bisnode.mail.smtp.test.client.RestMailService;
import com.bisnode.mail.smtp.test.common.SimpleMessage;

import java.util.List;

public class MailReader {

    private static final MailService mailService = new RestMailService(AppUrls.SMTP_TEST_SERVER_URL);

    public static List<SimpleMessage> getMailTo(String address){
       return mailService.getMailTo(address);
    }

    public static SimpleMessage getLastMessageSentTo(String address) throws InterruptedException {
        Thread.sleep(3000);
        List<SimpleMessage> messages = getMailTo(address);
        if( messages!=null && messages.size() > 0){
            return messages.get(messages.size()-1);
        }
        return null;
    }

    public static List<SimpleMessage> deleteMailTo(String address){
        return mailService.deleteMailTo(address);
    }


}
