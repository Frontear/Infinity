package org.frontear.infinity.commands.impl;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import lombok.*;
import org.frontear.framework.client.impl.Client;
import org.frontear.infinity.commands.Command;

public final class Dump extends Command {
    public Dump() {
        super("Dumps a full stacktrace of all active threads to the logs.");
    }

    @Override
    public void process(@NonNull final String[] args) throws Exception {
        val dump = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
        val file = new File(Client.WORKING_DIRECTORY,
            "${org.frontear.infinity.Infinity.inst().getInfo().getName().toLowerCase()}.dmp");

        try (val writer = new PrintWriter(file)) {
            Arrays.stream(dump).forEach(x -> {
                writer.println("Dumping \"${x.getThreadName()}\":");
                val trace = x.getStackTrace();
                Arrays.stream(trace).forEach(z -> {
                    writer.println("\tat $z");
                });
            });

            sendMessage("Dumped stack trace data to ${file.getAbsolutePath()}");
        }
        catch (IOException e) {
            sendMessage("Failed to dump [${e.getSimpleName()}]");
            e.printStackTrace();
        }
    }
}
