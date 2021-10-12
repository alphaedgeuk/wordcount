package com.alphaedge.wordcount.common.events;

public class ErrorEvent extends Event {

    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
