package com.alphaedge.wordcount.app;

import com.alphaedge.wordcount.common.api.Commands;
import com.alphaedge.wordcount.common.commands.CommandSender;
import com.alphaedge.wordcount.common.events.WordAdded;
import com.alphaedge.wordcount.common.events.WordCountQueried;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class WordCountQueryServiceTest {

    private WordCountQueryService wordCountQueryService;
    private CommandSender commandSender;
    private EventReader eventReader;
    private Commands commands;

    @Before
    public void setUp() {
        commands = Mockito.mock(Commands.class);
        commandSender = new CommandSender(commands);
        eventReader = Mockito.mock(EventReader.class);
        wordCountQueryService = new WordCountQueryService(commandSender, eventReader);

        Mockito.when(eventReader.readOne()).thenReturn(true);
    }

    @Test
    public void testAddWord() {
        WordAdded wordAdded = new WordAdded().word("test");
        //noinspection ResultOfMethodCallIgnored
        Mockito.doReturn(wordAdded).when(eventReader).getLastEvent();

        String result = wordCountQueryService.addWord("test");

        assertThat(result, equalTo("!com.alphaedge.wordcount.common.events.WordAdded {\n" +
                "  word: test\n" +
                "}\n"));
    }

    @Test
    public void testWordCountQueried() {
        WordCountQueried wordCountQueried = new WordCountQueried();
        wordCountQueried.word("test");
        //noinspection ResultOfMethodCallIgnored
        Mockito.doReturn(wordCountQueried).when(eventReader).getLastEvent();

        String result = wordCountQueryService.getWordCount("test");

        assertThat(result, equalTo("!com.alphaedge.wordcount.common.events.WordCountQueried {\n" +
                "  count: 0,\n" +
                "  word: test\n" +
                "}\n"));
    }

}