package me.zkingofkill.spartan.rankup.engines;

import com.mysql.jdbc.Util;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import me.zkingofkill.spartan.estrelas.SpartanEstrelas;
import me.zkingofkill.spartan.gemas.SpartanGemas;
import me.zkingofkill.spartan.gemas.utils.Utils;
import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.database.Cache;
import me.zkingofkill.spartan.rankup.objects.Categorie;
import me.zkingofkill.spartan.rankup.objects.Rank;
import me.zkingofkill.spartan.rankup.objects.User;
import me.zkingofkill.spartan.rankup.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EngineMenu implements InventoryProvider {

    private User user;
    private ItemStack head;
    private SmartInventory INVENTORY;
    private int page = 1;

    public EngineMenu(User user) {
        this.user = user;
        this.head = new ItemStackBuilder(Material.SKULL_ITEM)
                .setDurability(3).setOwner(user.getOfflinePlayer().getName())
                .setName("§5§lSeu Status")
                .addLore("",
                        "§b● Rank Atual: " + user.getRank().getPrefix(),
                        "§b● Rank Seguinte: " + user.getNextRank().getPrefix(),
                        "",
                        "§6● Suas Gemas: " + SpartanGemas.getInstance().getCache().getGemas(user.getOfflinePlayer()).getFormatedGemas(),
                        "§6● Suas Estrelas: " + SpartanEstrelas.getInstance().getCache().getEstrelas(user.getOfflinePlayer()).getFormatedEstrelas(),
                        "",
                        "§a● Seu Prestígio: " + user.getPrestigy())
                .build();
        this.INVENTORY = SmartInventory.builder()
                .provider(this)
                .size(6, 9)
                .title("[Ranks] #" + page)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        ClickableItem[] items = new ClickableItem[SpartanRankup.getInstance().getCache().categories.size()];
        int i = 0;
        Cache cache = SpartanRankup.getInstance().getCache();
        for (Categorie categorie : cache.categories) {
            ItemStackBuilder itb = new ItemStackBuilder(categorie.getItem()).setLore(SpartanRankup.getInstance().getConfig().getStringList("globallore"));
            for (Rank r : categorie.getRanks()) {
                Rank rank = r.clone();
                if (user.getPrestigy() >= 1 && rank.getPosition() >= 2) {
                    double gemasRequeridas = (cache.getLastRank().getYolks() * user.getPrestigy()) * rank.getPosition();
                    rank.setYolks(gemasRequeridas);
                }
                itb.setLore(itb.build().getItemMeta().getLore().stream().map(o -> o.contains("&") ? o.replace("&", "§") : o)
                        .map(o -> o.contains("<amount-stars-" + rank.getName() + ">") ? o.replace("<amount-stars-" + rank.getName() + ">", Utils.numberFormat(rank.getStars())) : o)
                        .map(o -> o.contains("<amount-yolks-" + rank.getName() + ">") ? o.replace("<amount-yolks-" + rank.getName() + ">", Utils.numberFormat(rank.getYolks())) : o).collect(Collectors.toList()));
            }
            items[i] = ClickableItem.empty(itb.build());
            i++;
        }
        pagination.setItems(items);
        pagination.setItemsPerPage(14);
        SlotIterator iterator = contents.newIterator(SlotIterator.Type.HORIZONTAL, 2, 1);
        iterator.blacklist(2, 8);
        iterator.blacklist(3, 0);
        pagination.addToIterator(iterator);

        contents.set(5, 3, ClickableItem.of(new ItemStackBuilder(Material.ARROW).setName("§aPagina anterior.").build(),
                e -> {
                    INVENTORY.open(player, pagination.previous().getPage());
                    page -= 1;
                }));
        contents.set(5, 5, ClickableItem.of(new ItemStackBuilder(Material.ARROW).setName("§aPagina seguinte.").build(),
                e -> {
                    INVENTORY.open(player, pagination.next().getPage());
                    page += 1;
                }));
        contents.set(0, 4, ClickableItem.empty(head));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public void open() {
        this.INVENTORY.open((Player) this.user.getOfflinePlayer());
    }

    public void close() {
        this.INVENTORY.close((Player) this.user.getOfflinePlayer());
    }
}
