package net.keyinator.bw.Lobby;

import net.keyinator.bw.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class LobbyJoin implements Listener {
    private Main plugin;
    public LobbyJoin(Main main) { this.plugin = main; }

    private void countdown(List<Integer> list, int i) {
        if(this.plugin.getServer().getOnlinePlayers().size()<this.plugin.getConfig().getInt("min-players")) {
            return;
        } else {
            if (i == list.size()-1) {
                Bukkit.getScheduler().runTaskLater(
                    this.plugin,
                    () -> {
                        this.plugin.getServer().broadcastMessage("Game starting now");
                        this.plugin.state = Main.states.ingame;
                        RoundStart roundstart = new RoundStart(this.plugin);
                    },
                    (list.get(i)) * 20); //20 ticks equal 1 second
            } else {
                int cur = list.get(i);
                int next = (i == (list.size() - 1)) ? 0 : list.get(i + 1);
                Bukkit.getScheduler().runTaskLater(
                    this.plugin,
                    () -> {
                        this.plugin.getServer().broadcastMessage("Game starting in " + next + " seconds");
                        countdown(list, i + 1);
                    },
                    (cur - next) * 20); //20 ticks equal 1 second
            }
        }
        return;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        this.plugin.getServer().broadcastMessage("[§a+§f] §b"+e.getPlayer().getName()+"§f has joined the game");
        if (this.plugin.state == Main.states.lobby) {
            if(this.plugin.getServer().getOnlinePlayers().size()>this.plugin.getConfig().getInt("max-players")) {
                e.getPlayer().kickPlayer("§cThe server is full. You have been kicked.\n§aTry rejoining once the game has started to join as spectator");
            } else if(this.plugin.getServer().getOnlinePlayers().size()<this.plugin.getConfig().getInt("min-players")) {
                this.plugin.getServer().broadcastMessage("§a"+(this.plugin.getConfig().getInt("min-players")-this.plugin.getServer().getOnlinePlayers().size())+"§b more players needed");
            } else {
                List<Integer> list = new ArrayList<Integer>();
                list = this.plugin.getConfig().getIntegerList("wait-time");
                this.plugin.getServer().broadcastMessage("Game starting in "+list.get(0)+" seconds");
                countdown(list, 0);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        this.plugin.getServer().broadcastMessage("[§c-§f] §b"+e.getPlayer().getName()+"§f has left the game");
        if (this.plugin.state == Main.states.lobby) {
            if(this.plugin.getServer().getOnlinePlayers().size()-1<this.plugin.getConfig().getInt("min-players")) {
                this.plugin.getServer().broadcastMessage("§a"+(this.plugin.getConfig().getInt("min-players")-(this.plugin.getServer().getOnlinePlayers().size()-1))+"§b more players needed");
            }
        }
    }
}