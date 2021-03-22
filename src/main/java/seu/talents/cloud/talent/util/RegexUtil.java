package seu.talents.cloud.talent.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    private final static Pattern urlTemplate = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    public static boolean isUrl(String toMatch) {
        Matcher matcher = urlTemplate.matcher(toMatch);
        return matcher.matches();
    }
}
