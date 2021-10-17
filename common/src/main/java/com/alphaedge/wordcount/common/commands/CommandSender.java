package com.alphaedge.wordcount.common.commands;

import com.alphaedge.wordcount.common.api.Commands;

public class CommandSender {

    private final AddWord addWordFlyWeight = new AddWord();
    private final GetWordCount getWordCountFlyWeight = new GetWordCount();
    private final Commands commandAppender;

    public CommandSender(Commands commandAppender) {
        this.commandAppender = commandAppender;
    }

    public AddWord addWord(String word) {
        return addWordFlyWeight.word(word);
    }

    public GetWordCount getWordCount(String word) {
        return getWordCountFlyWeight.word(word);
    }


}
