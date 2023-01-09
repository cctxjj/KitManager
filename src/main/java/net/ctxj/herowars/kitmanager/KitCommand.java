package net.ctxj.herowars.kitmanager;

import net.ctxj.herowars.kitmanager.util.FileManager;
import net.ctxj.herowars.kitmanager.util.KitManagerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if(!(strings.length==2)) {
                return false;
            }
            if(!(p.hasPermission("system.kitmaster"))) {
                return false;
            }
            if(strings[0].equalsIgnoreCase("set")) {
                KitManagerAPI api = new KitManagerAPI(new FileManager("plugins//kits.yml"), strings[1]);
                for(ItemStack is : p.getInventory().getContents()) {
                    if(is != null) {
                        if (api.getSize() < 100) {
                            api.addItem(is);
                            System.out.println(is.getItemMeta().getDisplayName());
                        } else {
                            p.sendMessage("§cCouldn't add item, max is 100!");
                        }
                    }
                }
            } else if(strings[0].equalsIgnoreCase("get")) {
                KitManagerAPI api = new KitManagerAPI(new FileManager("plugins//kits.yml"), strings[1]);
                p.getInventory().clear();
                p.getInventory().setContents(api.get().getContents());
            }
        }
        return false;
    }
}
