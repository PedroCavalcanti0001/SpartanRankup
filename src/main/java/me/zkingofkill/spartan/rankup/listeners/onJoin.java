package me.zkingofkill.spartan.rankup.listeners;

import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.objects.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        User u = SpartanRankup.getInstance().getMysql().loadUser(e.getPlayer());
        User us = SpartanRankup.getInstance().getCache().getUserByPlayer(e.getPlayer());
        if (u != null) {
            if(us.getPrestigy() > u.getPrestigy() || us.getRank().getPosition() > u.getRank().getPosition()) return;
            us.setRank(u.getRank());
            us.setPrestigy(u.getPrestigy());
        }

    }
}
