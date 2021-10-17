package com.alphaedge.wordcount.common.api;

import com.alphaedge.wordcount.common.events.WordCountQueried;

public interface Events {
    void error(Error error);

    void wordCount(WordCountQueried wordCountQueried);
}
