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
 * Created by Student on 11/7/2015.
 */
@Path("/sendEmail")
public class EmailDriver {

    @GET
    @Path("/test")
    @Produces("text/html")
    public String testPath() {
        return "Hey it worked!";
    }

    // TO TEST: http://localhost:9998/sendEmail/A Test Message/This is the body/slackTeamTest@gmail.com
    // 1. When called takes the pathParams and creates a new EmailMessage
    // 2. {sbj:A Test Message}
    @GET
    @Path("{sbj}/{msg}/{recipient}")
    @Produces("text/html")
    public String sendEmailToStringOfAddresses(@PathParam("sbj") String subject,
                                               @PathParam("msg") String body,
                                               @PathParam("recipient") String recipients) {

        EmailMessage message = new EmailMessage(subject, body);

        sendEmail(message, recipients);

        String feedbackMessage = "Email Sent Successfully";
        return feedbackMessage;
    }


//    {"recipients":[
//        {"email":"slackTeamTest@gmail.com","name":"test"},
//        {"email":"slackTeamTest@gmail.com","name":"test2"}
//        ]}
// http://localhost:9998/sendEmail/personalize/A Test Message/This is the body/{"recipients":[{"email":"slackTeamTest@gmail.com","name":"test"},{"email":"slackTeamTest@gmail.com","name":"test2"}]}
    @GET
    @Path("/personalize/{sbj}/{msg}/{recipient}")
    @Produces("text/html")
    public String sendEmailToReceipientObjectAddresses(@PathParam("sbj") String subject,
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
