package io.vakili.examples;

import picocli.CommandLine;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.stream.Stream;

@CommandLine.Command(name = "niobuffer")
public class NioBuffer implements Runnable {
    @CommandLine.Parameters(index = "0")
    private String example;

    @Override
    public void run() {
        switch (example) {
            case "allocate" -> allocate();
            case "wrap" -> wrap();
            case "view" -> view();
            case "write" -> write(false);
            case "flip" -> flip();
            case "clear" -> clear(false);
            case "direct" -> clear(true);
            default -> System.err.println("Unknown example: " + example);
        }
    }

    private void flip() {
        final var strs = Stream.of("Amir", "loves", "Java.");
        final var buffer = CharBuffer.allocate(10);
        final var result = new StringBuffer();
        final var isFirst = new boolean[1];
        isFirst[0] = true;
        strs.forEach(s -> {
            if (isFirst[0]) {
                isFirst[0] = false;
            } else {
                buffer.put(' ');
            }
            System.out.println("Fill the buffer");
            for (int i = 0; i != s.length(); i++) {
                buffer.put(s.charAt(i));
            }
            printBuffer(buffer);
            System.out.println("Flip the buffer");
            buffer.flip();
            printBuffer(buffer);
            System.out.println("Drain the buffer");
            while (buffer.hasRemaining()) {
                result.append(buffer.get());
            }
            printBuffer(buffer);
            System.out.println("Clear the buffer");
            buffer.clear();
            printBuffer(buffer);
        });
        System.out.println(result);
    }

    private void clear(final boolean useDirect) {
        final var buffer = write(useDirect);
        System.out.println("Flip the buffer");
        buffer.flip();
        printBuffer(buffer);
        System.out.println("Clearing the buffer");
        buffer.clear();
        printBuffer(buffer);
    }

    private Buffer write(final boolean useDirect) {
        final var buffer = useDirect ? ByteBuffer.allocateDirect(7) : ByteBuffer.allocate(7);
        printBuffer(buffer);
        buffer.put((byte) 10)
                .put((byte) 20)
                .put((byte) 30);
        printBuffer(buffer);
        for (int i = 0; i < buffer.position(); i++) {
            System.out.println(buffer.get(i));
        }
        return buffer;
    }

    private void view() {
        final var buffer = ByteBuffer.allocate(7);
        printBuffer(buffer);
        final var bufferView = buffer.duplicate();
        bufferView.limit(5);
        printBuffer(buffer);
        printBuffer(bufferView);
        System.out.println("Are the underlying arrays are the same? " + (buffer.array() == bufferView.array() ? "yes" : "no"));
    }

    private void wrap() {
        final var bytes = new byte[200];
        final var buffer = ByteBuffer.wrap(bytes, 10, 50);
        printBuffer(buffer);
        System.out.println("ArrayOffSet: " + buffer.arrayOffset());
    }

    private void allocate() {
        System.out.println("ByteBuffer.allocate(7)");
        final var buffer = ByteBuffer.allocate(7);
        printBuffer(buffer);
        System.out.println("Changing buffer limit to 5");
        buffer.limit(5);
        printBuffer(buffer);
        System.out.println("Changing buffer limit to 3");
        buffer.limit(3);
        printBuffer(buffer);
        System.out.println("Changing buffer limit to 5");
        buffer.limit(5);
        printBuffer(buffer);
    }

    private static void printBuffer(final Buffer buffer) {
        System.out.println("     Buffer: " + buffer);
        System.out.println("   IsDirect: " + (buffer.isDirect() ? "yes" : "no"));
        System.out.println("   HasArray: " + (buffer.hasArray() ? "yes" : "no"));
        System.out.println("   Capacity: " + buffer.capacity());
        System.out.println("      Limit: " + buffer.limit());
        System.out.println("   Position: " + buffer.position());
        System.out.println("  Remaining: " + buffer.remaining());
    }
}
