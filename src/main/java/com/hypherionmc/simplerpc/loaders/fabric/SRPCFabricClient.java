package com.hypherionmc.simplerpc.loaders.fabric;

import com.hypherionmc.simplerpc.simplerpcClient;
import net.fabricmc.api.ClientModInitializer;

public final class SRPCFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        simplerpcClient.setupEvents();
    }
}
