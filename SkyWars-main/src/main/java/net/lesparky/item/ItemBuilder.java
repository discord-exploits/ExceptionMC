package net.lesparky.item;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material type, short subID) {

        this.itemStack = new ItemStack(type, 1, subID);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material type) {

        this(type, (short)0);
    }

    public ItemBuilder setDisplayName(String name) {

        this.itemMeta.setDisplayName(name);

        return this;
    }

    public ItemBuilder setLore(String... lore) {

        this.itemMeta.setLore(Arrays.asList(lore));

        return this;
    }

    public ItemBuilder setLore(List<String> lore) {

        this.itemMeta.setLore(lore);

        return this;
    }

    public ItemBuilder setAmount(int amount) {

        this.itemStack.setAmount(amount);

        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {

        this.itemMeta.spigot().setUnbreakable(unbreakable);

        return this;
    }

    public ItemBuilder hide(ItemFlag... itemFlag) {

        this.itemMeta.addItemFlags(itemFlag);

        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {

        this.itemMeta.addEnchant(enchantment, level, true);

        return this;
    }

    public ItemStack build() {

        this.itemStack.setItemMeta(this.itemMeta);

        return this.itemStack;
    }
}
