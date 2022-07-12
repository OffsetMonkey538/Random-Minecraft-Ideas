package io.github.offsetmonkey538.villager_drops.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {

    @Inject(method = "afterUsing", at = @At("TAIL"))
    protected void afterUsing(TradeOffer offer, CallbackInfo ci) {
        final SimpleInventory inventory = ((VillagerEntity) (Object) (this)).getInventory();
        final ItemStack firstBuyItem = offer.getAdjustedFirstBuyItem();
        final ItemStack secondBuyItem = offer.getSecondBuyItem();

        firstBuyItem.setCount(firstBuyItem.getCount()/2);
        secondBuyItem.setCount(secondBuyItem.getCount()/2);

        if (inventory.canInsert(firstBuyItem)) inventory.addStack(firstBuyItem);
        if (inventory.canInsert(secondBuyItem)) inventory.addStack(secondBuyItem);
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        final VillagerEntity villager = ((VillagerEntity) (Object) (this));

        for (ItemStack stack : villager.getInventory().clearToList()) {
            villager.dropStack(stack);
        }
    }
}
