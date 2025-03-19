package fornari.nucleo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public String sendMail(String to, String[] cc, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(body);

        //if atachment use MimeMessage
        mailSender.send(message);
        return "mail sent successfully";
    }
}
