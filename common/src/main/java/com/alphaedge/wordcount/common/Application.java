package com.alphaedge.wordcount.common;

import com.typesafe.config.Config;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.RollCycles;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;

public abstract class Application {

    private final ChronicleQueue commandsQueue;
    private final ChronicleQueue eventQueue;

    public Application(Config config) {
        commandsQueue = SingleChronicleQueueBuilder
                .binary(config.getString("chronicle.commands"))
                .rollCycle(RollCycles.DAILY).build();

        eventQueue = SingleChronicleQueueBuilder
                .binary(config.getString("chronicle.events"))
                .rollCycle(RollCycles.DAILY).build();
    }
}
