package io.vakili.tutorial.jcrypto.cli;

import com.amazon.corretto.crypto.provider.AmazonCorrettoCryptoProvider;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import picocli.CommandLine;

import java.security.Provider;
import java.security.Security;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@CommandLine.Command(name = "inspect")
public class InspectProvider implements Runnable {
    private final static Provider ACCP = AmazonCorrettoCryptoProvider.INSTANCE;
    private final static Map<String, Provider> notInstalledProviders = Map.of("BCFIPS",
            new BouncyCastleFipsProvider(),
            ACCP.getName(),
            ACCP);
    @CommandLine.Parameters(index = "0")
    private String provider;

    @CommandLine.Option(names = {"-service"})
    private String service;

    @Override
    public void run() {
        final var p = findProvider(provider);
        if (p == null) {
            System.err.println("Unknown provider: " + provider);
            return;
        }
        inspect(p);
    }

    private Provider findProvider(final String providerName) {
        final var p = notInstalledProviders.get(providerName);
        return p != null ? p : Security.getProvider(providerName);
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
