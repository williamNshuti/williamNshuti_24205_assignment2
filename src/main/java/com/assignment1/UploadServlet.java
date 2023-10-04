package com.assignment1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        Part passportPart = request.getPart("passport");
        Part certificatesPart = request.getPart("certificates");

        
        String userEmail = (String) request.getSession().getAttribute("userEmail");

        if (userEmail != null && !userEmail.isEmpty()) {
            if (sendConfirmationEmail(userEmail)) {
                out.println("Upload successful! Confirmation email sent to " + userEmail);
            } else {
                out.println("Upload successful, but failed to send confirmation email.");
            }
        } else {
            out.println("Upload successful, but no user email found in the session.");
        }
        
        
        out.close();
    }
    
    private boolean sendConfirmationEmail(String userEmail) {
        final String username = "carecnnct@gmail.com";
        final String password = "hwrqwrkncapthxqc";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
           
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(userEmail));

            message.setSubject("Upload Confirmation");
            message.setText("Your file(s) have been successfully uploaded.");

            Transport.send(message);

            return true; // Email sent successfully
        } catch (MessagingException e) {
            e.printStackTrace();
            return false; // Failed to send email
        }
    }
}
