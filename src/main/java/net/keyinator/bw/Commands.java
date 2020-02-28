package net.keyinator.bw;

import net.keyinator.bw.Gameplay.GetBedHead;
import net.keyinator.bw.Gameplay.Statics;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
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
            tabcomplete = new ArrayList<>(Arrays.asList("team","spawnpoint","bed"));
        } else {
            switch(args[1]) {
                case "team": {
                    if (args.length == 3) {
                        tabcomplete = new ArrayList<>(Arrays.asList("Blue", "Red", "Yellow", "Green", "Aqua", "Purple"));
                    } else if (args.length == 4) {
                        tabcomplete = new ArrayList<>(Arrays.asList("1", "c", "e", "a", "b", "5"));
                    }
                }
                case "spawnpoint": {
                    if (args.length == 3) {
                        tabcomplete = new ArrayList<>(Arrays.asList("Blue", "Red", "Yellow", "Green", "Aqua", "Purple"));
                    }
                }
                case "bed": {
                    if (args.length == 3) {
                        tabcomplete = new ArrayList<>(Arrays.asList("Blue", "Red", "Yellow", "Green", "Aqua", "Purple"));
                    }
                }
            }
        }

        return tabcomplete;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings[0].equals("add")) {
            switch (strings[1]) {
                case "team": {
                    if(strings.length == 4) {
                        this.plugin.getServer().getConsoleSender().sendMessage("WORKED");
                        String name = strings[2].toLowerCase();
                        String color = strings[3].toLowerCase();

                        Map<String, Object> obj = new HashMap<>();
                        obj.put("color", color.toLowerCase());
                        this.plugin.getConfig().set("teams." + name.toLowerCase(), obj);
                        commandSender.sendMessage(Statics.prefix+"Sucesfully added team");
                    } else {
                        commandSender.sendMessage(Statics.prefix+"Usage: /add team [Name] [Color]");
                    }
                } case "spawnpoint": {
                    if(strings.length == 3) {
                        String team = strings[2].toLowerCase();

                        ArrayList<Location> locs = new ArrayList<>();
                        locs.add(((Player)commandSender).getLocation());
                        this.plugin.getConfig().set("teams."+team+".spawnpoints", locs);
                        commandSender.sendMessage(Statics.prefix+"Sucesfully added spawnpoint");
                    } else {
                        commandSender.sendMessage(Statics.prefix+"§7 Usage: /add spawnpoint [Teamname]");
                    }
                } case"bed": {
                    if (strings.length == 3) {
                        String team = strings[2].toLowerCase();

                        ArrayList<Location> locs = new ArrayList<>();
                        locs.add(new GetBedHead().run(((Player)commandSender).getTargetBlock(null,50)));
                        this.plugin.getConfig().set("teams." + team + ".beds", locs);
                        commandSender.sendMessage(Statics.prefix + "Sucesfully added spawnpoint");
                    } else {
                        commandSender.sendMessage(Statics.prefix + "§7 Usage: /add spawnpoint [Teamname]");
                    }
                }
            }
        }
        return true;
    }
}
