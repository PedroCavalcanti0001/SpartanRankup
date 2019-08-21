package me.zkingofkill.spartan.rankup.objects;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Categorie implements Cloneable{

    private String id;
    private String name;
    private ItemStack item;
    private boolean glow;
    private ArrayList<Rank> ranks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public boolean isGlow() {
        return glow;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }

    public ArrayList<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(ArrayList<Rank> ranks) {
        this.ranks = ranks;
    }

    public Categorie copy(){
        Categorie categorie = new Categorie(id, name, item, glow, ranks);
        return categorie;
    }

    public Categorie(String id, String name, ItemStack item, boolean glow, ArrayList<Rank> ranks) {
        this.id = id;
        this.name = name;
        this.item = item;
        this.glow = glow;
        this.ranks = ranks;
    }
}
