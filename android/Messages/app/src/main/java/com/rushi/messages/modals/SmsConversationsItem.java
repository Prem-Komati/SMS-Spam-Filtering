package com.rushi.messages.modals;

public class SmsConversationsItem {
    private int threadId;
    private int msgCount;
    private String snippet;
    private String address;
    private int avatarColor;

    public SmsConversationsItem() {
    }

    public SmsConversationsItem(int threadId, int msgCount, String snippet, String address, int avatarColor) {
        this.threadId = threadId;
        this.msgCount = msgCount;
        this.snippet = snippet;
        this.address = address;
        this.avatarColor = avatarColor;
    }


    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(int avatarColor) {
        this.avatarColor = avatarColor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


