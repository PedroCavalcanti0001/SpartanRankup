package me.zkingofkill.spartan.rankup.commands;

import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.database.Cache;
import me.zkingofkill.spartan.rankup.objects.Categorie;
import me.zkingofkill.spartan.rankup.objects.Rank;
import me.zkingofkill.spartan.rankup.objects.User;
import me.zkingofkill.spartan.rankup.utils.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankupAdmin implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender.isOp()){
            if(args.length == 0){
                commandSender.sendMessage("§6* Commandos: ");
                commandSender.sendMessage("§e");
                commandSender.sendMessage("§e/rankupadmin reset <jogador>");
                commandSender.sendMessage("§e/rankupadmin setrank <jogador> <rank>");
                commandSender.sendMessage("§e/rankupadmin setprestigio <jogador> <quantidade>");
            }else{
                if(args[0].equalsIgnoreCase("definir")){
                    int porcentagem = Integer.valueOf(args[2]);
                    FileConfiguration config = SpartanRankup.getInstance().getConfig();
                    if(args[1].equalsIgnoreCase("gemas")){
                        for(Categorie categorie : SpartanRankup.getInstance().getCache().categories){
                            for(Rank rank : categorie.getRanks()){
                                if(rank.getPosition() >= 2){
                                    Rank rankAnterior = SpartanRankup.getInstance().getCache().getByPosition(rank.getPosition()-1);
                                    rank.setYolks(rankAnterior.getYolks() + (rankAnterior.getYolks()*porcentagem/100));
                                    config.set("categories."+categorie.getId()+".ranks."+rank.getName()+".requeriments.yolks", rank.getYolks());
                                    SpartanRankup.getInstance().saveConfig();
                                }
                            }
                        }
                    }
                    if(args[1].equalsIgnoreCase("estrelas")){
                        for(Categorie categorie : SpartanRankup.getInstance().getCache().categories){
                            for(Rank rank : categorie.getRanks()){
                                if(rank.getPosition() >= 2){
                                    Rank rankAnterior = SpartanRankup.getInstance().getCache().getByPosition(rank.getPosition()-1);
                                    rank.setStars(rankAnterior.getStars() + (rankAnterior.getStars()*porcentagem/100));
                                    config.set("categories."+categorie.getId()+".ranks."+rank.getName()+".requeriments.stars", rank.getStars());
                                    SpartanRankup.getInstance().saveConfig();
                                }
                            }
                        }
                    }
                    commandSender.sendMessage("definido com sucesso");
                    return false;
                }
                Player target = Bukkit.getPlayer(args[1]);
                Cache cache = SpartanRankup.getInstance().getCache();
                User user = cache.getUserByPlayer(target);
                if(target != null){
                    if(args[0].equalsIgnoreCase("reset")){
                        user.setPrestigy(0);
                        user.setRank(cache.getByPosition(1));
                        commandSender.sendMessage("§aJogador resetado com sucesso.");
                    }else if(args[0].equalsIgnoreCase("setrank")){
                        Rank rank = Rank.parse(args[2]);
                        if(rank != null) {
                            user.setRank(rank);
                            commandSender.sendMessage("§aRank setado com sucesso.");
                        }else{
                            commandSender.sendMessage("rank invalido!");
                        }
                    }else if(args[0].equalsIgnoreCase("setprestigio")){
                        int p = Integer.parseInt(args[2]);
                        user.setPrestigy(p);
                        commandSender.sendMessage("§aPrestigio setado com sucesso.");
                    }
                }else{
                    commandSender.sendMessage("§cJogador invalido.");
                }
            }
        }
        return false;
    }
}
