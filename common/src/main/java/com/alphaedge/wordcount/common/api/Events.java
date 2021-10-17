package com.alphaedge.wordcount.common.api;

import com.alphaedge.wordcount.common.events.WordAddError;
import com.alphaedge.wordcount.common.events.WordAdded;
import com.alphaedge.wordcount.common.events.WordCountQueried;

public interface Events {
    void error(WordAddError error);

    void wordAdded(WordAdded wordAdded);

    void wordCountQueried(WordCountQueried wordCountQueried);
}
