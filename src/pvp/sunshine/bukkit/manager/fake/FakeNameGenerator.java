package pvp.sunshine.bukkit.manager.fake;

import java.util.Random;

public class FakeNameGenerator {
    private static final String[] CONSONANTS = {"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "y", "z"};
    private static final String[] VOWELS = {"a", "e", "i", "o", "u"};

    public static String generateFakeName() {
        Random random = new Random();
        StringBuilder fakeName = new StringBuilder();
        int length = 4 + random.nextInt(7);

        for (int i = 0; i < length; i++) {
            if (i % 2 == 0) {
                fakeName.append(CONSONANTS[random.nextInt(CONSONANTS.length)]);
            } else {
                fakeName.append(VOWELS[random.nextInt(VOWELS.length)]);
            }
        }

        return fakeName.toString();
    }
}
