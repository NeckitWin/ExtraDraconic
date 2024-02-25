package com.github.neckitwin;

import com.github.neckitwin.common.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import static com.github.neckitwin.ExtraDraconic.*;

@Mod(modid = MOD_ID, version = VERSION, name = MOD_NAME)
public class ExtraDraconic {
    public static final String MOD_ID = "extradraconic";
    public static final String MOD_NAME = "Extra Draconic";
    public static final String VERSION = "1.0";

    @Mod.Instance(MOD_ID)
    public static ExtraDraconic instance;

    @SidedProxy(
            clientSide = "com.github.neckitwin.common.ClientProxy",
            serverSide = "com.github.neckitwin.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
