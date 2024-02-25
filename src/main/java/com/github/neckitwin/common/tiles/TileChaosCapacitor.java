package com.github.neckitwin.common.tiles;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyReceiver;
import com.brandon3055.draconicevolution.common.utills.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileChaosCapacitor extends TileEntity implements IInventory, IEnergyReceiver, IEnergyConnection {
    private ItemStack[] inventory;

    public EnergyStorage energy = new EnergyStorage(1000000, 10000, 0);

    @Override
    public boolean canConnectEnergy(ForgeDirection var1){
        return true;
    };

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
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote){
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
        }
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

    public TileChaosCapacitor() {
        inventory = new ItemStack[getSizeInventory()];
    }

    @Override
    public int getSizeInventory() {
        return (6);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public String getInventoryName() {
        return "Chaos Capacitor";
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

        if (compound.hasKey("Energy")){
            this.energy.readFromNBT(compound.getCompoundTag("Energy"));
        }
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 6) {
            return false;
        }
        return true;
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
