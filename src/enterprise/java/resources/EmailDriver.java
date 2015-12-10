package enterprise.java.resources;

import enterprise.java.JsonMapper;
import enterprise.java.entity.EmailMessage;
import enterprise.java.entity.Recipient;
import enterprise.java.entity.Recipients;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Properties;

/**
 * @author SlakkDaddeez
 *
 */
@Path("/sendEmail")
public class EmailDriver {

    /**
     * The only purpose of this method is to return a String, letting the user know that the path was reached.
     * @return a string to user
     */
    @GET
    @Path("/test")
    @Produces("text/html")
    public String testPath() {
        return "Hey it worked!";
    }

    /**
     * This method constructs an EmailMessage and calls the sendEmail() passing in the newly constructed message and
     * the recipients address.
     * @param subject used to construct an EmailMessage
     * @param body used to construct an EmailMessage
     * @param recipient sent as a parameter in the sendEmail() call
     * @return
     */
    @GET
    @Path("{sbj}/{msg}/{recipient}")
    @Produces("text/html")
    public String sendEmailToStringOfAddresses(@PathParam("sbj") String subject,
                                               @PathParam("msg") String body,
                                               @PathParam("recipient") String recipient) {

        EmailMessage message = new EmailMessage(subject, body);

        sendEmail(message, recipient);

        String feedbackMessage = "Email Sent Successfully";
        return feedbackMessage;
    }


    /**
     * This method maps a Recipients object based on the jsonRecipients parameter.
     * Loops through the Recipient objects and creates a unique Email Message for each and than calling the sendEmail()
     * @param subject used to construct an EmailMessage
     * @param body used to construct an EmailMessage
     * @param jsonRecipients used to map a Recipients object
     * @return a string with a list of recipient names of the emails sent.
     */
    @GET
    @Path("/personalize/{sbj}/{msg}/{recipient}")
    @Produces("text/html")
    public String sendEmailToRecipientObjectAddresses(@PathParam("sbj") String subject,
                                                      @PathParam("msg") String body,
                                                      @PathParam("recipient") String jsonRecipients) {

        Recipients recipients = JsonMapper.toClassInstance(jsonRecipients, Recipients.class);

        StringBuilder feedbackMessage = new StringBuilder();
        feedbackMessage.append("Emails were sent to:\n");

        for(Recipient recipient : recipients.getRecipientsArrayList()) {
            String template = "Hello " + recipient.getRecipientName();
            EmailMessage message = new EmailMessage(template ,subject, body);

            sendEmail(message, recipient.getEmailAddress());
            feedbackMessage.append(recipient.getRecipientName() + "\n");
        }

        return feedbackMessage.toString();
    }

    /**
     * The purpose of this method is to send an email.
     * This method uses 'slackTeamTest@gmail.com' as the sender.
     * The contents of the message and the recipient are passed in on the string
     * @param emailMessage an EmailMessage object that has a subj/body
     * @param address an address the email should be sent to
     */
    public void sendEmail(EmailMessage emailMessage, String address) {
        final String username = "slackTeamTest@gmail.com";
        final String password = "weLoveSlack";

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("slackTeamTest@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
            message.setSubject(emailMessage.getSubject());
            message.setText(emailMessage.getBody());

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}