package io.github.potterplus.api.command.flag;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandFlag {

    @NonNull @Getter
    private final String longFlag, shortFlag;

    public String getFullLongFlag() {
        return String.format("--%s", getLongFlag());
    }

    public String getFullShortFlag() {
        return String.format("-%s", getShortFlag());
    }

}
