package me.zkingofkill.spartan.rankup.engines;

import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.database.Cache;
import me.zkingofkill.spartan.rankup.objects.Categorie;
import me.zkingofkill.spartan.rankup.objects.Rank;
import me.zkingofkill.spartan.rankup.utils.ItemStackBuilder;
import me.zkingofkill.spartan.rankup.utils.Utils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EngineConfig {

    public void setup(){
        FileConfiguration config = SpartanRankup.getInstance().getConfig();

        for(String category : config.getConfigurationSection("categories").getKeys(false)){
            String name = config.getString("categories."+category+".name").replace("&","§");
            boolean glow = config.getBoolean("categories."+category+".glow");
            Material m = Material.getMaterial(Integer.valueOf(config.getString("categories."+category+".item-id").split(":")[0]));
            int data = Integer.valueOf(config.getString("categories."+category+".item-id").split(":")[1]);
            ArrayList<Rank> rk = new ArrayList<>();
            List<String> lore = config.getStringList("globallore");
            for(String rank : config.getConfigurationSection("categories."+category+".ranks").getKeys(false)) {
                double stars = config.getDouble("categories."+category+".ranks."+rank+".requeriments.stars");
                double yolks =  config.getDouble("categories."+category+".ranks."+rank+".requeriments.yolks");
               Map<String, Boolean> permissions = new HashMap<>();
                config.getStringList("categories."+category+".ranks."+rank+".permissions").forEach(a -> permissions.put(a, true));
               ArrayList<String> commands = (ArrayList<String>) config.getStringList("categories."+category+".ranks"+rank+".commands");
               String prefix = config.getString("categories."+category+".ranks."+rank+".prefix").replace("&","§");
               int position = config.getInt("categories."+category+".ranks."+rank+".position");
               rk.add(new Rank(rank, position, stars, yolks, prefix, permissions, commands));
            }
            ItemStack item =  new ItemStackBuilder(m).setName(name).setDurability(data).setLore(lore).build();
            Categorie categorie = new Categorie(category, name, item, glow, rk);
            SpartanRankup.getInstance().getCache().categories.add(categorie);
        }
    }
}
