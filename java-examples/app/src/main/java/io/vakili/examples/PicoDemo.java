package io.vakili.examples;

import picocli.CommandLine;

@CommandLine.Command(name = "demo")
public class PicoDemo implements Runnable {
    @CommandLine.Parameters(index = "0")
    private String fixArg0;

    @CommandLine.Option(names = {"-optional-arg"})
    private String optionalArg;

    @Override
    public void run() {
        System.out.println(String.format("demo was called with fixed arg '%s'", fixArg0));
        System.out.println(String.format("optional arg was %s", optionalArg == null ? "was not provided." : String.format("'%s'", optionalArg)));
    }
}