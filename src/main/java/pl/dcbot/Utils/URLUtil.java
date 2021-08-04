package pl.dcbot.Utils;

public class URLUtil {
    public static boolean isUrl(String argument) {
        return argument.startsWith("https://") || argument.startsWith("http://");
    }

}
