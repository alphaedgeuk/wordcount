package com.alphaedge.wordcount.common.events;

public class WordAddError extends Event<WordAddError> {

    private String error;
    private String word;


    public String error() {
        return error;
    }

    public WordAddError error(String error) {
        this.error = error;
        return this;
    }

    public String word() {
        return word;
    }

    public WordAddError word(String word) {
        this.word = word;
        return this;
    }
}
