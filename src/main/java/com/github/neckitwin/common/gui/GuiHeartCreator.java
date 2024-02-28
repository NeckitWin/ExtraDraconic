package com.github.neckitwin.common.gui;

import com.github.neckitwin.ExtraDraconic;
import com.github.neckitwin.common.container.ContainerHeartCreator;
import com.github.neckitwin.common.tiles.TileHeartCreator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static cofh.repack.codechicken.lib.render.FontUtils.fontRenderer;

public class GuiHeartCreator extends GuiContainer {
    private TileHeartCreator tile;
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtraDraconic.MOD_ID + ":textures/gui/GuiChaosCapacitor.png");

    public GuiHeartCreator(InventoryPlayer player, TileHeartCreator tile) {
        super(new ContainerHeartCreator(player, tile));
        this.tile = tile;
        this.xSize = 200;
        this.ySize = 256;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float size, int x, int y) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.getTextureManager().bindTexture(BACKGROUND);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int energy = tile.getEnergyStored(ForgeDirection.UP);
        int maxEnergy = tile.getMaxEnergyStored(ForgeDirection.UP);

        int energyBarHeight = (int) ((double) energy / (double) maxEnergy * 100);
        this.drawTexturedModalRect(guiLeft + 12, guiTop + 55 + (100-energyBarHeight), 200, 0, 18, energyBarHeight);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int x, int y, float size) {
        super.drawScreen(x, y, size);
        if (x >= guiLeft + 12 && x <= guiLeft + 30 && y >= guiTop + 55 && y <= guiTop + 155) {
            List<String> text = new ArrayList<>();
            text.add("Energy: " + tile.getEnergyStored(ForgeDirection.UP) + " / " + tile.getMaxEnergyStored(ForgeDirection.UP) + " RF");
            drawHoveringText(text, x, y, fontRenderer);
        }
    }
}