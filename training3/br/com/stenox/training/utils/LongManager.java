// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils;

public class LongManager
{
    public static long stringToLong(final String paramOfString) {
        final String[] timeString = paramOfString.split(",");
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        String[] arrayOfString;
        for (int templateInteger = (arrayOfString = timeString).length, integer = 0; integer < templateInteger; ++integer) {
            final String string = arrayOfString[integer];
            if (string.contains("d") || string.contains("D")) {
                day = Integer.parseInt(string.replace("d", "").replace("D", ""));
            }
            if (string.contains("h") || string.contains("H")) {
                hour = Integer.parseInt(string.replace("h", "").replace("H", ""));
            }
            if (string.contains("m") || string.contains("M")) {
                minute = Integer.parseInt(string.replace("m", "").replace("M", ""));
            }
            if (string.contains("s") || string.contains("S")) {
                second = Integer.parseInt(string.replace("s", "").replace("S", ""));
            }
        }
        return convert(day, hour, minute, second);
    }
    
    public static String formatLong(final long paramOfLong) {
        String message = "";
        final long now = System.currentTimeMillis();
        final long diff = paramOfLong - now;
        int seconds = (int)(diff / 1000L);
        if (seconds >= 86400) {
            final int days = seconds / 86400;
            seconds %= 86400;
            if (days == 1) {
                message = message + days + " dia ";
            }
            else {
                message = message + days + " dias ";
            }
        }
        if (seconds >= 3600) {
            final int hours = seconds / 3600;
            seconds %= 3600;
            if (hours == 1) {
                message = message + hours + " hora ";
            }
            else {
                message = message + hours + " horas ";
            }
        }
        if (seconds >= 60) {
            final int min = seconds / 60;
            seconds %= 60;
            if (min == 1) {
                message = message + min + " minuto ";
            }
            else {
                message = message + min + " minutos ";
            }
        }
        if (seconds >= 0) {
            if (seconds == 1) {
                message = message + seconds + " segundo";
            }
            else {
                message = message + seconds + " segundos";
            }
        }
        if (seconds < 0) {
            message = "Nunca";
        }
        return message;
    }
    
    public static long convert(final long days, final long hours, final long minutes, final long seconds) {
        long x = 0L;
        final long minute = minutes * 60L;
        final long hour = hours * 3600L;
        final long day = days * 86400L;
        x = minute + hour + day + seconds;
        return System.currentTimeMillis() + x * 1000L;
    }
}
