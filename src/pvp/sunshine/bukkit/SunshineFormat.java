package pvp.sunshine.bukkit;

import java.text.DecimalFormat;

public class SunshineFormat {

    private final static DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

    public static String format(int value) {
        return decimalFormat.format(value);
    }

    public static String format(double value) {
        return decimalFormat.format(value);
    }
}
