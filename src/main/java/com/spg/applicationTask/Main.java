package com.spg.applicationTask;

import com.spg.applicationTask.controller.JustController;
import com.spg.applicationTask.engine.ServerConfig;

import java.io.IOException;

public class Main {

    ConnectionPool pool;
    public static void main(String[] args) throws IOException {

//        Connection connection = ConnectionPool.INSTANCE.getConnection();
//
//        Server server = new Server(metaConfig());
//        server.start();
//
//        while (true) {
//
//        }
    }

    public static ServerConfig metaConfig() {

        return new ServerConfig.Builder()
                .controller(new JustController.Builder("/1").build())
                .build();
    }
}
