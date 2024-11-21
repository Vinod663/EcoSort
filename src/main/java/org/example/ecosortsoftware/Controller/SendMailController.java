package org.example.ecosortsoftware.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javax.mail.*;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class SendMailController {

    @FXML
    private TextField emailText;

    @FXML
    private TextField messageText;

    @FXML
    private Button sendBtn;

    @FXML
    private TextField subjectText;

    @Setter
    private String employeeEmail;


    @FXML
    void SendBtnAction(ActionEvent event) {
        String emailFromTextField = emailText.getText().trim();

        if (!emailFromTextField.isEmpty()) {
            employeeEmail = emailFromTextField; // Override the previously set email with the new one
        }

        if (employeeEmail == null || employeeEmail.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Enter Email First!", ButtonType.OK).show();
            return; // Stop further execution if the email is empty
        }

        final String FROM = "Email";//need to hide before commit
        String subject = subjectText.getText();
        String body = messageText.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
            return;
        }

        sendEmailWithSendgrid(FROM, employeeEmail, subject, body);

    }

    private void sendEmailWithSendgrid(String from, String to, String subject, String body) {
        Properties props = new Properties();

        // Enable SMTP authentication. This requires the server to authenticate the sender before sending emails.
        props.put("mail.smtp.auth", "true");

        // Enable STARTTLS, which upgrades an insecure connection to a secure one (TLS encryption).
        props.put("mail.smtp.starttls.enable", "true");

        // Specify the SMTP server host. For Gmail, it is "smtp.gmail.com".
        props.put("mail.smtp.host", "smtp.gmail.com");

        // Specify the port to use for SMTP. Port 587 is used for TLS connections. Alternatively, port 465 can be used for SSL.
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {

                // Authenticate using the sender's email and app password
                return new PasswordAuthentication(from, "API");// app password
            }
        });

        try {
            // Create a new MimeMessage object to represent the email message.
            Message message = new MimeMessage(session);

            // Set the sender's email address using the `from` parameter.
            message.setFrom(new InternetAddress(from));

            // Set the recipient(s) of the email. The `to` parameter is parsed to handle multiple recipients, if necessary.
            // `Message.RecipientType.TO` defines that this is the primary recipient (To field).
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set the subject of the email using the `subject` parameter.
            message.setSubject(subject);

            // Set the body (content) of the email using the `messageBody` parameter.
            message.setText(body);

            // Send the email message using the `Transport.send()` method.
            // This sends the email through the SMTP server configured in the session.
            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email.").show();
        }
    }

}
