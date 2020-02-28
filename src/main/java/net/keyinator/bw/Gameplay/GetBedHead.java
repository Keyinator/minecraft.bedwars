package net.keyinator.bw.Gameplay;

import net.keyinator.bw.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;

public class GetBedHead {
    public Location run(Block b) {
        if(((Bed)b.getBlockData()).getPart().equals(Bed.Part.HEAD)) {
            return b.getLocation();
        } else {
            if (((Bed) b.getBlockData()).getFacing() == BlockFace.NORTH){
                return(b.getLocation().add(0,0,-1));
            } else if (((Bed) b.getBlockData()).getFacing() == BlockFace.EAST){
                return(b.getLocation().add(1,0,0));
            } else if (((Bed) b.getBlockData()).getFacing() == BlockFace.SOUTH){
                return(b.getLocation().add(0,0,1));
            } else if (((Bed) b.getBlockData()).getFacing() == BlockFace.WEST){
                return(b.getLocation().add(-1,0,0));
            }
        }
        return null;
    }
}
