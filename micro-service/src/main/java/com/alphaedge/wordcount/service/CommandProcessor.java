package com.alphaedge.wordcount.service;

import com.alphaedge.wordcount.common.api.Commands;
import com.alphaedge.wordcount.common.commands.AddWord;
import com.alphaedge.wordcount.common.commands.GetWordCount;
import com.alphaedge.wordcount.common.events.WordAddError;
import com.alphaedge.wordcount.common.events.WordAdded;
import com.alphaedge.wordcount.common.events.WordCountQueried;
import com.alphaedge.wordcount.lib.Translator;
import com.alphaedge.wordcount.lib.WordCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandProcessor implements Commands {

    private static final Logger LOG = LoggerFactory.getLogger(CommandProcessor.class);

    private final WordAdded wordAddedFlyWeight = new WordAdded();
    private final WordCountQueried wordCountQueriedFlyWeight = new WordCountQueried();
    private final WordAddError wordAddErrorFlyWeight = new WordAddError();

    private final Translator translator = new BasicTranslator();
    private final WordCounter wordCounter = new WordCounter(translator);

    @Override
    public void addWord(AddWord addWord) {
        String word = addWord.word();
        LOG.info("Adding word {}", word);
        boolean result = wordCounter.addWord(word);

        if (!result) {
            wordAddErrorFlyWeight
                    .error("Unable to add word")
                    .word(word)
                    .send();
            fjf unit test this
            return;
        }

        wordAddedFlyWeight
                .word(word)
                .send();
    }

    @Override
    public void getWordCount(GetWordCount getWordCount) {
        String word = getWordCount.word();
        LOG.info("Querying count for word {}", word);
        long count = wordCounter.getCount(word);
        wordCountQueriedFlyWeight
                .count(count)
                .word(word)
                .send();
    }
}
