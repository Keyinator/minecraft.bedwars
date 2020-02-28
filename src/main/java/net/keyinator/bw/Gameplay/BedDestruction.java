package net.keyinator.bw.Gameplay;

import net.keyinator.bw.Main;
import org.bukkit.Location;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Array;
import java.util.*;

public class BedDestruction implements Listener {
    private Main plugin;
    public BedDestruction(Main main) {this.plugin = main;}

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent e) {//ToDo Check if actually bed
        if(this.plugin.state == Main.states.ingame) {
            if(e.getBlock().getBlockData() instanceof Bed) {

                Map<String, ArrayList<Player>> Teams = Statics.getTeams();
                //Get the team the player is in
                String teamOfPlayer = null;
                for (Map.Entry<String, ArrayList<Player>> TeamName : Teams.entrySet()) {
                    if (TeamName.getValue().indexOf(e.getPlayer()) != -1) {
                        teamOfPlayer = TeamName.getKey();
                        break;
                    }
                }

                //Get team of bed
                String teamOfBed = null;
                for(String TeamName : Objects.requireNonNull(this.plugin.getConfig().getConfigurationSection("teams")).getKeys(false)) {
                    ArrayList<Location> BedLocations = new ArrayList<>();
                    for(Object Location : Objects.requireNonNull(this.plugin.getConfig().getList("teams." + TeamName + ".beds"))) {
                        BedLocations.add((Location) Location);
                    }
                    if((Objects.requireNonNull(this.plugin.getConfig().getList("teams." + TeamName + ".beds"))).contains(new GetBedHead().run(e.getBlock()))) {
                        teamOfBed = TeamName;
                        break;
                    }
                }

                this.plugin.getServer().getConsoleSender().sendMessage(teamOfBed+" | "+teamOfPlayer);
                if(Objects.equals(teamOfBed, teamOfPlayer)) {
                    e.getPlayer().sendMessage(Statics.prefix+"You can't break your own bed");
                    e.setCancelled(true);
                } else {
                    this.plugin.getServer().broadcastMessage(
                            Statics.prefix+"The bed of team §"+this.plugin.getConfig().getString("teams."+teamOfBed+".color")+teamOfBed+"§f has been destroyed by team §"+this.plugin.getConfig().getString("teams."+teamOfPlayer+".color")+teamOfPlayer);
                }
            }
        }
    }
}
