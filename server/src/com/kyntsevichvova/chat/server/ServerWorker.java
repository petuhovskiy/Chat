package com.kyntsevichvova.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerWorker implements Runnable {

    private Server server;
    private BufferedReader br;
    private Thread thread;

    public ServerWorker(Server server) {
        this.server = server;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
        while (!Thread.interrupted()) {
            try {
                String s = br.readLine();
                s = s.toLowerCase();
                if (s.startsWith("/shutdown")) {
                    server.shutDown();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
