package com.alphaedge.wordcount.common.commands;

public class GetWordCount extends Command<GetWordCount> {
    String word;

    public String word() {
        return word;
    }

    public GetWordCount word(String word) {
        this.word = word;
        return this;
    }
}
