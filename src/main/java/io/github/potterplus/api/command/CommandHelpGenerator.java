package io.github.potterplus.api.command;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class CommandHelpGenerator {

    public abstract String getHeaderFormat();
    public abstract List<CommandArg> getArgs();
}
