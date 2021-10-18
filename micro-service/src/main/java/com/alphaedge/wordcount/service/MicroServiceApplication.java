package com.alphaedge.wordcount.service;

import com.alphaedge.wordcount.common.Application;
import com.alphaedge.wordcount.common.api.Events;
import com.alphaedge.wordcount.lib.BasicTranslator;
import com.alphaedge.wordcount.lib.WordCounter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.openhft.chronicle.bytes.MethodReader;

public class MicroServiceApplication extends Application {

    public static final int BACK_OFF_MILLIS = 100;
    private final MethodReader methodReader;

    public MicroServiceApplication(Config config) {
        super(config);

        WordCounter wordCounter = new WordCounter(new BasicTranslator());

        CommandProcessor commandProcessor = new CommandProcessor(wordCounter);

        methodReader = getCommandsQueue().createTailer().methodReader(commandProcessor);

        // Replay all commands in Chronicle history to rebuild state
        //noinspection StatementWithEmptyBody
        while (methodReader.readOne()) {
            ;
        }

        Events eventsAppender = getEventQueue().acquireAppender().methodWriter(Events.class);

        // Now replay has finished set the CommandProcessor to append to the Chronicle
        commandProcessor.setEventAppender(eventsAppender);
    }

    public static void main(String[] args) throws InterruptedException {
        MicroServiceApplication application = new MicroServiceApplication(ConfigFactory.load());

        //noinspection InfiniteLoopStatement
        while (true) {
            application.runOne();

            // For latency sensitive application we would not sleep and busy spin instead here
            Thread.sleep(BACK_OFF_MILLIS);

            // todo: Trap signals to exit loop on shutdown
        }
    }

    private void runOne() {
        methodReader.readOne();
    }
}
