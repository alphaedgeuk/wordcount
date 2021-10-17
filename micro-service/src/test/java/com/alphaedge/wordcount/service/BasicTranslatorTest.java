package com.alphaedge.wordcount.service;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class BasicTranslatorTest {

    private BasicTranslator basicTranslator;

    @Before
    public void setUp() {
        basicTranslator = new BasicTranslator();
    }

    @Test
    @Parameters({
            "flower, flower",
            "blume, flower",
            "flor, flower",
            "hoja, leaf",
            "semilla, seed",
            "Flor, flower",
            "  Flor, flower",
            "  Flor  , flower"
            })
    public void testTranslations(String word, String translation) {
        assertThat(translation, equalTo(basicTranslator.translate(word)));
    }
}