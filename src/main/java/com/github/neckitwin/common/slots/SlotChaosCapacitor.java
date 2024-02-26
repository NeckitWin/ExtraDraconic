package com.github.neckitwin.common.slots;

import com.brandon3055.draconicevolution.common.ModBlocks;
import com.brandon3055.draconicevolution.common.ModItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotChaosCapacitor extends Slot {
    public SlotChaosCapacitor(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public int getSlotStackLimit() {
        return 64;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (this.getSlotIndex() == 0) return stack.getItem() == ModItems.dragonHeart;
        else if (this.getSlotIndex() == 1 || this.getSlotIndex() == 3) return stack.getItem() == ModItems.draconicCore || stack.getItem() == ModItems.wyvernCore;
        else if (this.getSlotIndex() == 2 || this.getSlotIndex() == 4) return stack.getItem() == ItemBlock.getItemFromBlock(ModBlocks.draconiumBlock) && stack.getItemDamage() == 2;
        else if (this.getSlotIndex() == 6 || this.getSlotIndex() == 7 || this.getSlotIndex() == 8) return stack.getItem() == ModItems.chaoticCore;
        return false;
    }
}
