import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dec6b {
    public static void main(String[] args) throws IOException {
        final var orbits = input();
        final var myLink = orbitLink(orbits, "YOU");
        final var santasLink = orbitLink(orbits, "SAN");

        System.out.println(myLink);
        System.out.println(santasLink);

        final var common = commonPlanet(myLink, santasLink);
        System.out.println(common);

        final var commonOrbits = nrOfOrbits(orbits, common);
        final var myOrbits = nrOfOrbits(orbits, "YOU") - 1;
        final var santasOrbits = nrOfOrbits(orbits, "SAN") - 1;

        System.out.println((myOrbits - commonOrbits) + (santasOrbits - commonOrbits));
    }

    private static int nrOfOrbits(Map<String, String> orbits, String planet) {
        if (orbits.get(planet).equals("COM")) {
            return 1;
        }
        return 1 + nrOfOrbits(orbits, orbits.get(planet));
    }

    private static List<String> orbitLink(Map<String, String> orbits, String planet) {
        if (orbits.get(planet).equals("COM")) {
            return new ArrayList<>(Collections.singletonList("COM"));
        }
        final var link = orbitLink(orbits, orbits.get(planet));
        link.add(planet);
        return link;
    }

    private static String commonPlanet(List<String> link1, List<String> link2) {
        for (int p = 0; p < link1.size(); p++) {
            if (!link1.get(p).equals(link2.get(p))) {
                return link1.get(p - 1);
            }
        }
        throw new IllegalStateException("No common planet");
    }

    private static Map<String, String> input() throws IOException {
        final Map<String, String> input = new HashMap<>();
        Files.lines(Paths.get("6.txt"))
                .map(s -> s.split("\\)"))
                .forEach(parts -> input.put(parts[1], parts[0]));
        return input;
    }
}
