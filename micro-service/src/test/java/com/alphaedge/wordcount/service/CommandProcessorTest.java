package com.alphaedge.wordcount.service;

import com.alphaedge.wordcount.common.api.Events;
import com.alphaedge.wordcount.common.commands.AddWord;
import com.alphaedge.wordcount.common.commands.GetWordCount;
import com.alphaedge.wordcount.common.events.WordAddError;
import com.alphaedge.wordcount.common.events.WordAdded;
import com.alphaedge.wordcount.common.events.WordCountQueried;
import com.alphaedge.wordcount.lib.WordCounter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

public class CommandProcessorTest {

    private WordCounter wordCounter;
    private Events events;
    private CommandProcessor commandProcessor;

    @Before
    public void setUp() {
        events = Mockito.mock(Events.class);
        wordCounter = Mockito.mock(WordCounter.class);
        commandProcessor = new CommandProcessor(wordCounter);
    }

    @Test
    public void testAddWord() {
        commandProcessor.setEventAppender(events);
        Mockito.when(wordCounter.addWord(any())).thenReturn(true);

        AddWord addWord = new AddWord();
        addWord.word("word");
        commandProcessor.addWord(addWord);

        ArgumentCaptor<WordAdded> captor = ArgumentCaptor.forClass(WordAdded.class);
        Mockito.verify(events).wordAdded(captor.capture());

        WordAdded wordAdded = captor.getValue();
        assertThat(wordAdded.word(), equalTo("word"));

        Mockito.verifyNoMoreInteractions(events);
    }

    @Test
    public void testGetWordCount() {
        commandProcessor.setEventAppender(events);
        Mockito.when(wordCounter.getCount(eq("word"))).thenReturn(42L);

        GetWordCount getWordCount = new GetWordCount();
        getWordCount.word("word");
        commandProcessor.getWordCount(getWordCount);

        ArgumentCaptor<WordCountQueried> captor = ArgumentCaptor.forClass(WordCountQueried.class);
        Mockito.verify(events).wordCountQueried(captor.capture());

        WordCountQueried wordCountQueried = captor.getValue();
        assertThat(wordCountQueried.word(), equalTo("word"));
        assertThat(wordCountQueried.count(), equalTo(42L));

        Mockito.verifyNoMoreInteractions(events);
    }

    @Test
    public void testErrorSentOnWordCountReturningFalse() {
        commandProcessor.setEventAppender(events);
        Mockito.when(wordCounter.addWord(any())).thenReturn(false);

        AddWord addWord = new AddWord();
        addWord.word("word");
        commandProcessor.addWord(addWord);

        ArgumentCaptor<WordAddError> captor = ArgumentCaptor.forClass(WordAddError.class);
        Mockito.verify(events).error(captor.capture());

        WordAddError error = captor.getValue();
        assertThat(error.error(), equalTo("Unable to add word"));
        assertThat(error.word(), equalTo("word"));

        Mockito.verifyNoMoreInteractions(events);
    }


    @Test
    public void testWordCountStateUpdatedBeforeEventAppenderSet() {
        AddWord addWord = new AddWord();
        addWord.word("word");
        commandProcessor.addWord(addWord);
        Mockito.verify(wordCounter).addWord("word");

        GetWordCount getWordCount = new GetWordCount();
        getWordCount.word("word");
        commandProcessor.getWordCount(getWordCount);
        Mockito.verify(wordCounter).getCount("word");

        Mockito.verifyNoMoreInteractions(events);
    }

}