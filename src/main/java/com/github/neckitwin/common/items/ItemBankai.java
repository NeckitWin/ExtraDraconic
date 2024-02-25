package com.github.neckitwin.common.items;

import com.github.neckitwin.ExtraDraconic;
import com.github.neckitwin.common.handler.ModItems;
import com.github.neckitwin.common.handler.ModTab;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemBankai extends ItemSword {
    public ItemBankai() {
        super(ModItems.BANKAI);
        this.setUnlocalizedName("Bankai");
        this.setTextureName(ExtraDraconic.MOD_ID + ":bankai");
        this.setCreativeTab(ModTab.INSTANCE);
    }

    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return true;
    }

    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        return 9999999.0F;
    }

    public boolean hitEntity(ItemStack stack, net.minecraft.entity.EntityLivingBase target, net.minecraft.entity.EntityLivingBase attacker) {
        return true;
    }

    public boolean onBlockDestroyed(ItemStack stack, net.minecraft.world.World world, Block block, int x, int y, int z, net.minecraft.entity.EntityLivingBase entity) {
        return true;
    }

    public boolean isFull3D() {
        return true;
    }

    public boolean getIsRepairable(ItemStack stack, ItemStack stack2) {
        return stack2.getItem() == Items.diamond;
    }

    public boolean isItemTool(ItemStack stack) {
        return true;
    }
}
