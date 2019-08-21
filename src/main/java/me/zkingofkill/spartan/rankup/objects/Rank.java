package me.zkingofkill.spartan.rankup.objects;

import me.zkingofkill.spartan.rankup.SpartanRankup;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collector;

public class Rank implements Cloneable{

    private String name;
    private int position;
    private double stars;
    private double yolks;
    private String prefix;
    private Map<String, Boolean> permissions;
    private ArrayList<String> commands;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public double getYolks() {
        return yolks;
    }

    public void setYolks(double yolks) {
        this.yolks = yolks;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Boolean> permissions) {
        this.permissions = permissions;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<String> commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", stars=" + stars +
                ", yolks=" + yolks +
                ", prefix='" + prefix + '\'' +
                ", permissions=" + permissions +
                ", commands=" + commands +
                '}';
    }

    @Override
    public Rank clone() {
        try {
            return (Rank) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Rank parse(String rank) {
        for (Categorie categorie : SpartanRankup.getInstance().getCache().categories) {
            for (Rank r : categorie.getRanks()) {
                String nome = categorie.getId() + "-" + r.getName();
                if (nome.equalsIgnoreCase(rank)) {
                    return r;
                }
            }
        }
        return null;
    }

    public Rank(String name, int position, double stars, double yolks, String prefix, Map<String, Boolean> permissions, ArrayList<String> commands) {
        this.name = name;
        this.position = position;
        this.stars = stars;
        this.yolks = yolks;
        this.prefix = prefix;
        this.permissions = permissions;
        this.commands = commands;
    }
}
