package com.alphaedge.wordcount.common.events;

public class WordCountQueried extends Event<WordCountQueried> {
    private long count;
    private String word;

    public long count() {
        return count;
    }

    public WordCountQueried count(long count) {
        this.count = count;
        return this;
    }

    public String word() {
        return word;
    }

    public WordCountQueried word(String word) {
        this.word = word;
        return this;
    }
}
