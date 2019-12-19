import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Dec8a {
    public static void main(String[] args) throws IOException {
        final var data = input();
        int[] mins = {Integer.MAX_VALUE, 0, 0};
        for (List<Integer> layer : Iterables.partition(data, 25 * 6)) {
            int[] counts = {0, 0, 0};
            for (Integer v : layer) {
                counts[v]++;
            }
            if (counts[0] < mins[0]) {
                mins = counts;
            }
        }
        System.out.println(mins[1] * mins[2]);
    }

    private static List<Integer> input() throws IOException {
        return Splitter.fixedLength(1).splitToList(Files.readString(Paths.get("8.txt")))
                .stream()
                .filter(StringUtils::isNotBlank)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
