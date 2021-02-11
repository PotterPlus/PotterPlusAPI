package io.github.potterplus.api.storage.flatfile;

import io.github.potterplus.api.string.StringUtilities;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class MessagesFile<T extends JavaPlugin> extends PluginYamlFile<T> {

    private final T plugin;

    public MessagesFile(T plugin) {
        super(plugin, "messages.yml");

        this.plugin = plugin;
    }

    public String getRawMessage(String key) {
        String str = getFileConfiguration().getString(key);

        if (str == null) {
            InputStream def = plugin.getResource("messages.yml");

            if (def != null) {
                Reader reader = new InputStreamReader(def);
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(reader);

                str = yaml.getString(key);

                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (str == null) {
                throw new IllegalArgumentException(String.format("Could not resolve message from key '%s'", key));
            }
        }

        return str;
    }

    public String getMessage(String key) {
        return StringUtilities.color(getRawMessage(key));
    }

    public String getStrippedMessage(String key) {
        return ChatColor.stripColor(getMessage(key));
    }

    public void sendMessage(CommandSender to, String node) {
        sendMessage(to, node, null);
    }

    public void sendMessage(CommandSender to, String node, Map<String, String> replace) {
        String message = getMessage(node);

        message = StringUtilities.color(message);

        if (replace != null) {
            message = StringUtilities.replace(message, replace);
        }

        to.sendMessage(message);
    }
}
