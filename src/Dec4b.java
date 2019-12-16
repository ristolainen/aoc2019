import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import java.io.IOException;

public class Dec4b {
    public static void main(String[] args) throws IOException {
        var lower = 153517;
        var upper = 630395;

        var counter = 0;
        for (int i = lower; i <= upper; i++) {
            if (isPassword(i)) {
                counter++;
            }
        }

        System.out.println(isPassword(112233));
        System.out.println(isPassword(123444));
        System.out.println(isPassword(111122));

        System.out.println(counter);
    }

    private static boolean isPassword(int candidate) {
        final Multiset<Character> groups = HashMultiset.create();
        final var str = String.valueOf(candidate);
        for (int i = 0; i < 6; i++) {
            if (i > 0 && str.charAt(i) < str.charAt(i-1)) {
                return false;
            }
            groups.add(str.charAt(i));
        }
        return groups.entrySet().stream()
                .map(Multiset.Entry::getCount)
                .anyMatch(count -> count == 2);
    }
}
