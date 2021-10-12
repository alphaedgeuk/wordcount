package com.alphaedge.wordcount.lib;

public interface Translator {

    /**
     * @return Either a translation of the word into English or the original string if no translation found
     */
    String translate(String word);
}
