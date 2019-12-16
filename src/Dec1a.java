import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.OptionalInt;

public class Dec1a {
    public static void main(String[] args) throws IOException {
        final var input = Files.readAllLines(Paths.get("1a.txt"));
        final var sum = input.stream()
                .mapToInt(Integer::parseInt)
                .map(mass -> mass / 3)
                .map(mass -> mass - 2)
                .reduce(Math::addExact)
                .getAsInt();

        System.out.println(sum);
    }
}
