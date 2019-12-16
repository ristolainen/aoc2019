import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Dec5b {
    public static void main(String[] args) throws IOException {
        final var program = input();
        final var computer = new IntComputer2(program, 5);
        computer.runProgram();
    }

    private static List<Integer> input() throws IOException {
        final var program = Arrays.stream(Files.readString(Paths.get("5.txt"))
                .split(","))
                .map(String::trim)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        return program;
        //return new ArrayList<>(List.of(3,9,8,9,10,9,4,9,99,-1,8));
        //return new ArrayList<>(List.of(3,9,7,9,10,9,4,9,99,-1,8));
        //return new ArrayList<>(List.of(3,3,1108,-1,8,3,4,3,99));
        //return new ArrayList<>(List.of(3,3,1107,-1,8,3,4,3,99));
        //return new ArrayList<>(List.of(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99));
        //return new ArrayList<>(List.of(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9));
        //return new ArrayList<>(List.of(3,3,1105,-1,9,1101,0,0,12,4,12,99,1));
    }
}
