package com.example.collectiontableadministration.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author lhy
 * 邮箱发送工具类
 */
public class MailUtil{
    /**
     *
     * @param email     接收者邮箱
     * @param subject   邮件主题
     * @param emailMsg  邮件内容
     * @throws AddressException
     * @throws MessagingException
     */
    public static void sendMail(String email, String subject,String emailMsg)
            throws AddressException, MessagingException {
        //创建配置文件
        Properties props = new Properties();
        //设置发送时遵从SMTP协议
        props.setProperty("mail.transport.protocol", "SMTP");
        /*
         * 发送邮件的域名
         * smtp.xx.com
         * smtp.qq.com则代表发送邮件时使用的邮箱域名来自qq
         * smtp.163.com则代表发送邮件时使用的邮箱域名来自163
         */
        props.setProperty("mail.smtp.host","smtp.163.com");
        props.setProperty("mail.smtp.port","25");
        props.setProperty("mail.smtp.auth","true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //return new PasswordAuthentication("用户名", "密码");
                //注意qq邮箱需要去qq邮箱的设置中获取授权码，并将授权码作为密码来填写
                return new PasswordAuthentication("yzh011477", "KSPAGYKJVZVOWAOZ");
            }
        };
        //创建session域
        Session session = Session.getInstance(props, auth);
        Message message = new MimeMessage(session);
        //设置邮件发送者,与PasswordAuthentication中的邮箱一致即可
        message.setFrom(new InternetAddress("yzh011477@163.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        //设置邮件主题
        message.setSubject(subject);
        //设置邮件内容
        message.setContent(emailMsg, "text/html;charset=utf-8");
        //发送邮件
        Transport.send(message);
    }
}