package com.revature.pokecare.service;

import com.revature.pokecare.models.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final MailSender mailSender;
    private final SimpleMailMessage templateMessage;

    @Autowired
    public MailService(MailSender mailSender, SimpleMailMessage templateMessage) {
        this.mailSender = mailSender;
        this.templateMessage = templateMessage;

        templateMessage.setFrom("PokeCare <" + templateMessage.getFrom() + ">");
    }

    public void sendRegistration(Trainer trainer) {
        SimpleMailMessage mail = new SimpleMailMessage(templateMessage);

        mail.setSubject("PokeCare Account Confirmation");
        mail.setTo(trainer.getEmail());
        mail.setText("New Account!");

        mailSender.send(mail);
    }
}