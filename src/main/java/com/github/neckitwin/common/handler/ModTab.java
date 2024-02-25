package com.github.neckitwin.common.handler;

import com.github.neckitwin.ExtraDraconic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModTab extends CreativeTabs {
    public static final ModTab INSTANCE = new ModTab();

    private ModTab() {
        super(ExtraDraconic.MOD_ID);
        setBackgroundImageName("item_search.png");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return ModItems.ITEM_BANKAI;
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
