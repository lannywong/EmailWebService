package enterprise.java.entity;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Student on 11/16/2015.
 */
public class Recipient {

    @JsonProperty("email")
    private String emailAddress;

    @JsonProperty("name")
    private String recipientName;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }



}
