package spring.demo.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailSevice {

    @Autowired
    private Environment environment;

    @Value("${demo.disney.email.sender}")
    private String emailSender;
    @Value("${demo.disney.email.enabled}")
    private boolean enabled;


    public void sendWelcomeEmailTo(String to) {
        // SendGrid API Key es una variable de entorno
        String apiKey = environment.getProperty("SENDGRID_API_KEY");
        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        Content content = new Content(
                "text/plain",
                "Welcome to Disney."
        );
        String subject = "Alkemy Disney";
        // Creating the Email with all its parts, a SendGrid Object with the API key and a Request
        Mail mail = new Mail(fromEmail, subject, toEmail, content);
        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            //Showing the response details in console
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException e) {System.out.println("Error trying to send the email.");}
    }

}
