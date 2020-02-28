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
        Map<String,Object> map = new HashMap<String,Object>(); //Object is containing String
        Map<String,ArrayList<Player>> Teams =new HashMap<String,ArrayList<Player>>();
        for (Map.Entry<String, Object> entry : this.plugin.getConfig().getConfigurationSection("teams").getValues(false).entrySet()) {
            ArrayList<Player> new_list = new ArrayList<>();
            Teams.put(entry.getKey(), new_list);
        }

        //Get players and shuffle them
        List<Player> players_random = new ArrayList<>();
        for(Player p:getServer().getOnlinePlayers()){
            players_random.add(p);
        }
        Collections.shuffle(players_random);

        //Put players into groups
        Iterator<Map.Entry<String, ArrayList<Player>>> Teams_iter = Teams.entrySet().iterator();
        Iterator<Player> Players_iter = players_random.iterator();
        while (Players_iter.hasNext()) {
            Player p = Players_iter.next();
            //Check Teams_iter
            if (!Teams_iter.hasNext()) {
                Teams_iter = Teams.entrySet().iterator();
            }
            Map.Entry<String, ArrayList<Player>> entry = Teams_iter.next();
            Teams.get(entry.getKey()).add(p);

            //Teleport to a spawnpoint of the group
            /*
            this.plugin.getServer().getConsoleSender().sendMessage((String) this.plugin.getConfig().getList("teams."+entry.getKey()+".spawnpoints").get(tp_num).toString());
            players_random.get(i).teleport((Location) this.plugin.getConfig().getList("teams."+entry.getKey()+".spawnpoints").get(tp_num));*/
        }

        //Telport all players of group
        Teams_iter = Teams.entrySet().iterator();
        while(Teams_iter.hasNext()) {
            Map.Entry<String, ArrayList<Player>> team = Teams_iter.next();
            Iterator<Player> Player_iter = team.getValue().iterator();
            List<?> Spawnpoints = this.plugin.getConfig().getList("teams." + team.getKey() + ".spawnpoints");
            Iterator<?> Spawnpoint_Iter = Spawnpoints.iterator();
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
