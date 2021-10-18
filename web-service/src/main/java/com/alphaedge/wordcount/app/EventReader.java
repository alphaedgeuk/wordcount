package com.alphaedge.wordcount.app;

import com.alphaedge.wordcount.common.api.Events;
import com.alphaedge.wordcount.common.events.Event;
import com.alphaedge.wordcount.common.events.WordAddError;
import com.alphaedge.wordcount.common.events.WordAdded;
import com.alphaedge.wordcount.common.events.WordCountQueried;
import net.openhft.chronicle.bytes.MethodReader;
import net.openhft.chronicle.queue.ChronicleQueue;

/**
 * Basic class for capturing events read from Event queue
 */
public class EventReader implements Events {

    private Event<?> lastEvent;
    private final MethodReader methodReader;

    public EventReader(ChronicleQueue eventQueue) {
        methodReader = eventQueue.createTailer().toEnd().methodReader(this);
    }

    @Override
    public void error(WordAddError error) {
        lastEvent = error;
    }

    @Override
    public void wordAdded(WordAdded wordAdded) {
        lastEvent = wordAdded;
    }

    @Override
    public void wordCountQueried(WordCountQueried wordCountQueried) {
        lastEvent = wordCountQueried;
    }

    public boolean readOne() {
        return methodReader.readOne();
    }

    public Event<?> getLastEvent() {
        return lastEvent;
    }
}
