package net.keyinator.bw;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Commands implements CommandExecutor, TabCompleter {
    private Main plugin;
    public Commands(Main main) { this.plugin = main; }

    @Override
    public ArrayList<String> onTabComplete(@NotNull CommandSender sender,@NotNull Command command,@NotNull String alias,String[] args) {
        ArrayList<String> tabcomplete = null;
        if(args.length <= 1) {
        tabcomplete = new ArrayList<>(Collections.singleton("add"));
        } else if(args.length == 2) {
            tabcomplete = new ArrayList<>(Arrays.asList("team","spawnpoint"));
        } else {
            if (args[1].equals("team")) {
                if (args.length == 3) {
                    tabcomplete = new ArrayList<>(Arrays.asList("Blue","Red","Yellow","Green","Aqua","Purple"));
                } else if (args.length == 4) {
                    tabcomplete = new ArrayList<>(Arrays.asList("1","c","e","a","b","5"));
                }
            } else if (args[1].equals("spawnpoint")) {
                if (args.length == 3) {
                    tabcomplete = new ArrayList<>(Arrays.asList("Blue","Red","Yellow","Green","Aqua","Purple"));
                }
            }
        }

        return tabcomplete;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        String prefix = "[§4Bed§fWars] ";
        if(strings[0].equals("add")) {
            if(strings[1].equals("team")) {
                if(strings.length == 4) {
                    this.plugin.getServer().getConsoleSender().sendMessage("WORKED");
                    String name = strings[2];
                    String color = strings[3];

                    Map<String, Object> obj = new HashMap<>();
                    obj.put("color", color.toLowerCase());
                    this.plugin.getConfig().set("teams." + name.toLowerCase(), obj);
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
