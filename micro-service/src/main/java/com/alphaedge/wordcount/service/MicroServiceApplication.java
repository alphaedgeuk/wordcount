package com.alphaedge.wordcount.service;

import com.alphaedge.wordcount.common.Application;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class MicroServiceApplication extends Application {
    public MicroServiceApplication(Config config) {
        super(config);
    }

    public static void main(String[] args) {
        MicroServiceApplication application = new MicroServiceApplication(ConfigFactory.load());
    }
}
