package com.alphaedge.wordcount.lib;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(JUnitParamsRunner.class)
public class WordCounterTest {

    private Translator translator;
    private WordCounter wordCounter;

    @Before
    public void setUp() {
        translator = Mockito.mock(Translator.class);
        Mockito.when(translator.translate(anyString())).thenAnswer((Answer<String>) invocation -> {
            Object[] args = invocation.getArguments();
            return (String) args[0];
        });
        wordCounter = new WordCounter(translator);
    }

    @Test
    public void testWordAdd() {
        assertTrue(wordCounter.addWord("once"));
        assertTrue(wordCounter.addWord("twice"));
        assertTrue(wordCounter.addWord("twice"));
        assertTrue(wordCounter.addWord("thrice"));
        assertTrue(wordCounter.addWord("thrice"));
        assertTrue(wordCounter.addWord("thrice"));


        assertThat(wordCounter.getCount("once"), equalTo(1L));
        assertThat(wordCounter.getCount("twice"), equalTo(2L));
        assertThat(wordCounter.getCount("thrice"), equalTo(3L));
        assertThat(wordCounter.getCount("other"), equalTo(0L));
    }

    @Test
    @Parameters({"test1", "1test", "a test", "!test"})
    public void testWordWithNonAlphaCharsNotAdded(String word) {
        assertFalse(wordCounter.addWord(word));

        assertThat(wordCounter.getCount(word), equalTo(0L));
    }

    @Test
    public void testTranslatorReturnsNull() {
        Mockito.when(translator.translate(any())).thenReturn(null);
        assertFalse(wordCounter.addWord("test"));
    }

    @Test
    @Parameters({
            "flor, flower",
            "blume, flower",
            "word, word"})
    public void testTranslation(String word, String translation) {
        Mockito.when(translator.translate(word)).thenReturn(translation);
        assertTrue(wordCounter.addWord(word));
        assertThat(wordCounter.getCount(translation), equalTo(1L));
        assertThat(wordCounter.getCount(word), equalTo(1L));
    }

}