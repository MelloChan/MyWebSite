package com.mello.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/4.
 */
public class SendEmail {
    private static final String smtp = "smtp.163.com";
    private static final String serverMail = "13192265851@163.com";
    private static final String serverPassword = "123456789Mello";
    private static final String myEmail = "352983198@qq.com";
    private static final String TITLE = "新留言";

    public static void sendMail(String message) throws GeneralSecurityException, MessagingException {
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

        Session session = Session.getInstance(props);
        //会话发送
        Message msg = new MimeMessage(session);
        msg.setSubject(TITLE);
        msg.setText(message);
        msg.setFrom(new InternetAddress(serverMail));
        //服务端协议及账户与授权码
        Transport transport = session.getTransport();
        transport.connect(smtp, serverMail, serverPassword);
        transport.sendMessage(msg, new Address[]{new InternetAddress(myEmail)});
        transport.close();
    }
}
