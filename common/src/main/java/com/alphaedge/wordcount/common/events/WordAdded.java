package com.alphaedge.wordcount.common.events;

public class WordAdded extends Event<WordAdded> {
    private String word;

    public String word() {
        return word;
    }

    public WordAdded word(String word) {
        this.word = word;
        return this;
    }
}
