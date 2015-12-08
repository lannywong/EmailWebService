package enterprise.java.entity;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Student on 11/22/2015.
 */
public class Recipients {
    @JsonProperty("recipients")
    ArrayList<Recipient> recipientsArrayList;

    public ArrayList<Recipient> getRecipientsArrayList() {
        return recipientsArrayList;
    }

    public void setRecipientsArrayList(ArrayList<Recipient> recipientsArrayList) {
        this.recipientsArrayList = recipientsArrayList;
    }
}
