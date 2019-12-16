import com.google.common.collect.Collections2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Dec7b {
    public static void main(String[] args) throws IOException {
        final var permutations = Collections2.permutations(List.of(5, 6, 7, 8, 9));
        int maxOutput = 0;
        for (List<Integer> permutation : permutations) {
            final var amplifiers = List.of(
                    new IntComputer4(input(), permutation.get(0)),
                    new IntComputer4(input(), permutation.get(1)),
                    new IntComputer4(input(), permutation.get(2)),
                    new IntComputer4(input(), permutation.get(3)),
                    new IntComputer4(input(), permutation.get(4))
            );
            int signal = 0;
            boolean programEnd = false;
            while (!programEnd) {
                for (int i = 0; i < 5; i++) {
                    final var amplifier = amplifiers.get(i);
                    final var amplifierOutput = amplifier.runToOutput(signal);
                    System.out.println(amplifierOutput);
                    if (amplifierOutput != null) {
                        signal = amplifierOutput;
                    } else {
                        programEnd = true;
                    }
                }
            }
            if (signal > maxOutput) {
                maxOutput = signal;
            }
        }
        System.out.println(maxOutput);
    }

    private static List<Integer> input() throws IOException {
        final var program = Arrays.stream(Files.readString(Paths.get("7.txt"))
                .split(","))
                .map(String::trim)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        return program;
        //return new ArrayList<>(List.of(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26, 27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5));
    }
}
