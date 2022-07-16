package io.github.offsetmonkey538.wandering_notifications.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Environment(value = EnvType.CLIENT)
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "onSpawnPacket", at = @At(value = "TAIL"))
    public void onSpawnPacket(EntitySpawnS2CPacket packet, CallbackInfo ci) {
        if ((Object) this instanceof WanderingTraderEntity) {
            WanderingTraderEntity wanderingTrader = (WanderingTraderEntity) (Object) this;
            ClientPlayerEntity player = MinecraftClient.getInstance().player;

            player.sendMessage(Text.of("A wandering trader has spawned at [" + (int) packet.getX() + " / " + (int) packet.getY() + " / " + (int) packet.getZ() + "]"));
            player.sendMessage(Text.of("Here are the trades:"));
            wanderingTrader.getOffers().forEach(offer -> {
                ItemStack firstBuyItem = offer.getAdjustedFirstBuyItem().copy();
                ItemStack secondBuyItem = offer.getSecondBuyItem().copy();
                ItemStack sellItem = offer.copySellItem();

                String firstItemText = firstBuyItem.getCount() + " of " + I18n.translate(firstBuyItem.getName().getString());
                String secondItemText = " and " + secondBuyItem.getCount() + " of " + I18n.translate(secondBuyItem.getName().getString());
                String sellItemText = sellItem.getCount() + " of " + I18n.translate(sellItem.getName().getString());

                if (secondBuyItem.isEmpty()) secondItemText = "";

                Text message = Text.of(firstItemText + secondItemText + " for " + sellItemText);

                player.sendMessage(message);
            });
        }
    }
}
