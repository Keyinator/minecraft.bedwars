package net.keyinator.bw.Lobby;

import net.keyinator.bw.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BuildProtection implements Listener {
    private Main plugin;
    public BuildProtection(Main main) {this.plugin = main;}

    @EventHandler
    public void BlockBreakEvent(@NotNull BlockBreakEvent e) {
        if (!(e.getPlayer().hasPermission("bw.breakInLobby") || e.getPlayer().isOp())) {
            e.setCancelled(true);
        }
    }
}
