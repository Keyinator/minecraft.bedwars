package net.keyinator.bw.Lobby;

import net.keyinator.bw.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class RoundStart {
    private Main plugin;
    public RoundStart(Main main) { this.plugin = main; run();}

    private void run() {
        //Make array for each team and add the teams
        Map<String,Object> map = new HashMap<>(); //Object is containing String
        Map<String,ArrayList<Player>> Teams =new HashMap<>();
        Objects.requireNonNull(this.plugin.getConfig().getConfigurationSection("teams")).getValues(false);
        for (Map.Entry<String, Object> entry : Objects.requireNonNull(this.plugin.getConfig().getConfigurationSection("teams")).getValues(false).entrySet()) {
            ArrayList<Player> new_list = new ArrayList<>();
            Teams.put(entry.getKey(), new_list);
        }

        //Get players and shuffle them
        List<Player> players_random = new ArrayList<>(getServer().getOnlinePlayers());
        Collections.shuffle(players_random);

        //Put players into groups
        Iterator<Map.Entry<String, ArrayList<Player>>> Teams_iter = Teams.entrySet().iterator();
        for (Player p : players_random) {
            //Check Teams_iter
            if (!Teams_iter.hasNext()) {
                Teams_iter = Teams.entrySet().iterator();
            }
            Map.Entry<String, ArrayList<Player>> entry = Teams_iter.next();
            Teams.get(entry.getKey()).add(p);
        }

        //Telport all players of group
        for (Teams_iter = Teams.entrySet().iterator(); Teams_iter.hasNext(); ) {
            Map.Entry<String, ArrayList<Player>> team = Teams_iter.next();
            Iterator<Player> Player_iter = team.getValue().iterator();
            List<?> Spawnpoints = this.plugin.getConfig().getList("teams." + team.getKey() + ".spawnpoints");
            Iterator<?> Spawnpoint_Iter = Objects.requireNonNull(Spawnpoints).iterator();
            while (Player_iter.hasNext()) {
                Player p = Player_iter.next();
                if (!Spawnpoint_Iter.hasNext()) {
                    Spawnpoint_Iter = Spawnpoints.iterator();
                }
                Object Spawnpoint = Spawnpoint_Iter.next();
                this.plugin.getServer().getConsoleSender().sendMessage(Spawnpoint.toString());
                p.teleport((Location) Spawnpoint);
            }
        }
    }
}
