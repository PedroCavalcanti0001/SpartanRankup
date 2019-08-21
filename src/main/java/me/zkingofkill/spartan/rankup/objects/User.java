package me.zkingofkill.spartan.rankup.objects;

import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.database.Cache;
import org.bukkit.OfflinePlayer;

public class User {
    private OfflinePlayer offlinePlayer;
    private Rank rank;
    private int prestigy;
    private boolean modified;

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public void setOfflinePlayer(OfflinePlayer offlinePlayer) {
        this.modified = true;
        this.offlinePlayer = offlinePlayer;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.modified = true;
        this.rank = rank;
    }

    public int getPrestigy() {
        return prestigy;
    }

    public void setPrestigy(int prestigy) {
        this.modified = true;
        this.prestigy = prestigy;
    }

    public boolean isModified() {
        return modified;
    }
    public Rank getNextRank(){
        Cache c = SpartanRankup.getInstance().getCache();
        if(getRank().getPosition() == c.getLastRank().getPosition()){
           return c.getByPosition(1);
        }else{
           return c.getByPosition(getRank().getPosition()+1);
        }
    }
    public Rank getPreviousRank(){
        Cache c = SpartanRankup.getInstance().getCache();
        Rank r = getRank().getPosition() >= 1 ? c.getByPosition(getRank().getPosition()-1) : c.getByPosition(0);
        return r;
    }
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "User{" +
                "offlinePlayer=" + offlinePlayer +
                ", rank=" + rank +
                ", prestigy=" + prestigy +
                ", modified=" + modified +
                '}';
    }

    public User(OfflinePlayer offlinePlayer, Rank rank, int prestigy) {
        this.offlinePlayer = offlinePlayer;
        this.rank = rank;
        this.prestigy = prestigy;
    }
}
