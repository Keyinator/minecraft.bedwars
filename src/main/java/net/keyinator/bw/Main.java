package net.keyinator.bw;

import net.keyinator.bw.Gameplay.BedDestruction;
import net.keyinator.bw.Lobby.BuildProtection;
import net.keyinator.bw.Lobby.LobbyJoin;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

        Map<String,ArrayList<Player>> Teams =new HashMap<>();

        getServer().getPluginManager().registerEvents(new LobbyJoin(this), this);
        getServer().getPluginManager().registerEvents(new BuildProtection(this), this);
        Commands CommandClass = new Commands(this);
        Objects.requireNonNull(getCommand("bw")).setExecutor(CommandClass);
        Objects.requireNonNull(getCommand("bw")).setTabCompleter(CommandClass);

        getServer().getPluginManager().registerEvents(new BedDestruction(this), this);
        saveConfig();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
