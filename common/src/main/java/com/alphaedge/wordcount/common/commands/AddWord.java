package com.alphaedge.wordcount.common.commands;

public class AddWord extends Command<AddWord> {
    String word;

    public String word() {
        return word;
    }

    public AddWord word(String word) {
        this.word = word;
        return this;
    }
}
