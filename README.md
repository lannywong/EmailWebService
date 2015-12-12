### Team Members:
- Lanny Wong
- Nathan Wuenstel
- Brandon Ciancio

###### Paths used for testing:
```
rest/sendEmail/test
rest/sendEmail/A Test Message/A Test Body/slackTeamTest@gmail
rest/sendEmail/A Test Message/A Test Body/{"recipients":[{"email":"slackTeamTest@gmail.com","name":"test"},{"email":"slackTeamTest@gmail.com","name":"test2"}]}
```

### Project Goal:
Create a web-service that sends an email to one or many users. The email recipients and email contents are based on parameters. The parameters that we would require are: a collection of emails, a subjectLine, and messageContent.
				   
### Knownbugs / Limitations

* If the subject line exceeds 20 characters than the subject line will be truncated.
* If the body + recipient(s) exceede 1800 characters than the url length cap will be reached and the email will not be sent.
* If you use a ' + ' character anywhere, it will be removed and a space will take its place.
	
### Rest url + paths
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<application xmlns="http://wadl.dev.java.net/2009/02">
  <doc xmlns:jersey="http://jersey.java.net/" jersey:generatedBy="Jersey: 1.12 02/15/2012 05:30 PM" />
  <grammars />
  <resources base="http://tomcat-mademailservice.rhcloud.com/NewEmailWebservice_war/rest/">
     <resource path="/sendEmail">
        <resource path="{sbj}/{msg}/{recipient}">
           <param xmlns:xs="http://www.w3.org/2001/XMLSchema" name="sbj" style="template" type="xs:string" />
           <param xmlns:xs="http://www.w3.org/2001/XMLSchema" name="msg" style="template" type="xs:string" />
           <param xmlns:xs="http://www.w3.org/2001/XMLSchema" name="recipient" style="template" type="xs:string" />
           <method id="sendEmailToStringOfAddresses" name="GET">
              <response>
                 <representation mediaType="text/html" />
              </response>
           </method>
        </resource>
        <resource path="/personalize/{sbj}/{msg}/{recipient}">
           <param xmlns:xs="http://www.w3.org/2001/XMLSchema" name="sbj" style="template" type="xs:string" />
           <param xmlns:xs="http://www.w3.org/2001/XMLSchema" name="msg" style="template" type="xs:string" />
           <param xmlns:xs="http://www.w3.org/2001/XMLSchema" name="recipient" style="template" type="xs:string" />
           <method id="sendEmailToRecipientObjectAddresses" name="GET">
              <response>
                 <representation mediaType="text/html" />
              </response>
           </method>
        </resource>
        <resource path="/test">
           <method id="testPath" name="GET">
              <response>
                 <representation mediaType="text/html" />
              </response>
           </method>
        </resource>
     </resource>
  </resources>
</application>
```	
### How to use our Email Webservice:

In the ```package enterprise.java.workingexample;``` there is an URLConnection class.

The main method provides a sample of how to make a connection to the webservice and get the response.

You can construct a URL that contains your message by using the

```public URL getEmailUrl(String recipient, String subject, String body)```

When this method is called the parameters are than encoded and the URL is built and returned. This would be the URL you use to connect and send an email message.


