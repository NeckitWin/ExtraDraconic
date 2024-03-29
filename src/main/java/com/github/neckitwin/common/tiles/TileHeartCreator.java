package com.github.neckitwin.common.tiles;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyReceiver;
import com.brandon3055.draconicevolution.common.ModBlocks;
import com.brandon3055.draconicevolution.common.ModItems;
import com.brandon3055.draconicevolution.common.utills.EnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileHeartCreator extends TileEntity implements IInventory, IEnergyReceiver, IEnergyConnection, ISidedInventory {
    private ItemStack[] inventory;
    private int timer = 0;

    private int timeBoost;
    private static final int HEART_SLOT = 0;
    private static final int CORE_SLOT = 1;
    private static final int DRAKONIUM_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;
    private static final int BOOST_SLOT_1 = 4;
    private static final int BOOST_SLOT_2 = 5;
    private static final int BOOST_SLOT_3 = 6;
    int[] slots = new int[]{HEART_SLOT, CORE_SLOT, DRAKONIUM_SLOT, OUTPUT_SLOT, BOOST_SLOT_1, BOOST_SLOT_2, BOOST_SLOT_3};
    public EnergyStorage energy = new EnergyStorage(1000000, 10000, 0);

    public TileHeartCreator() {
        inventory = new ItemStack[getSizeInventory()];
    }

    public void markForSave() {
        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }

    public void markForSync() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            markForSave();
            markForSync();

            boostCheck();

            boolean hasItemsInInputSlots = true;
            for (int i = 0; i < 3; i++) {
                if (inventory[i] == null) {
                    hasItemsInInputSlots = false;
                    break;
                }
            }

            if (!hasItemsInInputSlots) return;
            if ((inventory[HEART_SLOT].getItem() == null) && (inventory[HEART_SLOT].getItem() != ModItems.dragonHeart)) return;
            if ((inventory[CORE_SLOT].getItem() != ModItems.draconicCore && inventory[CORE_SLOT].getMaxStackSize() < 8) || (inventory[CORE_SLOT].getItem() != ModItems.wyvernCore && inventory[CORE_SLOT].getMaxStackSize() < 2)) return;
            if (inventory[DRAKONIUM_SLOT].getItem() != ItemBlock.getItemFromBlock(ModBlocks.draconiumBlock) && inventory[DRAKONIUM_SLOT].getMaxStackSize() < 4 && inventory[DRAKONIUM_SLOT].getItemDamage() != 2) return;
            {
                timer++;
                if (this.energy.getEnergyStored() > 100) {
                    this.energy.modifyEnergyStored(-100);
                } else {
                    return;
                }
                if (timer == timeBoost) {
                    if (inventory[OUTPUT_SLOT] == null) {
                        inventory[OUTPUT_SLOT] = new ItemStack(ModBlocks.draconicBlock, 4);
                        craftHeartBlock();
                    } else if ((inventory[OUTPUT_SLOT].stackSize < 64) && inventory[OUTPUT_SLOT].getItem() == ItemBlock.getItemFromBlock(ModBlocks.draconicBlock)) {
                        inventory[OUTPUT_SLOT].stackSize += 4;
                        craftHeartBlock();
                    }
                    worldObj.createExplosion(null, xCoord, yCoord, zCoord, 1.5F, true);
                    timer = 0;
                }
            }
            markDirty();
        }
    }

    public void boostCheck() {
        int nonNullCount = 0;

        if (inventory[BOOST_SLOT_1] != null) nonNullCount++;
        if (inventory[BOOST_SLOT_2] != null) nonNullCount++;
        if (inventory[BOOST_SLOT_3] != null) nonNullCount++;

        switch (nonNullCount) {
            case 3:
                timeBoost = 20;
                break;
            case 2:
                timeBoost = 50;
                break;
            case 1:
                timeBoost = 100;
                break;
            default:
                timeBoost = 200;
        }
    }

    public void craftHeartBlock () {
        inventory[HEART_SLOT] = null;
        if (inventory[CORE_SLOT].getItem() == ModItems.draconicCore) {inventory[CORE_SLOT].stackSize-=16;}
        else if (inventory[CORE_SLOT].getItem() == ModItems.wyvernCore) {inventory[CORE_SLOT].stackSize-=2;}
        if (inventory[CORE_SLOT].stackSize < 1) inventory[CORE_SLOT] = null;
        inventory[DRAKONIUM_SLOT].stackSize -= 4;
        if (inventory[DRAKONIUM_SLOT].stackSize < 1) inventory[DRAKONIUM_SLOT] = null;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        energy.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        NBTTagCompound compound = pkt.func_148857_g();
        energy.readFromNBT(compound);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection var1) {
        return true;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return this.energy.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection var1) {
        return this.energy.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection var1) {
        return this.energy.getMaxEnergyStored();
    }

    @Override
    public int getSizeInventory() {
        return (7);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public String getInventoryName() {
        return "HeartCreator";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (inventory[slot] != null) {
            ItemStack itemstack = inventory[slot];
            inventory[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (inventory[slot] != null) {
            ItemStack itemstack;

            if (inventory[slot].stackSize <= amount) {
                itemstack = inventory[slot];
                inventory[slot] = null;
                markDirty();
                return itemstack;
            } else {
                itemstack = inventory[slot].splitStack(amount);

                if (inventory[slot].stackSize == 0) {
                    inventory[slot] = null;
                }

                markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList inventoryList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                inventory[i].writeToNBT(itemTag);
                inventoryList.appendTag(itemTag);
            } else {
                inventoryList.appendTag(new NBTTagCompound());
            }
        }
        compound.setTag("Inventory", inventoryList);

        NBTTagCompound energyTag = new NBTTagCompound();
        this.energy.writeToNBT(energyTag);
        compound.setTag("Energy", energyTag);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList inventoryList = compound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < inventoryList.tagCount(); i++) {
            NBTTagCompound itemTag = inventoryList.getCompoundTagAt(i);
            inventory[i] = ItemStack.loadItemStackFromNBT(itemTag);
        }

        if (compound.hasKey("Energy")) {
            this.energy.readFromNBT(compound.getCompoundTag("Energy"));
        }
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 0) {
            return stack.getItem() == ModItems.dragonHeart;
        } else if (slot == 1) {
            return stack.getItem() == ModItems.draconicCore || stack.getItem() == ModItems.wyvernCore;
        } else if (slot == 2) {
            return stack.getItem() == ItemBlock.getItemFromBlock(ModBlocks.draconiumBlock) && stack.getItemDamage() == 2;
        } else if (slot == 3) {
            if (inventory[3] != null && inventory[3].getItem() != null) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (slot == HEART_SLOT) {
            return stack.getItem() == ModItems.dragonHeart;
        } else if (slot == CORE_SLOT) {
            return stack.getItem() == ModItems.draconicCore || stack.getItem() == ModItems.wyvernCore;
        } else if (slot == DRAKONIUM_SLOT) {
            return stack.getItem() == ItemBlock.getItemFromBlock(ModBlocks.draconiumBlock) && stack.getItemDamage() == 2;
        } else {
            return false;
        }
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side)
    {
        if (slot == OUTPUT_SLOT) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int slot) {
        return slots;
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public void openInventory() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }
}
