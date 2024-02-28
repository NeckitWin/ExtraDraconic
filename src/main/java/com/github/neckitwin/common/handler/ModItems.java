package com.github.neckitwin.common.handler;

import com.github.neckitwin.common.items.ItemBankai;
import com.github.neckitwin.common.items.ItemBoostCard;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
    public static Item.ToolMaterial BANKAI = EnumHelper.addToolMaterial("BANKAI", 9999999, 9999999, 999999, 999999, 45);
    public static final ItemBankai ITEM_BANKAI = new ItemBankai();
    public static final ItemBoostCard ITEM_BOOST_CARD = new ItemBoostCard();
    public static void register() {
        GameRegistry.registerItem(ITEM_BANKAI, "item_bankai");
        GameRegistry.registerItem(ITEM_BOOST_CARD, "item_boost_card");
    }
}
