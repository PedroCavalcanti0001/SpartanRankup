package me.zkingofkill.spartan.rankup.database;

import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.objects.Categorie;
import me.zkingofkill.spartan.rankup.objects.Rank;
import me.zkingofkill.spartan.rankup.objects.User;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Cache {
    public ArrayList<Categorie> categories;
    public ArrayList<User> users;

    public Cache() {
        this.categories = new ArrayList<>();
        this.users = new ArrayList<>();
        new BukkitRunnable() {
            @Override
            public void run() {
                save();
            }
        }.runTaskTimer(SpartanRankup.getInstance(), 5 * 20 * 60, 5 * 20 * 60);
    }

    public void save() {
        Mysql mysql = SpartanRankup.getInstance().getMysql();
        for (User user : new ArrayList<>(users)) {
            if (user.isModified()) {
                if (mysql.hasUser(user.getOfflinePlayer())) {
                    mysql.updateUser(user);
                } else {
                    mysql.insertUser(user);
                }
            }
            if (!user.getOfflinePlayer().isOnline()) {
                users.remove(user);
            }
        }
    }

    public User getUserByPlayer(Player player) {
        return users.stream().filter(user -> user.getOfflinePlayer().getName().equals(player.getName())).findAny().orElseGet(() -> {
            User user = new User(player, getByPosition(1), 0);
            users.add(user);
            return user;
        });
    }

    public Rank getByPosition(int position) {
        for (Categorie c : categories) {
            for (Rank rank : c.getRanks()) {
                if (rank.getPosition() == position) {
                    return rank;
                }
            }
        }
        return null;
    }

    public User getUser(Player player) {
        return users.stream().filter(user -> user.getOfflinePlayer().getName().equals(player.getName())).findAny().orElse(null);
    }

    public Rank getLastRank() {
        Categorie c = categories.get(categories.size() - 1);
        return c.getRanks().get(c.getRanks().size() - 1);
    }

    public ArrayList<Categorie> getCategories() {
        return categories;
    }
}
