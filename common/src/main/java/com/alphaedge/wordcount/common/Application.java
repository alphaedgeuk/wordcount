package com.alphaedge.wordcount.common;

import com.alphaedge.wordcount.common.api.Commands;
import com.alphaedge.wordcount.common.commands.CommandSender;
import com.typesafe.config.Config;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.RollCycles;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;

public abstract class Application {

    private final ChronicleQueue commandsQueue;
    private final ChronicleQueue eventQueue;
    private final CommandSender commandSender;

    public Application(Config config) {
        commandsQueue = SingleChronicleQueueBuilder
                .binary(config.getString("chronicle.commands"))
                .rollCycle(RollCycles.DAILY).build();

        eventQueue = SingleChronicleQueueBuilder
                .binary(config.getString("chronicle.events"))
                .rollCycle(RollCycles.DAILY).build();

        commandSender = new CommandSender(getCommandsAppender());
    }

    public ChronicleQueue getCommandsQueue() {
        return commandsQueue;
    }

    public ChronicleQueue getEventQueue() {
        return eventQueue;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    private Commands getCommandsAppender() {
        return commandsQueue.acquireAppender().methodWriter(Commands.class);
    }
}
