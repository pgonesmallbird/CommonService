package org.lgp.utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class EmailUtil {
    private static final String SMTP_HOST = "smtp.163.com";
    private static final String USER_NAME = "dujingtao0228@163.com";
    //邮箱授权码
    private static final String PASSWORD = "*******";


    /**
     * 发送邮件
     *
     * @param title
     * @param content
     * @param receiveList
     * @throws Exception
     */
    public static void sendEmail(String title, String content, List<String> receiveList, String fileBytes) {


        Session session = EmailUtil.getSession();
        MimeMessage message = new MimeMessage(session);
        InternetAddress[] toArray = new InternetAddress[receiveList.size()];
        try {
            //接收列表
            for (int i = 0; i < toArray.length; i++) {
                toArray[i] = new InternetAddress(receiveList.get(i));
            }
            message.setSubject(title);
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(USER_NAME));
            message.addRecipients(MimeMessage.RecipientType.TO, toArray);
            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();
            // 消息
            messageBodyPart.setContent(content, "text/html;charset=utf-8");
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);
            // 附件部分
            messageBodyPart = new MimeBodyPart();
            // 设置要发送附件的文件路径
            DataSource source = new FileDataSource(fileBytes);
            messageBodyPart.setDataHandler(new DataHandler(source));
            // 处理附件名称中文（附带文件路径）乱码问题
            messageBodyPart.setFileName(MimeUtility.encodeText(title + ".zip"));
            multipart.addBodyPart(messageBodyPart);
            // 发送完整消息
            message.setContent(multipart);
            // 发送消息
            Transport.send(message);
        } catch (Exception e) {
        }
    }

    public static Session getSession() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", SMTP_HOST);
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER_NAME, PASSWORD);
                    }
                });

        return session;
    }
}
