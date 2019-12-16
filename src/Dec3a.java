import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

public class Dec3a {
    public static void main(String[] args) throws IOException {
        final var input = input();
        final var wires = constructWires(input);
        final var crossings = calculateCrossings(wires);

        crossings.stream().min(Comparator.comparing(Point::manhattan)).ifPresent(c -> System.out.println(c + ": " + c.manhattan()));
    }

    private static List<Point> calculateCrossings(List<List<Wire>> wires) {
        final var crossings = new ArrayList<Point>();
        for (List<Wire> wire : wires) {
            for (List<Wire> otherWire : wires) {
                if (wire != otherWire) {
                    crossings.addAll(findCrossings(wire, otherWire));
                }
            }
        }
        return crossings;
    }

    private static List<Point> findCrossings(List<Wire> wire1, List<Wire> wire2) {
        final var crossings = new ArrayList<Point>();
        for (Wire part1 : wire1) {
            for (Wire part2 : wire2) {
                part1.crossesAt(part2).filter(p -> !p.origo()).ifPresent(e -> {
                    System.out.println(format("Crossing: %s - %s: %s", part1, part2, e));
                    crossings.add(e);
                });
            }
        }
        return crossings;
    }

    private static List<List<Wire>> constructWires(List<List<String>> input) {
        return input.stream()
                .map(Dec3a::wiresFromDirectives)
                .collect(Collectors.toList());
    }

    private static List<Wire> wiresFromDirectives(List<String> directives) {
        final var wires = new ArrayList<Wire>(directives.size());
        var current = new Point(0, 0);
        for (String directive : directives) {
            final var wire = wire(current, directive);
            wires.add(wire);
            current = wire.p2;
        }
        return wires;
    }

    private static Wire wire(Point from, String directive) {
        final var length = Integer.parseInt(directive.substring(1));
        switch (directive.substring(0, 1)) {
            case "L":
                return new HorizontalWire(from, -length);
            case "U":
                return new VerticalWire(from, length);
            case "R":
                return new HorizontalWire(from, length);
            case "D":
                return new VerticalWire(from, -length);
        }
        throw new IllegalStateException(directive);
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
    }

    private static class Point {
        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        private boolean origo() {
            return x == 0 && y == 0;
        }

        private int manhattan() {
            return Math.abs(x) + Math.abs(y);
        }
    }

    private static class HorizontalWire extends Wire {
        private HorizontalWire(Point start, int length) {
            super(start, new Point(start.x + length, start.y));
        }

        @Override
        protected Optional<Point> crossesAt(Wire other) {
            if (other instanceof HorizontalWire) {
                return Optional.empty();
            }
            if ((xmin() <= other.xmin() && xmax() >= other.xmin()) && (ymin() <= other.ymax() && ymin() >= other.ymin())) {
                return Optional.of(new Point(other.xmin(), ymin()));
            }
            return Optional.empty();
        }
    }

    private static class VerticalWire extends Wire {
        private VerticalWire(Point start, int length) {
            super(start, new Point(start.x, start.y + length));
        }

        @Override
        protected Optional<Point> crossesAt(Wire other) {
            if (other instanceof VerticalWire) {
                return Optional.empty();
            }
            if ((ymin() <= other.ymin() && ymax() >= other.ymin()) && (xmin() <= other.xmax() && xmin() >= other.xmin())) {
                return Optional.of(new Point(xmin(), other.ymin()));
            }
            return Optional.empty();
        }
    }

    private abstract static class Wire {
        private final Point p1;
        private final Point p2;

        private Wire(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        protected int xmin() {
            return Math.min(p1.x, p2.x);
        }

        protected int xmax() {
            return Math.max(p1.x, p2.x);
        }

        protected int ymin() {
            return Math.min(p1.y, p2.y);
        }

        protected int ymax() {
            return Math.max(p1.y, p2.y);
        }

        protected abstract Optional<Point> crossesAt(Wire other);

        @Override
        public String toString() {
            return getClass().getSimpleName() + "{" +
                    "p1=" + p1 +
                    ", p2=" + p2 +
                    '}';
        }
    }
}
