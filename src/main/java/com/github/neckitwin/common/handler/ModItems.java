package com.github.neckitwin.common.handler;

import com.github.neckitwin.common.items.ItemBankai;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
    public static Item.ToolMaterial BANKAI = EnumHelper.addToolMaterial("BANKAI", 9999999, 9999999, 999999, 999999, 45);
    public static final ItemBankai ITEM_BANKAI = new ItemBankai();

    public static void register() {
        GameRegistry.registerItem(ITEM_BANKAI, "item_bankai");
    }
}
