package net.gayland.raremobtracker.mixin;

import net.gayland.raremobtracker.RareMobTracker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Inject(at = @At("HEAD"), method = "addEntity")
    public void onAddEntity(Entity entity, CallbackInfo ci) {
        if(!RareMobTracker.enabled) return;
        if(entity.getDisplayName().getString().contains("❃")) {
            Text t = Text.of(String.format("Rare mob %s spawned at %s. Click to set compass target.", entity.getDisplayName().getString(), entity.getBlockPos().toShortString()));
            List<Text> a = t.getWithStyle(t.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/compass %d %d %d", entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ()))));
            MutableText mt = Text.empty();
            for(Text tt : a) {
                mt.append(tt);
            }
            MinecraftClient.getInstance().player.sendMessage(mt, false);
            World world = entity.getEntityWorld();
            if (!world.isClient) {
                world.playSound(null, MinecraftClient.getInstance().player.getBlockPos(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.BLOCKS, 1.5f, 1f);
            }
        }
    }
}
