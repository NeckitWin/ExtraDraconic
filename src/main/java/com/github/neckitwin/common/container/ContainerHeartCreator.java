package com.github.neckitwin.common.container;

import com.github.neckitwin.common.slots.SlotHeartCreator;
import com.github.neckitwin.common.tiles.TileHeartCreator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHeartCreator extends Container {
    private TileHeartCreator tile;
    private int lastTimerValue = 0;

    public ContainerHeartCreator(InventoryPlayer player, TileHeartCreator tile) {
        this.tile = tile;
        int i = 0;

        this.addSlotToContainer(new SlotHeartCreator(tile, 0, 91, 71));
        this.addSlotToContainer(new SlotHeartCreator(tile, 1, 62, 83));
        this.addSlotToContainer(new SlotHeartCreator(tile, 2, 120, 83));
        this.addSlotToContainer(new SlotHeartCreator(tile, 3, 91, 123));

        this.addSlotToContainer(new SlotHeartCreator(tile, 4, 170, 79));
        this.addSlotToContainer(new SlotHeartCreator(tile, 5, 170, 100));
        this.addSlotToContainer(new SlotHeartCreator(tile, 6, 170, 121));


        for (i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 20 + j * 18, 173 + i * 18));

        for (i = 0; i < 9; ++i) this.addSlotToContainer(new Slot(player, i, 20 + i * 18, 230));
    }

    public ItemStack transferStackInSlot(final EntityPlayer player, final int slotIndex) {
        ItemStack itemstack = null;
        final Slot slot = (Slot) this.inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (slotIndex < 7) {
                if (!this.mergeItemStack(itemstack2, 7, 43, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack2, 0, 7, false)) {
                return null;
            }
            if (itemstack2.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    protected boolean mergeItemStack(final ItemStack stack, final int startIndex, final int endIndex, final boolean reverse) {
        boolean flag1 = false;
        int k = startIndex;
        if (reverse)
            k = endIndex - 1;

        if (stack.isStackable()) {
            while (stack.stackSize > 0 && ((!reverse && k < endIndex) || (reverse && k >= startIndex))) {
                final Slot slot = (Slot) this.inventorySlots.get(k);
                final ItemStack itemstack1 = slot.getStack();
                if (itemstack1 != null && itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1)) {
                    final int l = itemstack1.stackSize + stack.stackSize;
                    final int maxStackSize = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                    if (l <= maxStackSize) {
                        stack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    } else if (itemstack1.stackSize < maxStackSize) {
                        stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = stack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }
                if (reverse)
                    --k;
                else
                    ++k;
            }
        }
        if (stack.stackSize > 0) {
            if (reverse)
                k = endIndex - 1;
            else
                k = startIndex;
            while ((!reverse && k < endIndex) || (reverse && k >= startIndex)) {
                final Slot slot = (Slot) this.inventorySlots.get(k);
                final ItemStack itemstack1 = slot.getStack();
                if (itemstack1 == null && slot.isItemValid(stack)) {
                    final int maxStackSize2 = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                    if (stack.stackSize > maxStackSize2) {
                        slot.putStack(stack.splitStack(maxStackSize2));
                        slot.onSlotChanged();
                    } else {
                        slot.putStack(stack.copy());
                        slot.onSlotChanged();
                        stack.stackSize = 0;
                    }
                    flag1 = true;
                    if (stack.stackSize <= 0)
                        break;
                }
                if (reverse)
                    --k;
                else
                    ++k;
            }
        }
        return flag1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        if (id == 0) {
            tile.setTimer(data);
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (lastTimerValue != tile.getTimer()) {
            for (Object crafter : this.crafters) {
                ICrafting icrafting = (ICrafting) crafter;
                icrafting.sendProgressBarUpdate(this, 0, tile.getTimer());
            }
            lastTimerValue = tile.getTimer();
        }
    }
}
