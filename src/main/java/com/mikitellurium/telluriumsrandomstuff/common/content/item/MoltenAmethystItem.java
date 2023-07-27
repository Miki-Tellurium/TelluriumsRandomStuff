package com.mikitellurium.telluriumsrandomstuff.common.content.item;

import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class MoltenAmethystItem extends Item {

    public MoltenAmethystItem() {
        super(new Item.Properties().fireResistant());
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.isInWaterOrBubble() && !isInsideWaterCauldron(entity.level(), entity)) {
            return false;
        } else {
            Level level = entity.level();
            Vec3 pos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
            ItemEntity lens = new ItemEntity(level, pos.x, pos.y + 0.01D, pos.z, new ItemStack(ModItems.AMETHYST_LENS.get(),
                    stack.getCount()));
            entity.remove(Entity.RemovalReason.DISCARDED);
            level.addFreshEntity(lens);
            lens.getDeltaMovement().add(0.0D, 0.02D, 0.0D);
            return true;
        }
    }

    public boolean isInsideWaterCauldron(Level level, ItemEntity entity) {
        BlockPos blockPos = entity.blockPosition();
        return level.getBlockState(blockPos).is(Blocks.WATER_CAULDRON);
    }

}
