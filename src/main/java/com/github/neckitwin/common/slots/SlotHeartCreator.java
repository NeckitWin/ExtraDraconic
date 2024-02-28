package com.github.neckitwin.common.slots;

import com.brandon3055.draconicevolution.common.ModBlocks;
import com.brandon3055.draconicevolution.common.ModItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotHeartCreator extends Slot {
    public SlotHeartCreator(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public int getSlotStackLimit() {
        if (this.getSlotIndex() == 4) return 1;
        else if (this.getSlotIndex() == 5) return 1;
        else if (this.getSlotIndex() == 6) return 1;
        else return 64;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (this.getSlotIndex() == 0) return stack.getItem() == ModItems.dragonHeart;
        else if (this.getSlotIndex() == 1 ) return stack.getItem() == ModItems.draconicCore || stack.getItem() == ModItems.wyvernCore;
        else if (this.getSlotIndex() == 2 ) return stack.getItem() == ItemBlock.getItemFromBlock(ModBlocks.draconiumBlock) && stack.getItemDamage() == 2;
        else if (this.getSlotIndex() == 4 || this.getSlotIndex() == 5 || this.getSlotIndex() == 6) return stack.getItem() == com.github.neckitwin.common.handler.ModItems.ITEM_BOOST_CARD;
        return false;
    }
}
