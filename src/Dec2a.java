import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;

public class Dec2a {
    public static void main(String[] args) throws IOException {
        final var program = input();
        final var computer = new Computer(program);
        while (computer.nextInstruction()) {
            System.out.println(computer.pos(0));
        }

        System.out.println("Result: " + computer.pos(0));
    }

    private static List<Integer> input() throws IOException {
        final var program = Arrays.stream(Files.readString(Paths.get("2a.txt"))
                .split(","))
                .map(String::trim)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        program.set(1, 12);
        program.set(2, 2);
        return program;
        //return new ArrayList<>(List.of(1,1,1,4,99,5,6,0,99));
    }

    private static class Computer {
        private final List<Integer> originalProgram;
        private List<Integer> program;
        private int programPointer = 0;

        private Computer(List<Integer> program) {
            this.originalProgram = program;
            reset();
        }

        int pos(int pos) {
            return program.get(pos);
        }

        void reset() {
            program = originalProgram;
        }

        boolean nextInstruction() {
            final var operator = operator();
            if (operator == null) {
                return false;
            }
            program.set(resultAt(), calculate(operator));
            advance();
            return true;
        }

        int calculate(IntBinaryOperator op) {
            return op.applyAsInt(operand1(), operand2());
        }

        IntBinaryOperator operator() {
            switch (program.get(programPointer)) {
                case 1:
                    return Math::addExact;
                case 2:
                    return Math::multiplyExact;
                case 99:
                    return null;
            }
            throw new IllegalStateException(String.valueOf(program.get(programPointer)));
        }

        int operand1() {
            return program.get(program.get(programPointer + 1));
        }

        int operand2() {
            return program.get(program.get(programPointer + 2));
        }

        int resultAt() {
            return program.get(programPointer + 3);
        }

        void advance() {
            programPointer += 4;
        }
    }
}
