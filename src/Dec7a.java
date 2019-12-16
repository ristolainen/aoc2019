import com.google.common.collect.Collections2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Dec7a {
    public static void main(String[] args) throws IOException {
        final var program = input();
        //create all permutations of 0,1,2,3,4 as a list of lists

        final var permutations = Collections2.permutations(List.of(0, 1, 2, 3, 4));
        int maxOutput = 0;
        for (List<Integer> permutation : permutations) {
            int output = 0;
            for (Integer phase : permutation) {
                final var input = new LinkedList<>(List.of(phase, output));
                final var computer = new IntComputer3(program, input);
                computer.runProgram();
                output = computer.getOutput();
            }
            if (output > maxOutput) {
                maxOutput = output;
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
        //return new ArrayList<>(List.of(1,1,1,4,99,5,6,0,99));
    }
}
