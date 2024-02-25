package com.github.neckitwin.common;

import com.github.neckitwin.ExtraDraconic;
import com.github.neckitwin.common.handler.GuiHandler;
import com.github.neckitwin.common.handler.ModBlocks;
import com.github.neckitwin.common.handler.ModItems;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public void registerGuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(ExtraDraconic.instance, new GuiHandler());
    }

    public void preInit(FMLPreInitializationEvent event) {
        ModItems.register();
        ModBlocks.register();
    }

    public void init(FMLInitializationEvent event) {
        registerGuiHandler();
    }

    public void postInit(FMLPostInitializationEvent event) {}


}