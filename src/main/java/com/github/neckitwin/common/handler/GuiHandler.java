package com.github.neckitwin.common.handler;

import com.github.neckitwin.common.container.ContainerChaosCapacitor;
import com.github.neckitwin.common.gui.GuiChaosCapacitor;
import com.github.neckitwin.common.tiles.TileChaosCapacitor;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (ID == 0 && tile instanceof TileChaosCapacitor)
            return new ContainerChaosCapacitor(player.inventory, (TileChaosCapacitor) tile);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (ID == 0 && tile instanceof TileChaosCapacitor)
            return new GuiChaosCapacitor(player.inventory, (TileChaosCapacitor) tile);
        return null;
    }
}
