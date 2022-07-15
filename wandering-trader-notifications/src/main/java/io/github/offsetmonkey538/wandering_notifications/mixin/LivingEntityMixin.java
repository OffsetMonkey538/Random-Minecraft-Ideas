package io.github.offsetmonkey538.wandering_notifications.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {


    @Inject(method = "onSpawnPacket", at = @At(value = "TAIL"))
    public void onSpawnPacket(EntitySpawnS2CPacket packet, CallbackInfo ci) {

    }
}
