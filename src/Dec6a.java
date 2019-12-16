import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Dec6a {
    public static void main(String[] args) throws IOException {
        final var orbits = input();
        int nrOrbits = 0;
        for (String planet : orbits.keySet()) {
            nrOrbits += nrOfOrbits(orbits, planet);
        }
        System.out.println(nrOrbits);
    }

    private static int nrOfOrbits(Map<String, String> orbits, String planet) {
        if (orbits.get(planet).equals("COM")) {
            return 1;
        }
        return 1 + nrOfOrbits(orbits, orbits.get(planet));
    }

    private static Map<String, String> input() throws IOException {
        final Map<String, String> input = new HashMap<>();
        Files.lines(Paths.get("6.txt"))
                .map(s -> s.split("\\)"))
                .forEach(parts -> input.put(parts[1], parts[0]));
        return input;
    }
}
