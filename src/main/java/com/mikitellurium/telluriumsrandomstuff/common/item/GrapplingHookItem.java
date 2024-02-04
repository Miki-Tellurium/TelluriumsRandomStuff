package com.mikitellurium.telluriumsrandomstuff.common.item;

import com.mikitellurium.telluriumsrandomstuff.common.entity.GrapplingHookEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

public class GrapplingHookItem extends Item implements Vanishable {

    public GrapplingHookItem() {
        super(new Item.Properties()
                .stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            GrapplingHookEntity grapplingHook = new GrapplingHookEntity(player, level);
            level.addFreshEntity(grapplingHook);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

}
