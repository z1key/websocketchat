package util;

/**
 * Created by User on 09.04.2017.
 */
public class AjaxUtils {
    private AjaxUtils() {
    }

    public static boolean isAjaxRequest(String requestedWith) {
        return requestedWith != null && "XMLHttpRequest".equals(requestedWith);
    }
}
