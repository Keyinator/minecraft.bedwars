package net.keyinator.bw;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Commands implements CommandExecutor {
    private Main plugin;
    public Commands(Main main) { this.plugin = main; }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        String prefix = "[§4Bed§fWars] ";
        this.plugin.getServer().getConsoleSender().sendMessage(strings.length+"");
        if(strings[0].equals("add")) {
            if(strings[1].equals("team")) {
                if(strings.length == 4) {
                    this.plugin.getServer().getConsoleSender().sendMessage("WORKED");
                    String name = strings[2];
                    String color = strings[3];

                    Map<String, Object> obj = new HashMap<String, Object>();
                    obj.put("color", color);
                    this.plugin.getConfig().set("teams." + name, obj);
                    commandSender.sendMessage(prefix+"Sucesfully added team");
                } else {
                    commandSender.sendMessage(prefix+"Usage: /add team [Name] [Color]");
                }
            } else if(strings[1].equals("spawnpoint")) {
                if(strings.length == 3) {
                    String team = strings[2];

                    ArrayList<Location> locs = new ArrayList<>();
                    locs.add(((Player)commandSender).getLocation());
                    this.plugin.getConfig().set("teams."+team+".spawnpoints", locs);
                    commandSender.sendMessage(prefix+"Sucesfully added spawnpoint");
                } else {
                    commandSender.sendMessage(prefix+"§7 Usage: /add spawnpoint [Teamname]");
                }

            }
        }
        return true;
    }
}
