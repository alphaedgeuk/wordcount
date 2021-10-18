package com.alphaedge.wordcount.service;

import com.alphaedge.wordcount.common.api.Events;
import com.alphaedge.wordcount.common.events.WordAddError;
import com.alphaedge.wordcount.common.events.WordAdded;
import com.alphaedge.wordcount.common.events.WordCountQueried;

public class NullEventsAppender implements Events {
    @Override
    public void error(WordAddError error) {
    }

    @Override
    public void wordAdded(WordAdded wordAdded) {
    }

    @Override
    public void wordCountQueried(WordCountQueried wordCountQueried) {
    }
}
