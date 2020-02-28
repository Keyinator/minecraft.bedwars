package net.keyinator.bw;

import net.keyinator.bw.Lobby.LobbyJoin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public enum states {
        lobby,
        ingame,
        end
    }

    public states state = states.lobby;

    //Project STARTED on 26.02.2020

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getConsoleSender().sendMessage("§2+-----------------------+");
        getServer().getConsoleSender().sendMessage("§2| §eKeyinator§3's §eBW §6loaded §2|");
        getServer().getConsoleSender().sendMessage("§2+-----------------------+");
        getServer().getPluginManager().registerEvents(new LobbyJoin(this), this);
        Commands CommandClass = new Commands(this);
        getCommand("bw").setExecutor(CommandClass);
        saveConfig();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
