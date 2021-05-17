package com.revature.pokecare.service;

import com.revature.pokecare.models.Trainer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final MailSender mailSender;
    private final SimpleMailMessage templateMessage;
    private static final Logger loggy = Logger.getLogger(MailService.class);

    @Autowired
    public MailService(MailSender mailSender, SimpleMailMessage templateMessage) {
        loggy.info("Constructing MailService");
        this.mailSender = mailSender;
        this.templateMessage = templateMessage;

        templateMessage.setFrom("PokeCare <" + templateMessage.getFrom() + ">");
    }

    public void sendRegistration(Trainer trainer) {
        loggy.info("sendRegistration called in MailService.");
        SimpleMailMessage mail = new SimpleMailMessage(templateMessage);

        mail.setSubject("PokeCare Account Confirmation");
        mail.setTo(trainer.getEmail());
        mail.setText("New Account!");

        mailSender.send(mail);
    }
}