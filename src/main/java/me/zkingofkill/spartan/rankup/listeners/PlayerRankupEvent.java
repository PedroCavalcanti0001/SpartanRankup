package me.zkingofkill.spartan.rankup.listeners;

import me.zkingofkill.spartan.rankup.objects.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRankupEvent extends Event {
    private User user;

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PlayerRankupEvent(User user){
        this.user = user;
    }




}
