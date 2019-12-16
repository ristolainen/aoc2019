import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Queue;

public class IntComputer3 {
    private List<Integer> program;
    private final Queue<Integer> input;
    private Integer output = null;
    private int programPointer = 0;

    public IntComputer3(List<Integer> program, Queue<Integer> input) {
        this.program = program;
        this.input = input;
    }

    public void runProgram() {
        while (runInstruction()) ;
    }

    public int getOutput() {
        return output;
    }

    private boolean runInstruction() {
        final var instruction = StringUtils.leftPad(String.valueOf(program.get(programPointer)), 5, '0');
        final var operatorNumber = Integer.parseInt(instruction.substring(instruction.length() - 2));
        switch (operatorNumber) {
            case 1:
                final var a1 = createParameter(1, instruction);
                final var a2 = createParameter(2, instruction);
                program.set(parameter(3), a1 + a2);
                advance(4);
                return true;
            case 2:
                final var m1 = createParameter(1, instruction);
                final var m2 = createParameter(2, instruction);
                program.set(parameter(3), m1 * m2);
                advance(4);
                return true;
            case 3:
                final var i = parameter(1);
                program.set(i, input.remove());
                advance(2);
                return true;
            case 4:
                final var o = parameter(1);
                output = program.get(o);
                advance(2);
                return true;
            case 5:
                final var jit = createParameter(1,instruction);
                if (jit != 0) {
                    programPointer = createParameter(2, instruction);
                }
                else {
                    advance(3);
                }
                return true;
            case 6:
                final var jif = createParameter(1, instruction);
                if (jif == 0) {
                    programPointer = createParameter(2, instruction);
                }
                else {
                    advance(3);
                }
                return true;
            case 7:
                final var l1 = createParameter(1, instruction);
                final var l2 = createParameter(2, instruction);
                if (l1 < l2) {
                    program.set(parameter(3), 1);
                } else {
                    program.set(parameter(3), 0);
                }
                advance(4);
                return true;
            case 8:
                final var e1 = createParameter(1, instruction);
                final var e2 = createParameter(2, instruction);
                if (e1 == e2) {
                    program.set(parameter(3), 1);
                } else {
                    program.set(parameter(3), 0);
                }
                advance(4);
                return true;
            case 99:
                return false;
        }
        throw new IllegalStateException(String.valueOf(program.get(programPointer)));
    }

    private Integer parameter(int i) {
        return program.get(programPointer + i);
    }

    private int createParameter(int offset, String instruction) {
        final var index = instruction.length() - 2 - offset;
        final var mode = instruction.charAt(index);
        final var position = programPointer + offset;
        switch (mode) {
            case '0':
                return program.get(program.get(position));
            case '1':
                return program.get(position);
        }
        throw new IllegalStateException();
    }

    void advance(int steps) {
        programPointer += steps;
    }
}
