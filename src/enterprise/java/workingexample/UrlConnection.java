package enterprise.java.workingexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author SlakkDaddeez
 * @version 1.0 12/15
 *
 * This class provides a working example of the service. There is a method to build the URL. The
 * main method will get an HttpURLConnection and call the service.
 */
public class UrlConnection {

    protected static String EMAIL_URL_FRONT = "http://tomcat-mademailservice.rhcloud.com/NewEmailWebservice_war/rest/sendEmail/";

    /**
     * A sample main class that makes a call to the email webservice and interprets the response.
     */
    public static void main(String[] args) {

        String response = "";

        try {

            String subject = "Oldish sort of subject";
            String body = "This is yet another body of test message";
            String recipientSAddress = "slackteamtest@gmail.com";

            UrlConnection urlConnection = new UrlConnection();
            URL url = urlConnection.getEmailUrl(subject, body, recipientSAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                response += line;
            }
            reader.close();
            System.out.println("The response was: " + response);

        } catch (MalformedURLException e) {
            System.out.println("malformed url");
        } catch (IOException e) {
            System.out.println("io exception");
            e.printStackTrace();
        }
    }

    /**
     * Builds and returns a URL for the search.books method
     *
     * @param recipient The email address of the recipient
     * @param subject The subject of the email
     * @param body The body of the email massage
     */
    public URL getEmailUrl(String recipient, String subject, String body) throws MalformedURLException {

        recipient = encodeString(recipient);
        subject = encodeString(subject);
        body = encodeString(body);

        String url = EMAIL_URL_FRONT + recipient + "/" + subject + "/" + body;

        return new URL(url);
    }

    /**
     * Encodes the string to a URL, manually replacing "+" with "%20" and
     * "'" with "%27".
     *
     * @param path The URL path to encode
     */
    public String encodeString(String path) {

        String url = URLEncoder.encode(path).replace("+", "%20").replace("'", "%27");

       /* return the encoded URI manually escape the single quote character */
        return url;
    }
}
