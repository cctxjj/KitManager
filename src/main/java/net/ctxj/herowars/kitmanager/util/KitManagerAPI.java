package net.ctxj.herowars.kitmanager.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class KitManagerAPI {

    private final FileManager manager;
    private final String kitname;
    private final FileConfiguration configuration;


    public KitManagerAPI(FileManager manager, String kitname) {
        this.manager = manager;
        this.kitname = kitname;
        this.configuration = manager.getConfiguration();
    }

    public ItemStack getItem(int number) {
        String usualpath = kitname+"."+number+".";
        if(!(configuration.contains(usualpath))) {
            return null;
        }

        ItemStack is = new ItemStack(Material.valueOf(configuration.getString(usualpath + "material")), configuration.getInt(usualpath+"amount"));
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(configuration.getString(usualpath + "displayname"));
        im.setLore(configuration.getStringList(usualpath + "lore"));
        for(Enchantment enchantment : Enchantment.values()) {
            if(configuration.contains(usualpath+"enchantments."+enchantment.getName())) {
                im.addEnchant(enchantment, configuration.getInt(usualpath + "enchantments." + enchantment.getName()), true);
            }
        }
        is.setItemMeta(im);
        return is;
    }

    public Inventory get() {
        Inventory inventory = Bukkit.createInventory(null, 4*9, kitname);
        for(int i = 1; i<=getSize(); i++ ) {
            inventory.addItem(getItem(i));
        }

        return inventory;
    }

    public void addItem(ItemStack itemStack) {
        ItemMeta im = itemStack.getItemMeta();
        String usualpath = kitname+"."+Integer.valueOf(getSize()+1) + ".";
        configuration.set(usualpath+"material", itemStack.getType().name());
        configuration.set(usualpath+"amount", itemStack.getAmount());
        configuration.set(usualpath+"displayname", itemStack.getItemMeta().getDisplayName());
        configuration.set(usualpath+"lore", itemStack.getItemMeta().getLore());
        for(Enchantment enchantment : Enchantment.values()) {
            if(im.getEnchants().containsKey(enchantment)) {
                configuration.set(usualpath+"enchantments."+enchantment.getName(), im.getEnchants().get(enchantment));
            }
        }
        manager.safe();
    }
    

    public int getSize() {
        for(int i = 1; i<100; i++ ) {
            if(!(configuration.contains(kitname+"."+i+".material"))) {
                return i-1;
            }
        }
        try {
            throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public FileManager getManager() {
        return manager;
    }
}
