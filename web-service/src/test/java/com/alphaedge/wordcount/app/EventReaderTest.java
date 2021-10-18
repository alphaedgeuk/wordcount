package com.alphaedge.wordcount.app;

import com.alphaedge.wordcount.common.events.WordAddError;
import com.alphaedge.wordcount.common.events.WordAdded;
import com.alphaedge.wordcount.common.events.WordCountQueried;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptTailer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventReaderTest {

    private ChronicleQueue eventQueue;
    private EventReader eventReader;

    @Before
    public void setUp() {
        eventQueue = Mockito.mock(ChronicleQueue.class);
        ExcerptTailer tailer = Mockito.mock(ExcerptTailer.class);
        Mockito.when(eventQueue.createTailer()).thenReturn(tailer);
        Mockito.when(tailer.toEnd()).thenReturn(tailer);
        eventReader = new EventReader(eventQueue);
    }

    @Test
    public void testOnError() {
        WordAddError error = new WordAddError();
        eventReader.error(error);

        assertThat(eventReader.getLastEvent(), equalTo(error));
    }

    @Test
    public void testWordAdded() {
        WordAdded wordAdded = new WordAdded();
        eventReader.wordAdded(wordAdded);

        assertThat(eventReader.getLastEvent(), equalTo(wordAdded));
    }

    @Test
    public void testWordCountQueried() {
        WordCountQueried wordCountQueried = new WordCountQueried();
        eventReader.wordCountQueried(wordCountQueried);

        assertThat(eventReader.getLastEvent(), equalTo(wordCountQueried));
    }
}