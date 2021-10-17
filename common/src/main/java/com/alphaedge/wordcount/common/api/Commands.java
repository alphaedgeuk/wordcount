package com.alphaedge.wordcount.common.api;

import com.alphaedge.wordcount.common.commands.AddWord;
import com.alphaedge.wordcount.common.commands.GetWordCount;

public interface Commands {

    void addWord(AddWord addWord);

    void getWordCount(GetWordCount getWordCount);
}
