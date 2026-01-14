package com.hypherionmc.simplerpc.loaders.forge;

import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.simplerpc.RPCConstants;
import com.hypherionmc.simplerpc.simplerpcClient;
import net.minecraftforge.fml.common.Mod;

@Mod(RPCConstants.MOD_ID)
public final class simplerpcForge {

    public simplerpcForge() {
        if (ModloaderEnvironment.INSTANCE.getEnvironment().isClient()) {
            simplerpcClient.setupEvents();
        }
    }
}
