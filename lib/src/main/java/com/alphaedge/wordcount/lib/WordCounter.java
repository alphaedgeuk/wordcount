package com.alphaedge.wordcount.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class to count the number of occurrences of a word with the
 * same semantic meaning across multiple languages
 *
 * Note this class is not thread-safe
 */
public class WordCounter {

    private static final Logger LOG = LoggerFactory.getLogger(WordCounter.class);

    private final Translator translator;

    private final Map<String, WordInstanceCounter> wordInstanceCounterMap = new HashMap<>();

    public WordCounter(Translator translator) {
        Objects.requireNonNull(translator);
        this.translator = translator;
    }

    /**
     * Increments the count for a given word or its translation
     * @param word the word to increment count for
     * @return true if word count was successfully incremented
     */
    public boolean addWord(String word) {

        boolean allLetters = word.chars().allMatch(Character::isLetter);
        if (!allLetters) {
            LOG.debug("Not adding word {}", word);
            return false;
        }

        String translated = getTranslation(word);

        LOG.debug("Word {} translated to {}", word, translated);
        WordInstanceCounter wordInstanceCounter = wordInstanceCounterMap.computeIfAbsent(translated, WordInstanceCounter::new);

        wordInstanceCounter.increment();

        return true;
    }

    /**
     * Returns the number of occurrences of a word or it's translation.
     *
     * e.g. if the following word are added "flor" & "flower" then querying the count
     * for both "flor" and "flower" will return 2.
     *
     * @param word the word or its translation
     * @return the number of occurrences of the word or it's translation
     */
    public long getCount(String word) {

        // note.. a bit of an assumption here that the word should be translated
        // before looking up the count. The spec does say...
        // "... should treat same words written in different languages as the same word ..."
        String translated = getTranslation(word);

        WordInstanceCounter wordInstanceCounter = wordInstanceCounterMap.get(translated);
        return wordInstanceCounter == null ? 0 : wordInstanceCounter.getCount();
    }

    private String getTranslation(String word) {
        String translated = translator.translate(word);
        if (translated == null) {
            throw new IllegalStateException(String.format("Translator returned null for word %s", word));
        }
        return translated;
    }
}
