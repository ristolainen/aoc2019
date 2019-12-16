import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Dec1b {
    public static void main(String[] args) throws IOException {
        final var input = Files.readAllLines(Paths.get("1a.txt"));
        final var sum = input.stream()
                .mapToInt(Integer::parseInt)
                .map(Dec1b::fuel)
                .reduce(Math::addExact)
                .getAsInt();

        System.out.println(sum);
    }

    private static int fuel(int mass) {
        int consumption = (mass / 3) - 2;
        if (consumption <= 0) {
            return 0;
        }
        return consumption + fuel(consumption);
    }
}
