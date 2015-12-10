package enterprise.java.emaildriver;

import enterprise.java.entity.EmailMessage;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sun Prairie PC on 12/10/2015.
 */
public class EmailDriverTest {

    EmailDriver driver = new EmailDriver();

    @Test
    public void createPropertiesTypeTest() {

        Properties props = driver.createProperties();

        assertTrue("The method did not return the expected object type", props instanceof Properties);
    }

    @Test
    public void createPropertiesHostValueTest() {

        Properties props = driver.createProperties();

        String key = EmailDriver.HOST_KEY;
        String expectedValue = EmailDriver.HOST;

        assertEquals("The properties did not return the expected value", props.getProperty(key), expectedValue);
    }

    @Test
    public void createPropertiesSfPortValueTest() {

        Properties props = driver.createProperties();

        String key = EmailDriver.SF_PORT_KEY;
        String expectedValue = EmailDriver.SF_PORT;

        assertEquals("The properties did not return the expected value", props.getProperty(key), expectedValue);
    }

    @Test
    public void createPropertiesClassValueTest() {

        Properties props = driver.createProperties();

        String key = EmailDriver.CLASS_KEY;
        String expectedValue = EmailDriver.CLASS;

        assertEquals("The properties did not return the expected value", props.getProperty(key), expectedValue);
    }

    @Test
    public void createPropertiesAuthValueTest() {

        Properties props = driver.createProperties();

        String key = EmailDriver.AUTH_KEY;
        String expectedValue = EmailDriver.AUTH;

        assertEquals("The properties did not return the expected value", props.getProperty(key), expectedValue);
    }

    @Test
    public void createPropertiesSmtfPortValueTest() {

        Properties props = driver.createProperties();

        String key = EmailDriver.SMTP_PORT_KEY;
        String expectedValue = EmailDriver.SMTP_PORT;

        assertEquals("The properties did not return the expected value", props.getProperty(key), expectedValue);
    }

    @Test
    public void createSessionTypeTest() {

        Properties props = driver.createProperties();
        Session session = driver.createSession(props);

        assertTrue("The method did not return the expected object type", session instanceof Session);
    }

    @Test
    public void createMessageTypeTest() throws MessagingException {

        EmailMessage emailMessage = new EmailMessage();
        String address = "emailaddress@test.com";

        emailMessage.setSubject("test subject");
        emailMessage.setBody("test body");

        Properties props = driver.createProperties();
        Session session = driver.createSession(props);
        Message message = driver.createMessage(session, emailMessage, address);

        assertTrue("The method did not return the expected object type", message instanceof Message);
    }

    @Test
    public void createMessageSubjectValueTest() throws MessagingException {

        EmailMessage emailMessage = new EmailMessage();
        String address = "emailaddress@test.com";
        String testSubject = "test subject";
        String testBody = "test body";

        emailMessage.setSubject(testSubject);
        emailMessage.setBody(testBody);

        Properties props = driver.createProperties();
        Session session = driver.createSession(props);
        Message message = driver.createMessage(session, emailMessage, address);

        assertEquals("The email message did not return the expected value", message.getSubject(), testSubject);
    }
}
