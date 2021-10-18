package com.alphaedge.wordcount.app;

import com.alphaedge.wordcount.common.commands.CommandSender;

public class WordCountQueryService {

    private final CommandSender commandSender;
    private final EventReader eventReader;

    public WordCountQueryService(CommandSender commandSender, EventReader eventReader) {
        this.commandSender = commandSender;
        this.eventReader = eventReader;
    }

    public String addWord(String word) {
        commandSender
                .addWord(word)
                .send();

        return readOneEvent();
    }

    public String getWordCount(String word) {
        commandSender
                .getWordCount(word)
                .send();

        return readOneEvent();
    }

    private String readOneEvent() {
        //noinspection StatementWithEmptyBody
        while (!eventReader.readOne()) {
            // In practise there would be a timeout here
            ;
        }

        return eventReader.getLastEvent().toString();
    }

}
