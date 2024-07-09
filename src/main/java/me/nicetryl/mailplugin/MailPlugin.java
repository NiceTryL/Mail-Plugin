package me.nicetryl.mailplugin;

import me.nicetryl.mailplugin.commands.MailCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class MailPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("mail").setExecutor(new MailCommand());
    }
}
