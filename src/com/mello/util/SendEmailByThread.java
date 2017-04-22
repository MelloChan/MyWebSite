package com.mello.util;

import com.mello.entity.EmailMessage;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/30.
 * 另建线程发送邮件 TODO
 */
public class SendEmailByThread implements Runnable {
    private static final String smtp = "";
    private static final String serverMail = "";
    private static final String serverPassword = "";
    private static final String myEmail = "";
    private static final Map<Integer, String> TITLE = new HashMap<>();
    private Session session;
    private EmailMessage emailMessage;
    public  SendEmailByThread(EmailMessage emailMessage) throws GeneralSecurityException {
        this.emailMessage=emailMessage;
        TITLE.put(1, "你有一条新留言");
        TITLE.put(2, "你好,欢迎注册MelloChan的个人站点");
        Properties props = new Properties();
        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.163.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        //基本的参数协议
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        //基于SSL的邮箱安全协议 163邮箱似乎不用？
        session=Session.getInstance(props);
    }
    private void sendMail(int index, String message, String email) throws GeneralSecurityException, MessagingException {
        //设置会话发送
        Message msg = new MimeMessage(session);
        //设置发送邮件标题
        msg.setSubject(TITLE.get(index));
        //设置发送的媒体格式 此处为html
        Multipart multipart = new MimeMultipart();
        BodyPart html = new MimeBodyPart();
        //将信息组装
        html.setContent(message, "text/html;charset=utf-8");
        multipart.addBodyPart(html);
        //发送的html格式文本
        msg.setContent(multipart);
        //服务端邮箱
        msg.setFrom(new InternetAddress(serverMail));
        //服务端协议及账户与授权码
        Transport transport = session.getTransport();
        transport.connect(smtp, serverMail, serverPassword);
        //正式发送给指定账户
        transport.sendMessage(msg, new Address[]{new InternetAddress(email)});
        transport.close();
    }
    @Override
    public void run() {
        try {
            sendMail(emailMessage.getIndex(),emailMessage.getMessage(),emailMessage.getEmail());
        } catch (GeneralSecurityException | MessagingException e) {
            e.printStackTrace();
            System.out.println("邮件发送异常:"+e.getMessage());
        }
    }
}
