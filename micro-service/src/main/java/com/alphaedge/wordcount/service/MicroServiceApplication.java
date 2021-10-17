package com.alphaedge.wordcount.service;

import com.alphaedge.wordcount.common.Application;
import com.alphaedge.wordcount.common.api.Events;
import com.alphaedge.wordcount.lib.BasicTranslator;
import com.alphaedge.wordcount.lib.WordCounter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.openhft.chronicle.bytes.MethodReader;

public class MicroServiceApplication extends Application {

    private final MethodReader methodReader;

    public MicroServiceApplication(Config config) {
        super(config);

        WordCounter wordCounter = new WordCounter(new BasicTranslator());

        Events eventsAppender = getEventQueue().acquireAppender().methodWriter(Events.class);

        CommandProcessor commandProcessor = new CommandProcessor(eventsAppender, wordCounter);

        methodReader = getCommandsQueue().createTailer().methodReader(commandProcessor);

        // Replay all commands in Chronicle history to rebuild state
        //noinspection StatementWithEmptyBody
        while (methodReader.readOne()) {
            ;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MicroServiceApplication application = new MicroServiceApplication(ConfigFactory.load());

        //noinspection InfiniteLoopStatement
        while (true) {
            application.runOne();

            // For latency sensitive application we would not sleep and busy spin here
            Thread.sleep(100);

            // todo: Trap signals to exit loop
        }
    }

    private void runOne() {
        methodReader.readOne();
    }
}
