package me.zkingofkill.spartan.rankup.hooks;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import me.zkingofkill.spartan.gemas.gemasapi.GemasAPI;
import me.zkingofkill.spartan.rankup.SpartanRankup;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlaceHolderAPI extends EZPlaceholderHook {

    public PlaceHolderAPI(Plugin plugin, String identifier){
        super(plugin, identifier);
    }

    public String onPlaceholderRequest(final Player p, final String i) {
        if (p == null) {
            return null;
        }
        if (i.equals("atual")) {
            return SpartanRankup.getInstance().getCache().getUserByPlayer(p).getRank().getPrefix();
        }
        if (i.equals("proximo")) {
            return SpartanRankup.getInstance().getCache().getUserByPlayer(p).getNextRank().getPrefix();
        }
        if (i.equals("prestigio")) {
            return String.valueOf(SpartanRankup.getInstance().getCache().getUserByPlayer(p).getPrestigy());
        }
        return null;
    }
}
