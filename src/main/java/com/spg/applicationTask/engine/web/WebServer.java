package com.spg.applicationTask.engine.web;

/**
 * Provides basic methods for a web server.
 */
public interface WebServer {

    /**
     * Starts a web server and returns an instance.
     *
     * @return a web server.
     */
    WebServer start();

    /**
     * Stops a web server
     */
    void stop();

    /**
     * Adds controllers
     */

    void addControllers(AbstractController... controllers);
}
