package io.github.potterplus.api.misc;

import java.util.Date;

public class TimeUtilities {

    public static final String PATTERN = "yyyy-MM-dd HH:mm:ssZ";

    public Date getDate(long unix) {
        return new Date(unix);
    }
}
