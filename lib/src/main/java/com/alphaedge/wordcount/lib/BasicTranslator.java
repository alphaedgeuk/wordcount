package com.alphaedge.wordcount.lib;

import java.util.HashMap;
import java.util.Map;

public class BasicTranslator implements Translator {

    private final Map<String, String> translations = new HashMap<>();

    public BasicTranslator() {
        translations.put("flor", "flower");
        translations.put("blume", "flower");
        translations.put("hoja", "leaf");
        translations.put("semilla", "seed");
    }

    @Override
    public String translate(String word) {
        if (word == null) {
            throw new IllegalStateException("Word cannot be null");
        }
        return translations.getOrDefault(word.trim().toLowerCase(), word);
    }
}
