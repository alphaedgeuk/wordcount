package com.alphaedge.wordcount.app;

import com.alphaedge.wordcount.common.Application;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.javalin.Javalin;

public class WebAppMain extends Application {

    public WebAppMain(Config config) {
        super(config);
    }

    public static void main(String[] args) {

        WebAppMain application = new WebAppMain(ConfigFactory.load());

        EventReader eventReader = new EventReader(application.getEventQueue());

        WordCountQueryService wordCountQueryService = new WordCountQueryService(
                application.getCommandSender(),
                eventReader);


        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.result("Hello World"));

        app.post("/add/{word}", ctx -> ctx.result(wordCountQueryService.addWord(ctx.pathParam("word"))));
        app.get("/get/{word}", ctx -> ctx.result(wordCountQueryService.getWordCount(ctx.pathParam("word"))));
    }
}
