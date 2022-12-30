package com.rushi.messages.modals;

public class SPAM {
    private int position;
    private Boolean spam;

    public SPAM() {
    }

    public SPAM(int position, Boolean spam) {
        this.position = position;
        this.spam = spam;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Boolean getSpam() {
        return spam;
    }

    public void setSpam(Boolean spam) {
        this.spam = spam;
    }
}
