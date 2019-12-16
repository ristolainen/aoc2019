import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;

public class Dec4a {
    public static void main(String[] args) throws IOException {
        var lower = 153517;
        var upper = 630395;

        var counter = 0;
        for (int i = lower; i <= upper; i++) {
            if (isPassword(i)) {
                counter++;
            }
        }

        System.out.println(counter);
    }

    private static boolean isPassword(int candidate) {
        final var str = String.valueOf(candidate);
        boolean hasPair = false;
        for (int i = 1; i < 6; i++) {
            if (str.charAt(i) < str.charAt(i-1)) {
                return false;
            }
            if (str.charAt(i) == str.charAt(i-1)) {
                hasPair = true;
            }
        }
        return hasPair;
    }
}
