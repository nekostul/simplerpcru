package com.hypherionmc.simplerpc.loaders.neoforge;

import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.simplerpc.RPCConstants;
import com.hypherionmc.simplerpc.simplerpcClient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(RPCConstants.MOD_ID)
public final class simplerpcNeoForge {

    public simplerpcNeoForge(IEventBus eventBus) {
        if (ModloaderEnvironment.INSTANCE.getEnvironment().isClient()) {
            simplerpcClient.setupEvents();
        }
    }
}
