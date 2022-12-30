package com.rushi.messages.modals;

public class SmsItem {
    private int _id;
    private int threadId;
    private long date;
    private long date_sent;
    private boolean read;
    private int type;
    private String address;
    private String body;
    private Boolean spam;

    public SmsItem() {
    }

    public SmsItem(int _id, int threadId, long date, long date_sent, boolean read, int type, String address, String body, Boolean spam) {
        this._id = _id;
        this.threadId = threadId;
        this.date = date;
        this.date_sent = date_sent;
        this.read = read;
        this.type = type;
        this.address = address;
        this.body = body;
        this.spam = spam;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(long date_sent) {
        this.date_sent = date_sent;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean isSpam() {
        return spam;
    }

    public void setSpam(Boolean spam) {
        this.spam = spam;
    }

}


