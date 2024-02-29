package com.github.neckitwin.common.items;

import com.github.neckitwin.ExtraDraconic;
import com.github.neckitwin.common.handler.ModTab;
import net.minecraft.item.Item;

public class ItemBoostCard extends Item {
    public ItemBoostCard() {
        super();
        this.setUnlocalizedName("boostcard");
        this.setTextureName(ExtraDraconic.MOD_ID+ ":boostсard");
        this.setCreativeTab(ModTab.INSTANCE);
    }
}
