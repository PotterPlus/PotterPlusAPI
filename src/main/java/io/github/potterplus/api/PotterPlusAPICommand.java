package io.github.potterplus.api;

import io.github.potterplus.api.command.CommandBase;
import io.github.potterplus.api.command.CommandContext;
import lombok.NonNull;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.List;

import static io.github.potterplus.api.misc.StringUtilities.equalsAny;

public class PotterPlusAPICommand extends CommandBase<PotterPlusAPI> {

    public static final Permission PERMISSION_ADMIN;

    static {
        PERMISSION_ADMIN = new Permission("potterplus.admin");
    }

    public PotterPlusAPICommand(@NonNull PotterPlusAPI plugin) {
        super(plugin);
    }

    @Override
    public String getLabel() {
        return "potterplusapi";
    }

    @Override
    public void execute(CommandContext context) {
        if (!context.hasPermission(PERMISSION_ADMIN)) {
            context.sendMessage(" &4&lX &cYou are not allowed to do that!");
        }

        String[] args = context.getArgs();

        if (args.length == 0) {
            context.sendMessage("version, reload");
        } else {
            String arg1 = args[0];

            if (equalsAny(arg1, "reload", "load")) {
                try {
                    getPlugin().reloadConfig();
                    context.sendMessage("&dPotterPlusAPI&8> &aPlugin reloaded!");
                } catch (Exception e) {
                    context.sendMessage("&dPotterPlusAPI&8> &cFailed to reload! Check console for information.");

                    e.printStackTrace();
                }
            } else if (equalsAny(arg1, "version", "v")) {
                PluginDescriptionFile pdFile = getPlugin().getDescription();

                String version = pdFile.getVersion();

                context.sendMessage("&dPotterPlusAPI version &e" + version);
            }
        }
    }

    @Override
    public List<String> tab(CommandContext context) {
        return null;
    }
}
