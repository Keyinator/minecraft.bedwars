package net.keyinator.bw.Gameplay;

import net.keyinator.bw.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

import static org.bukkit.Material.*;
import static org.bukkit.Material.YELLOW_BED;

public class Statics {
    private Main plugin;
    public Statics(Main main) { this.plugin = main; }

    public static String prefix = "[§4Bed§fWars] ";

    public static HashSet<Material> BedSet= new HashSet<>(Arrays.asList(
            BLACK_BED, BLUE_BED, BROWN_BED, CYAN_BED, GRAY_BED, GREEN_BED, LIGHT_BLUE_BED, LIGHT_GRAY_BED, LIME_BED, MAGENTA_BED, ORANGE_BED, PINK_BED, PURPLE_BED, RED_BED, WHITE_BED, YELLOW_BED
    ));

    private static Map<String, ArrayList<Player>> Teams =new HashMap<>();
    public static void setTeams(Map<String, ArrayList<Player>> n) {
        Teams = n;
    }
    public static Map<String, ArrayList<Player>> getTeams() {
        return Teams;
    }
}
