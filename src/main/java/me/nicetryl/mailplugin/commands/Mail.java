package me.nicetryl.mailplugin.commands;

public class Mail {
    private final String RecipientName;
    private final String SenderName;
    private final String MailContents;

public Mail(String recipientName, String senderName, String mailContents) {
    RecipientName = recipientName;
    SenderName = senderName;
    MailContents = mailContents;
}
    public String getRecipientName() {
        return RecipientName;
    }

    public String getSenderName() {
        return SenderName;
    }

    public String getMailContents() {
        return MailContents;

    }
}
