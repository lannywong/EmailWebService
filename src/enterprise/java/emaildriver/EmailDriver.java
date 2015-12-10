package enterprise.java.emaildriver;

import enterprise.java.JsonMapper;
import enterprise.java.entity.EmailMessage;
import enterprise.java.entity.Recipient;
import enterprise.java.entity.Recipients;
import org.apache.log4j.Logger;

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

    static Logger _log = Logger.getLogger("Email Services");

    protected final static String USER_NAME = "slackTeamTest@gmail.com";
    protected final static String PASSWORD = "weLoveSlack";
    protected final static String HOST_ADDRESS = "slackTeamTest@gmail.com";

    protected final static String HOST_KEY = "mail.smtp.host";
    protected final static String HOST = "smtp.gmail.com";
    protected final static String SF_PORT_KEY = "mail.smtp.socketFactory.port";
    protected final static String SF_PORT = "465";
    protected final static String CLASS_KEY = "mail.smtp.socketFactory.class";
    protected final static String CLASS = "javax.net.ssl.SSLSocketFactory";
    protected final static String AUTH_KEY = "mail.smtp.auth";
    protected final static String AUTH = "true";
    protected final static String SMTP_PORT_KEY = "mail.smtp.port";
    protected final static String SMTP_PORT = "465";

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

        /* log and response */
        String msg = message.toString() + " Sent Successfully to " + recipient;
        _log.info(msg);
        return msg;
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

        _log.info(feedbackMessage.toString());
        return feedbackMessage.toString();
    }

    /**
      * Creates and returns a Properties object populated with
      * the assigned constants.
      */
    public Properties createProperties() {

        Properties props = new Properties();

        props.put(HOST_KEY, HOST);
        props.put(SF_PORT_KEY, SF_PORT);
        props.put(CLASS_KEY, CLASS);
        props.put(AUTH_KEY, AUTH);
        props.put(SMTP_PORT_KEY, SMTP_PORT);

        return props;
    }

    /**
     * Creates and returns a Session object created with
     * the input properties. The function has to override
     * the PasswordAuthentication method to instantiate
     * the interface.
     */
    public Session createSession(Properties props) {

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER_NAME, PASSWORD);
                    }
                });

        return session;
    }

    /**
     * Creates and returns a Session object created with
     * the input properties.
     */
    public Message createMessage(Session session, EmailMessage emailMessage, String address) throws MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(HOST_ADDRESS));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getBody());

        return message;
    }

    /**
     * The purpose of this method is to send an email.
     * This method uses 'slackTeamTest@gmail.com' as the sender.
     * The contents of the message and the recipient are passed in on the string
     * @param emailMessage an EmailMessage object that has a subj/body
     * @param address an address the email should be sent to
     */
    public void sendEmail(EmailMessage emailMessage, String address) {

        Properties props = createProperties();
        Session session = createSession(props);

        try {

            Message message = createMessage(session, emailMessage, address);

            /* log send attempt */
            String msg = " Attempting to send " + message.toString() + "to Recipient: " + address;
            _log.info(msg);

            Transport.send(message);

        } catch (MessagingException e) {
            _log.error("*** Exception thrown sending message to Recipient: " + address + " *** " + e);
            throw new RuntimeException(e);
        }
    }

}