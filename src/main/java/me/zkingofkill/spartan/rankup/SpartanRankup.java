package me.zkingofkill.spartan.rankup;

import fr.minuskube.inv.InventoryManager;
import me.zkingofkill.spartan.rankup.commands.Ranks;
import me.zkingofkill.spartan.rankup.commands.Rankup;
import me.zkingofkill.spartan.rankup.commands.RankupAdmin;
import me.zkingofkill.spartan.rankup.database.Cache;
import me.zkingofkill.spartan.rankup.database.Mysql;
import me.zkingofkill.spartan.rankup.engines.EngineConfig;
import me.zkingofkill.spartan.rankup.hooks.PlaceHolderAPI;
import me.zkingofkill.spartan.rankup.listeners.onJoin;
import me.zkingofkill.spartan.rankup.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.SQLException;

public final class SpartanRankup extends JavaPlugin {

    private static SpartanRankup instance;
    private Cache cache;
    private Mysql mysql;
    private InventoryManager inventoryManager;
    private Utils utils;

    @Override
    public void onEnable() {
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();
        instance = this;
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        this.mysql = new Mysql();
        this.cache = new Cache();
        this.utils = new Utils();
        new EngineConfig().setup();


        try {
            this.mysql.setup();
        } catch (SQLException e) {
            System.err.println("Por favor configure o mysql para que o plugin funcione!");
            getServer().getPluginManager().disablePlugin(this);
        }
        getCommand("ranks").setExecutor(new Ranks());
        getCommand("rankup").setExecutor(new Rankup());
        getCommand("rankupadmin").setExecutor(new RankupAdmin());
        getServer().getPluginManager().registerEvents(new onJoin(), this);
        if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceHolderAPI(this, "SpartanRankup").hook();
        } else {
            System.out.println("PlaceholderAPI não encontrado.");
        }
    }

    @Override
    public void onDisable() {

        this.cache.save();
    }

    public static SpartanRankup getInstance() {
        return instance;
    }

    public Cache getCache() {
        return cache;
    }

    public Mysql getMysql() {
        return mysql;
    }

    public Utils getUtils() {
        return utils;
    }
}
