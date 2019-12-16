import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Dec5a {
    public static void main(String[] args) throws IOException {
        final var program = input();
        final var computer = new IntComputer2(program, 1);
        computer.runProgram();
    }

    private static List<Integer> input() throws IOException {
        final var program = Arrays.stream(Files.readString(Paths.get("5.txt"))
                .split(","))
                .map(String::trim)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        return program;
        //return new ArrayList<>(List.of(1,1,1,4,99,5,6,0,99));
    }
}
