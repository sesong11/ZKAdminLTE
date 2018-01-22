package com.sample.ZKSpringJPA.utils;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import org.zkoss.zk.ui.Executions;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class EmailHelper {

    private static ExchangeService service;
    private static String webservice = "https://mail.phillipbank.com.kh/ews/exchange.asmx";
    @Getter
    public static String username = "paperless@phillipbank.com.kh";
    private static String password = "Abc123";
    private static ExchangeCredentials credentials = new WebCredentials(username, password);
    @Getter @Setter
    private static String emailTemplate ="/view/request/email-template/email_template.html";

    private static ExchangeService getService() throws URISyntaxException {
            service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
            service.setCredentials(credentials);
            service.setUrl(new URI(webservice));
        return service;
    }

    public static void getEmail() throws Exception {
        ItemView view = new ItemView(10);
        FindItemsResults<Item> findResults = getService().findItems(WellKnownFolderName.Inbox, view);

        for (Item item : findResults.getItems()) {
            item.load(new PropertySet(BasePropertySet.FirstClassProperties, ItemSchema.MimeContent));
            EmailMessage emailMessage = EmailMessage.bind(getService(), item.getId());
            System.out.println(emailMessage.getSubject());
        }
    }

    public static void sendMail(String subject,
            String content, List<String> toList, List<String> ccList) throws Exception {
        EmailMessage message = new EmailMessage(getService());
        EmailAddress fromEmailAddress = new EmailAddress(username);
        MessageBody messageBody = new MessageBody();
        messageBody.setBodyType(BodyType.HTML);
        messageBody.setText(content);

        message.setSubject(subject);
        message.setBody(messageBody);
        message.setFrom(fromEmailAddress);
        for(String email : toList) {
            message.getToRecipients().add(email);
        }
        message.send();
    }

    public static String replaceContentByPath(String path, HashMap<String,String> keyValues) throws IOException {
        String src =  Executions.getCurrent().getDesktop().getWebApp().getRealPath(path);
        Stream stream = Files.lines(Paths.get(src));
        String temp = "";
        Set set = keyValues.entrySet();

        for(Object obj : stream.toArray()) {
            temp += obj.toString();
        }
        for(String key : keyValues.keySet()){
            temp = temp.replace(key , keyValues.get(key));
        }
        return temp;
    }

    public static String replaceContentBySource(String source, HashMap<String,String> keyValues) {
        for(String key : keyValues.keySet()){
            source = source.replace(key , keyValues.get(key));
        }
        return source;
    }
}
