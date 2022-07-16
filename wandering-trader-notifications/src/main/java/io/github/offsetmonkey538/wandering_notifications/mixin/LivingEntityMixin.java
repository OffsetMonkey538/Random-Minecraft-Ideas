package io.github.offsetmonkey538.wandering_notifications.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value = EnvType.CLIENT)
@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "onSpawnPacket", at = @At(value = "TAIL"))
    public void onSpawnPacket(EntitySpawnS2CPacket packet, CallbackInfo ci) {
        if ((Object) this instanceof WanderingTraderEntity) {
            WanderingTraderEntity wanderingTrader = (WanderingTraderEntity) (Object) this;

            wanderingTrader.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200));

            ClientPlayerEntity player = MinecraftClient.getInstance().player;

            player.sendMessage(Text.of(I18n.translate("commands.summon.success", I18n.translate("entity.minecraft.wandering_trader")) + " [" + (int) packet.getX() + " / " + (int) packet.getY() + " / " + (int) packet.getZ() + "]"));
        }
    }

    @Redirect(method = "isGlowing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isClient()Z"))
    public boolean isGlowing(World instance) {
        return false;
    }
}
