package com.prophius.socialMedia.mailService;

import com.prophius.socialMedia.exception.GenericException;
import com.prophius.socialMedia.notification.NotificationRequest;
import com.prophius.socialMedia.notification.NotificationResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;

import static com.prophius.socialMedia.utils.ConstantUtils.SOCIALSPACE_EMAIL;
import static com.prophius.socialMedia.utils.ConstantUtils.SOCIALSPACE_USERNAME;

@Service
@RequiredArgsConstructor
public class GmailEmailService implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    @Override
    public CompletableFuture<NotificationResponse> sendEmail(NotificationRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String emailContent = templateEngine.process(request.getContent(), request.getContext());
                MimeMessage mailMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "utf-8");
                mimeMessageHelper.setSubject(request.getSubject());
                mimeMessageHelper.setTo(request.getTo());
                mimeMessageHelper.setFrom(new InternetAddress(SOCIALSPACE_EMAIL, SOCIALSPACE_USERNAME));
                mimeMessageHelper.setText(emailContent, true);
                javaMailSender.send(mailMessage);
                return new NotificationResponse(String.format("Email sent successfully to %s", request.getTo() ));
            } catch (MessagingException | MailException | UnsupportedEncodingException exception) {
                throw new GenericException(exception.getMessage());
            }
        });
    }
}
