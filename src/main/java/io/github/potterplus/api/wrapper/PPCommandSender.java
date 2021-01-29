package io.github.potterplus.api.wrapper;

import io.github.potterplus.api.misc.StringUtilities;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * A quick CommandSender wrapper for simplified messages, etc.
 */
@RequiredArgsConstructor
public class PPCommandSender {

    public static PPCommandSender of(CommandSender sender) {
        return new PPCommandSender(sender);
    }

    @NonNull
    private final CommandSender sender;

    public void sendMessage(String message) {
        sender.sendMessage(StringUtilities.color(message));
    }

    public void sendMessages(String... messages) {
        Arrays.stream(messages).map(StringUtilities::color).forEach(sender::sendMessage);
    }
}
