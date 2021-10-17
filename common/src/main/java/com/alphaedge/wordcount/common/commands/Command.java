package com.alphaedge.wordcount.common.commands;

import net.openhft.chronicle.wire.AbstractCommonMarshallable;

import java.util.function.Consumer;

public class Command<C> extends AbstractCommonMarshallable {
    private transient Consumer<C> appender;

    public C appender(Consumer<C> appender) {
        this.appender = appender;
        return (C) this;
    }

    public void send() {
        appender.accept((C) this);
    }
}
