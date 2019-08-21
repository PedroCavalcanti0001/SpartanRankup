package me.zkingofkill.spartan.rankup.commands;

import me.zkingofkill.spartan.estrelas.SpartanEstrelas;
import me.zkingofkill.spartan.estrelas.objects.Estrelas;
import me.zkingofkill.spartan.gemas.SpartanGemas;
import me.zkingofkill.spartan.gemas.objects.Gemas;
import me.zkingofkill.spartan.gemas.utils.Utils;
import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.database.Cache;
import me.zkingofkill.spartan.rankup.listeners.PlayerRankupEvent;
import me.zkingofkill.spartan.rankup.objects.User;
import me.zkingofkill.spartan.rankup.utils.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Rankup implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas players pode executar esse comando!");
        }
        Player player = (Player) sender;
        User user = SpartanRankup.getInstance().getCache().getUserByPlayer(player);
        Gemas gemas = SpartanGemas.getInstance().getCache().getGemas(user.getOfflinePlayer());
        Estrelas estrelas = SpartanEstrelas.getInstance().getCache().getEstrelas(user.getOfflinePlayer());
        Cache cache = SpartanRankup.getInstance().getCache();
        double gemasRequeridas = user.getNextRank().getYolks();
        if(user.getPrestigy() >= 2){
            gemasRequeridas = user.getNextRank().getYolks() * (cache.getLastRank().getYolks()/user.getNextRank().getYolks())*user.getPrestigy();
        }
        if (gemas.getGemas() >= gemasRequeridas) {
            if (estrelas.getEstrelas() >= user.getNextRank().getStars()) {
                gemas.withdraw(user.getNextRank().getYolks());
                estrelas.withdraw(user.getNextRank().getStars());
                PlayerRankupEvent pre = new PlayerRankupEvent(user);
                Bukkit.getPluginManager().callEvent(pre);
                if (SpartanRankup.getInstance().getCache().getLastRank().equals(user.getRank())) {
                    user.setRank(SpartanRankup.getInstance().getCache().getByPosition(1));
                    user.setPrestigy(user.getPrestigy() + 1);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        ActionBar.sendActionbar(all, "§4§lUP:§b " + player.getName() + " voltou para o rank inicial e ganhou 1 de prestígio! ");
                    }
                } else {
                    user.setRank(user.getNextRank());
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        ActionBar.sendActionbar(all, "§4§lUP:§b " + player.getName() + " upou para o rank " + user.getRank().getPrefix() + "§b!");
                    }
                }
            } else {
                player.sendMessage("§f[§5§lSpartanRankup§f] §cPara upar de rank você precisa de mais §a" + (Utils.numberFormat(user.getNextRank().getStars() - estrelas.getEstrelas())) + "§c (" + Utils.numberFormat(user.getNextRank().getStars()) + " no total) estrelas.");
            }
        } else {
            player.sendMessage("§f[§5§lSpartanRankup§f] §cPara upar de rank você precisa de mais §a" + (Utils.numberFormat(user.getNextRank().getYolks() - gemas.getGemas())) + "§c (" + Utils.numberFormat(user.getNextRank().getYolks()) + " no total)§c gemas.");
        }
        return false;
    }
}
