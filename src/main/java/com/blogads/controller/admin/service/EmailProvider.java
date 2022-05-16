package com.blogads.controller.admin.service;

import com.blogads.controller.admin.model.MailInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailProvider {

    private final JavaMailSender emailSender;

    /**
     * Send a normal text email.
     *
     * @param mailInfos {@link List<MailInfo>}
     */
    public void sendSimpleMail(List<MailInfo> mailInfos) {
        MimeMessage mimeMessage;
        for (MailInfo item : mailInfos) {
            mimeMessage = this.getMimeMessage(item, false);
            emailSender.send(mimeMessage);
        }
    }

    /**
     * Send an HTML email and a file attachment.
     *
     * @param mailInfos {@link List<MailInfo>}
     */
    public void sendFileMail(List<MailInfo> mailInfos) {
        MimeMessage mimeMessage;
        for (MailInfo item : mailInfos) {
            mimeMessage = this.getMimeMessage(item, true);
            //TODO update
            emailSender.send(mimeMessage);
        }
    }

    @SneakyThrows
    private MimeMessage getMimeMessage(MailInfo mailInfo, boolean multiPart) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, multiPart, StandardCharsets.UTF_8.name());
//        helper.setFrom(mailInfo.getEmailFrom());
        helper.setTo(mailInfo.getEmailTo());
        helper.setText(mailInfo.getContent(), true);
        helper.setSubject(mailInfo.getEmailSubject());
        return mimeMessage;
    }
}
