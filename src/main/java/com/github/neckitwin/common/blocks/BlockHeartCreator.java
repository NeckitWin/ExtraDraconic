package com.github.neckitwin.common.blocks;

import com.github.neckitwin.ExtraDraconic;
import com.github.neckitwin.common.handler.ModTab;
import com.github.neckitwin.common.tiles.TileHeartCreator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHeartCreator extends BlockContainer {
    public BlockHeartCreator() {
        super(Material.wood);
        this.setBlockName("heartcreator");
        this.setBlockTextureName(ExtraDraconic.MOD_ID+":heartcreator");
        this.setHardness(0.5F);
        this.setCreativeTab(ModTab.INSTANCE);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileHeartCreator();
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
        TileHeartCreator tile = (TileHeartCreator) world.getTileEntity(x, y, z);
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
