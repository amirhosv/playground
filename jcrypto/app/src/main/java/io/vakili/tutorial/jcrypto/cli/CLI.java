package io.vakili.tutorial.jcrypto.cli;

import picocli.CommandLine;

@CommandLine.Command(subcommands = {InspectProvider.class})
public class CLI {
    public static void main(String[] args) throws Exception {
        new CommandLine(new CLI()).execute(args);
    }
}
