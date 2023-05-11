package io.vakili.tutorial.jcrypto.cli;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import picocli.CommandLine;

import java.security.Provider;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@CommandLine.Command(name = "inspect")
public class InspectProvider implements Runnable {
    @CommandLine.Parameters(index = "0")
    private String provider;

    @CommandLine.Option(names = {"-service"})
    private String service;

    @Override
    public void run() {
        if ("BCFIPS".equals(provider)) {
            inspect(new BouncyCastleFipsProvider());
        } else {
            System.err.println("Unknown provider: " + provider);
        }
    }

    private void inspect(final Provider p) {
        final Map<String, Set<String>> services = new TreeMap<>();
        p.getServices().forEach(s -> {
            final var type = s.getType();
            final var alg = s.getAlgorithm();
            final var algs = services.get(type);
            final var updatedAlgs = algs == null ? new TreeSet<String>() : algs;
            updatedAlgs.add(alg);
            services.put(type, updatedAlgs);
        });
        final int[] count = {0};
        services.forEach((type, set) -> {
            if (service != null && !type.equals(service)) return;
            count[0] += set.size();
            System.out.println(type);
            System.out.println("=".repeat(type.length()));
            set.forEach(System.out::println);
            System.out.println();
        });
        final var message = service == null ? "Number of services: " : "Number of algorithms: ";
        System.out.println(message + count[0]);
    }
}
