package com.github.neckitwin.common.handler;

import com.github.neckitwin.ExtraDraconic;
import com.github.neckitwin.common.blocks.*;
import com.github.neckitwin.common.tiles.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static final BlockHeartCreator CHAOS_CAPACITOR = new BlockHeartCreator();
    public static void register() {
        GameRegistry.registerBlock(CHAOS_CAPACITOR, "chaos_capacitor");
        // add tile entity
        GameRegistry.registerTileEntity(TileHeartCreator.class, ExtraDraconic.MOD_ID + "tile_chaos_capacitor");
    }
}
