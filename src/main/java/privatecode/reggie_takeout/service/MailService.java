package privatecode.reggie_takeout.service;

public interface MailService {
    void sendVertifyCode(String to, String title, String content);
}
