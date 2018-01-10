package com.sample.ZKSpringJPA.utils;

import lombok.Getter;
import lombok.Setter;
import org.zkoss.zk.ui.Executions;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailHelper {
    private static String  host = "mail.phillipbank.com.kh";
    private static String username = "paperless@phillipbank.com.kh";
    private static String password = "Abc123";
    private static int port = 25;

    @Getter @Setter
    private static String emailTemplate = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/view/request/email-template/email_template.html");


    public static Properties getSmtpProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.host", host);
        return props;
    }

    public static void sendEmail(InternetAddress [] to,
                                 InternetAddress [] cc,
                                 InternetAddress [] bcc,
                                 String content) throws MessagingException {
        Session session = Session.getInstance(getSmtpProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));

        message.setRecipients(Message.RecipientType.TO, to);
        message.setRecipients(Message.RecipientType.CC, cc);
        message.setRecipients(Message.RecipientType.BCC, bcc);

        message.setSubject("Testing Subject");
        message.setContent(content, "text/html");
        Transport.send(message);
    }

}
