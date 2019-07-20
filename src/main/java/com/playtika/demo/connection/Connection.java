package com.playtika.demo.connection;

import static com.playtika.demo.utils.Utils.log;

/**
 * Simulated incoming connection from client.
 */
public class Connection {

    public void write(Object obj) {
        log("-> Writing to connection '{}'", obj);
    }
}
