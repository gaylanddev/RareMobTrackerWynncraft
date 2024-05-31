package net.gayland.raremobtracker;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class RareMobTracker implements ClientModInitializer {
    public static boolean enabled = true;
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("rmt")
                .executes(context -> {
                    enabled = !enabled;
                    context.getSource().sendFeedback(Text.of("Rare Mob Tracker is now " + (enabled ? "enabled" : "disabled")));
                    return 1;
                })));
    }
}
