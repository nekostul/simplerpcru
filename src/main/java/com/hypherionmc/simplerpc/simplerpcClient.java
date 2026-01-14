package com.hypherionmc.simplerpc;

import com.hypherionmc.craterlib.api.events.client.*;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.event.annot.CraterEventListener;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.hypherionmc.simplerpc.discord.simplerpcCore;
import com.hypherionmc.simplerpc.enums.GameType;
import com.hypherionmc.simplerpc.enums.RichPresenceState;
import com.hypherionmc.simplerpc.util.variables.RPCVariables;
import shadow.kyori.adventure.text.Component;
import shadow.kyori.adventure.text.format.NamedTextColor;
import shadow.kyori.adventure.text.format.Style;
import shadow.kyori.adventure.text.format.TextDecoration;

/**
 * @author HypherionSA
 *
 * Main Mod Entrypoint for Modloaders
 */
public final class simplerpcClient {

    private static boolean hasShownWarning = false;

    public static void setupEvents() {
        CraterEventBus.INSTANCE.registerEventListener(simplerpcClient.class);
    }

    @CraterEventListener
    public static void init(LateInitEvent event) {
        simplerpcCore.INSTANCE.init();
        simplerpcCore.INSTANCE.setLangCode(event.getOptions().getLanguage() == null ? "en_us" : event.getOptions().getLanguage());
    }

    @CraterEventListener
    public static void playerJoinGame(CraterSinglePlayerEvent.PlayerLogin event) {
        if (event.getPlayer().getStringUUID().equals(BridgedMinecraft.getInstance().getPlayer().getStringUUID())) {
            simplerpcCore.INSTANCE.getEvents().setRPCState(RichPresenceState.JOINING_GAME);
        }
    }

    @CraterEventListener
    public static void screenOpenEvent(ScreenEvent.Opening event) {
        if (event.getScreen().isTitleScreen()) {
            if (ModloaderEnvironment.INSTANCE.isModLoaded("craftpresence") && !hasShownWarning) {
                BridgedMinecraft.getInstance().showWarningScreen(
                        Component.text("Warning").style(Style.style(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)),
                        Component.text("You have both Simple RPC and CraftPresence installed. This will cause your RPC to break and behave badly. Please remove either mod and restart the game")
                );
                hasShownWarning = true;
            }

            simplerpcCore.INSTANCE.getEvents().setRPCState(RichPresenceState.MAIN_MENU);
        }

        if (event.getScreen().isRealmsScreen()) {
            simplerpcCore.INSTANCE.getEvents().setRPCState(RichPresenceState.REALM_MENU);
        }

        if (event.getScreen().isServerBrowserScreen() || event.getScreen().isDisconnetedScreen()) {
            simplerpcCore.INSTANCE.getEvents().setRPCState(RichPresenceState.SERVER_MENU);
        }

        if (event.getScreen().isPauseScreen()) {
            simplerpcCore.INSTANCE.getEvents().setRPCState(RichPresenceState.PAUSED);
        }

        if (event.getScreen().isLoadingScreen()) {
            simplerpcCore.INSTANCE.getEvents().setRPCState(RichPresenceState.JOINING_GAME);
        }
    }

    @CraterEventListener
    public static void playerJoinRealm(PlayerJoinRealmEvent event) {
        RPCVariables.realmsServer = event.getServer();
    }

    @CraterEventListener
    public static void clientTick(CraterClientTickEvent event) {
        if (event.getLevel() == null || !event.getLevel().isClientSide())
            return;

        if (event.getLevel().getGameTime() % 40L == 0L) {
            if (BridgedMinecraft.getInstance().isRealmServer()) {
                simplerpcCore.INSTANCE.getEvents().setRPCState(RichPresenceState.IN_GAME, GameType.REALM);
            } else {
                simplerpcCore.INSTANCE.getEvents().setRPCState(RichPresenceState.IN_GAME, BridgedMinecraft.getInstance().isSinglePlayer() ? GameType.SINGLE : GameType.MULTIPLAYER);
            }
        }
    }
}
