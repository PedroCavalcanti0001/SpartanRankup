package me.zkingofkill.spartan.rankup.api;

import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.database.Cache;

public class RankupAPI {
    public static Cache getCache(){
        return SpartanRankup.getInstance().getCache();
    }
}
