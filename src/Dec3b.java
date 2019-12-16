import com.google.common.collect.Iterables;
import lombok.Data;
import lombok.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

public class Dec3b {
    public static void main(String[] args) throws IOException {
        final var input = input();
        final var wires = constructWires(input);
        //wires.forEach(System.out::println);
        final var crossings = calculateIntersections(wires);

        crossings.stream().min(Comparator.comparing(i -> i.steps)).ifPresent(intersection ->
                System.out.println(intersection.point + ": " + intersection.point.manhattan() + ": " + intersection.steps));
    }

    private static List<Intersection> calculateIntersections(List<Wire> wires) {
        final var intersections = new ArrayList<Intersection>();
        for (Wire wire : wires) {
            for (Wire otherWire : wires) {
                intersections.addAll(wire.intersections(otherWire));
            }
        }
        return intersections;
    }

    private static List<Wire> constructWires(List<List<String>> input) {
        return input.stream()
                .map(Dec3b::wireFromDirectives)
                .collect(Collectors.toList());
    }

    private static Wire wireFromDirectives(List<String> directives) {
        final var wire = new Wire(new ArrayList<>());
        wire.points.add(new Point(0, 0));
        for (String directive : directives) {
            addWireSegment(wire, directive);
        }
        return wire;
    }

    private static void addWireSegment(Wire wire, String directive) {
        final var length = Integer.parseInt(directive.substring(1));
        final var current = Iterables.getLast(wire.points);
        switch (directive.substring(0, 1)) {
            case "L":
                createPoints(wire, length, current, -1, 0);
                break;
            case "U":
                createPoints(wire, length, current, 0, 1);
                break;
            case "R":
                createPoints(wire, length, current, 1, 0);
                break;
            case "D":
                createPoints(wire, length, current, 0, -1);
                break;
            default:
                throw new IllegalStateException(directive);
        }
    }

    private static void createPoints(Wire wire, int length, Point current, int deltaX, int deltaY) {
        System.out.println(format("%d, %s, %d, %d", length, current, deltaX, deltaY));
        for (int i = 1; i <= length; i++) {
            wire.points.add(new Point(current.x + (i * deltaX), current.y + (i * deltaY)));
        }
    }

    private static List<List<String>> input() throws IOException {
        return read()
                .map(line -> line.split(","))
                .map(List::of)
                .collect(Collectors.toList());
    }

    private static Stream<String> read() throws IOException {
        return Files.lines(Paths.get("3.txt"));
        /*return List.of(
                "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51",
                "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
        ).stream();*/
        /*return List.of(
                "R75,D30,R83,U83,L12,D49,R71,U7,L72",
                "U62,R66,U55,R34,D71,R55,D58,R83"
        ).stream();*/
        /*return List.of(
                "R8,U5,L5,D3",
                "U7,R6,D4,L4"
        ).stream();*/
    }

    @Data
    private static class Intersection {
        final Point point;
        final int steps;
    }

    @Value
    private static class Point {
        int x;
        int y;

        public int manhattan() {
            return Math.abs(x) + Math.abs(y);
        }
    }

    @Data
    private static class Wire {
        final List<Point> points;

        public List<Intersection> intersections(Wire other) {
            if (this == other) {
                return Collections.emptyList();
            }
            final var intersections = new ArrayList<Intersection>();
            for (int i = 1; i < points.size(); i++) {
                //System.out.println(points.get(i));
                for (int j = 1; j < other.getPoints().size(); j++) {
                    if (points.get(i).equals(other.getPoints().get(j))) {
                        intersections.add(new Intersection(points.get(i), i + j));
                    }
                }
            }
            return intersections;
        }
    }
}
