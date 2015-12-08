### Team Members:
- Lanny Wong
- Nathan Wuenstel
- Brandon Ciancio

### Project Goal: 
Create a web-service that sends an email to one or many users. The email recipients and email contents are based on parameters. The parameters that we would require are: a collection of emails, a subjectLine, and messageContent.
				   
### Knownbugs / Limitations

* If the subject line exceeds 20 characters than the subject line will be truncated.
* If the body + recipient(s) exceede 1800 characters than the url length cap will be reached and the email will not be sent.
* If you use a ' + ' character anywhere, it will be removed and a space will take its place.
	
### Rest url + paths
```
The BASE URL: 
	\- http://tomcat-mademailservice.rhcloud.com/NewEmailWebservice_war
 		\- rest/sendEmail
 			\- test
		\- {sbj}/{msg}/{recipient}" 
			\- personalize
				\- {sbj}/{msg}/{"recipients":[{"email":"A_EMAIL","name":"A_NAME"},{"email":"A_EMAIL","name":"A_NAME"}]}

```	
### How to use our Email Webservice:

