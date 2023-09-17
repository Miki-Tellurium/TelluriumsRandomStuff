package com.mikitellurium.telluriumsrandomstuff.common.mixin;

import com.mikitellurium.telluriumsrandomstuff.util.LevelUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentItem.class)
public class TridentItemMixin {

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;",
    shift = At.Shift.AFTER), cancellable = true)
    private void onUseTrident(Level level, Player player, InteractionHand hand,
                              CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (!level.isClientSide) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (EnchantmentHelper.getRiptide(itemstack) > 0 &&
                    LevelUtils.isInsideWaterCauldron(level, player)) {
                player.startUsingItem(hand);
                cir.setReturnValue(InteractionResultHolder.consume(itemstack));
            }
        }
    }

}
