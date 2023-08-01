package io.vakili.examples;

import picocli.CommandLine;

@CommandLine.Command(subcommands = {PicoDemo.class, NioBuffer.class})
public class Cli {

    public static void main(String[] args) {
        new CommandLine(new Cli()).execute(args);
    }
}
