package com.alphaedge.wordcount.lib;

import java.util.Objects;

/**
 * A class to store the number of times a word has been added
 */
class WordInstanceCounter {
    private long count;
    private final String word;

    public WordInstanceCounter(String word) {
        Objects.requireNonNull(word);
        this.word = word;
        count = 0;
    }

    public void increment() {
        count++;
    }

    public long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordInstanceCounter that = (WordInstanceCounter) o;
        return word.equals(that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}
