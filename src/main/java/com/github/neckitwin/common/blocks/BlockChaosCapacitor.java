package com.github.neckitwin.common.blocks;

import com.github.neckitwin.ExtraDraconic;
import com.github.neckitwin.common.handler.ModTab;
import com.github.neckitwin.common.tiles.TileChaosCapacitor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockChaosCapacitor extends BlockContainer {
    public BlockChaosCapacitor() {
        super(Material.wood);
        this.setBlockName("chaos_capacitor");
        this.setBlockTextureName(ExtraDraconic.MOD_ID+":chaos_capacitor");
        this.setHardness(0.5F);
        this.setCreativeTab(ModTab.INSTANCE);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileChaosCapacitor();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer playerEntity, int side, float hitX, float hitY, float hitZ) {
        playerEntity.openGui(ExtraDraconic.instance, 0, world, x, y, z);
        return true;
    }

    @Override
    public int getLightValue() {
        return 15;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        TileChaosCapacitor tile = (TileChaosCapacitor) world.getTileEntity(x, y, z);
        if (tile != null) {
            for (int i = 0; i < tile.getSizeInventory(); i++) {
                if (tile.getStackInSlot(i) != null) {
                    this.dropBlockAsItem(world, x, y, z, tile.getStackInSlot(i));
                }
            }
        }
        super.breakBlock(world, x, y, z, block, metadata);
    }
}
