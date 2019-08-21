package me.zkingofkill.spartan.rankup.commands;

import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.engines.EngineMenu;
import me.zkingofkill.spartan.rankup.objects.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ranks implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cApenas players pode executar esse comando!");
            return false;
        }
        Player player = (Player) sender;
        User user = SpartanRankup.getInstance().getCache().getUserByPlayer(player);
        new EngineMenu(user).open();
        return false;
    }
}
