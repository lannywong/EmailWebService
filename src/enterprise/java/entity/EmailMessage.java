package enterprise.java.entity;

/**
 * Created by Student on 11/7/2015.
 */
public class EmailMessage {
    private String subject;
    private String body;

    public EmailMessage() {}


    public EmailMessage(String subject,String body) {
        setSubject(subject);
        setBody(body);
    }

    public EmailMessage(String template, String subject, String body) {

        StringBuilder bodyWithTemplate = new StringBuilder();
        bodyWithTemplate.append(template + ",\n\n" + body);

        this.subject = subject;
        this.body = bodyWithTemplate.toString();
    }

    // Get/Set for body
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    // Get/Set for subject
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
