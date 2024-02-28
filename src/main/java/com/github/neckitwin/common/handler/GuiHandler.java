package com.github.neckitwin.common.handler;

import com.github.neckitwin.common.container.ContainerHeartCreator;
import com.github.neckitwin.common.gui.GuiHeartCreator;
import com.github.neckitwin.common.tiles.TileHeartCreator;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (ID == 0 && tile instanceof TileHeartCreator)
            return new ContainerHeartCreator(player.inventory, (TileHeartCreator) tile);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (ID == 0 && tile instanceof TileHeartCreator)
            return new GuiHeartCreator(player.inventory, (TileHeartCreator) tile);
        return null;
    }
}
