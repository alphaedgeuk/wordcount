package com.alphaedge.wordcount.common.events;

import net.openhft.chronicle.wire.AbstractCommonMarshallable;

import java.util.function.Consumer;

public class Event<E> extends AbstractCommonMarshallable {
    private transient Consumer<E> appender;

    public E appender(Consumer<E> appender) {
        this.appender = appender;
        return (E) this;
    }

    public void send() {
        appender.accept((E) this);
    }

}
