package com.github.neckitwin.common.blocks;

import com.github.neckitwin.ExtraDraconic;
import com.github.neckitwin.common.handler.ModTab;
import com.github.neckitwin.common.tiles.TileHeartCreator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockHeartCreator extends BlockContainer {
    @SideOnly(Side.CLIENT)
    private IIcon faceIcon;
    private IIcon topIcon;
    private IIcon sideIcon;

    public BlockHeartCreator() {
        super(Material.wood);
        this.setBlockName("heartcreator");
        this.setBlockTextureName(ExtraDraconic.MOD_ID + ":heartcreator");
        this.setHardness(0.5F);
        this.setCreativeTab(ModTab.INSTANCE);
        this.setResistance(6000000.0F);
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

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta == 2 && side == 2) return faceIcon;
        if (meta == 3 && side == 5) return faceIcon;
        if (meta == 0 && side == 3) return faceIcon;
        if (meta == 1 && side == 4) return faceIcon;
        if (side == 1) return topIcon;
        return sideIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);
        faceIcon = register.registerIcon(ExtraDraconic.MOD_ID + ":heartcreator_front");
        topIcon = register.registerIcon(ExtraDraconic.MOD_ID + ":machine_top");
        sideIcon = register.registerIcon(ExtraDraconic.MOD_ID + ":machine_side");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int direction = MathHelper.floor_double(((placer.rotationYaw * 4.0) / 360.0) + 2.5) & 3;
        world.setBlockMetadataWithNotify(x, y, z, direction, 2);
    }
}
