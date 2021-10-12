package com.alphaedge.wordcount.app;

import io.javalin.Javalin;

public class MicroServiceMain {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.result("Hello World"));
    }
}
