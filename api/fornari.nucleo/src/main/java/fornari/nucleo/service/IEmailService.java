package fornari.nucleo.service;

public interface IEmailService {

    public String sendMail(String to, String[] cc, String subject, String body);

}
