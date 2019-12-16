import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Dec2b {
    public static void main(String[] args) throws IOException {
        final var program = input();
        final var computer = new IntComputer(program);
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                computer.reset(i, j);
                while (computer.nextInstruction()) {
                    ;
                }
                if (computer.pos(0) == 19690720) {
                    System.out.println(format("Found at: %d, %d", i, j));
                    System.exit(0);
                }
            }
        }

        System.out.println("Not found");
    }

    private static List<Integer> input() throws IOException {
        return Arrays.stream(Files.readString(Paths.get("2a.txt"))
                .split(","))
                .map(String::trim)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //return new ArrayList<>(List.of(1,1,1,4,99,5,6,0,99));
    }

}
