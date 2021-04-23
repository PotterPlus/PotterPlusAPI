package io.github.potterplus.api.time;

public enum TimeConverter {

    MILLISECONDS,

    SECONDS,

    MINUTES,

    HOURS,

    TICKS;

    public long toMillis(long time) {

    }

    public long toSeconds(long time) {
        switch (this) {
            case MILLISECONDS:
                return time / 1000;
        }

        if (this.equals(TimeConverter.TICKS)) {
            return time * 20L;
        }

        return time;
    }

    public long toMinutes(long time) {

    }

    public long toTicks(long time) {
        switch (this) {
            case MILLISECONDS:
                return time / 1000 * 20;
            case SECONDS:
                return time * 20;
            case MINUTES:
                return time * 20 * x;
        }
    }
}
