import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

class IntComputer {
    private final List<Integer> originalProgram;
    private List<Integer> program;
    private int programPointer = 0;

    IntComputer(List<Integer> program) {
        this.originalProgram = program;
    }

    int pos(int pos) {
        return program.get(pos);
    }

    void reset(int noun, int verb) {
        program = new ArrayList<>(originalProgram);
        program.set(1, noun);
        program.set(2, verb);
        programPointer = 0;
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
