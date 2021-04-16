package com.ohooki.cardinal.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LinuxBridge {
    private final ExecutorService executor = Executors.newFixedThreadPool(1);


    public void mkdir(String path) {
        exec(new String[]{"mkdir", path});
    }


    public void rm(String path, boolean folder) {
        exec(new String[]{"rm", folder ? "-Rf" : null, path});
    }


    public void cp(String from, String to, boolean recursively) {
        exec(new String[]{"cp", recursively ? "-R" : null, from, to});
    }


    public void mv(String from, String to) {
        exec(new String[]{"mv", from, to});
    }


    public void dockerRun(String containerName, int port) {
        exec(new String[]{
                "docker", "run", "--name", containerName, "-d", "-p", port + ":25565", "-e", "EULA=TRUE", "-e",
                "TYPE=SPIGOT", "-e", "VERSION=1.8-R0.1-SNAPSHOT-latest", "-v", "/cardinal/tmp/" + containerName + "/:/data", "itzg/minecraft-server"});
    }


    public void exec(String[] commands) {
        this.executor.submit(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(commands);

                pb.redirectErrorStream(true);

                Process p = pb.start();

                p.waitFor();
            } catch (InterruptedException | java.io.IOException e) {
                e.printStackTrace();
            }
        });
    }
}
