package com.mikitellurium.telluriumsrandomstuff.capability;

import com.mikitellurium.telluriumsrandomstuff.block.custom.SoulAnchorBlock;
import com.mikitellurium.telluriumsrandomstuff.blockentity.custom.SoulAnchorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class SoulAnchorCapability {

    private final int playerInventorySize = 41;
    private boolean hasChargedAnchor;
    private SimpleContainer inventory = new SimpleContainer(playerInventorySize);
    private boolean hasRecentlyDied;

    public boolean hasChargedAnchor() {
        return hasChargedAnchor;
    }

    public void setChargedAnchor(boolean b) {
        this.hasChargedAnchor = b;
    }

    public void saveInventory(Inventory playerInventory) {
        for (int i = 0; i < playerInventorySize; i++) {
            ItemStack itemStack = playerInventory.getItem(i);
            inventory.setItem(i, itemStack.copy());
        }
    }

    public void clearInventory() {
        inventory.clearContent();
    }

    public void putInventoryInAnchor(SoulAnchorBlockEntity soulAnchor) {
        soulAnchor.savePlayerInventory(inventory);
    }

    public boolean hasSavedInventory() {
        return !inventory.isEmpty();
    }

    public boolean hasRecentlyDied() {
        return hasRecentlyDied;
    }

    public void setRecentlyDied(boolean b) {
        this.hasRecentlyDied = b;
    }

    public void charge(Entity entity, Level level, BlockPos pos, BlockState state) {
        SoulAnchorBlock.charge(entity, level, pos, state);
        setChargedAnchor(true);
    }

    public void discharge(Entity entity, Level level, BlockPos pos, BlockState state) {
        SoulAnchorBlock.discharge(entity, level, pos, state);
        setChargedAnchor(false);
    }

    public void copyFrom(SoulAnchorCapability source) {
        this.hasChargedAnchor = source.hasChargedAnchor;
        this.inventory = source.inventory;
        this.hasRecentlyDied = source.hasRecentlyDied;
    }

    public void saveNBTDAta(CompoundTag nbt) {
        nbt.putBoolean("hasChargedAnchor", hasChargedAnchor);
        nbt.put("inventory", inventory.createTag());
        nbt.putBoolean("hasRecentlyDied", hasRecentlyDied);
    }

    public void loadNBTData(CompoundTag nbt) {
        hasChargedAnchor = nbt.getBoolean("hasChargedAnchor");
        inventory.fromTag(nbt.getList("inventory", Tag.TAG_COMPOUND));
        hasRecentlyDied = nbt.getBoolean("hasRecentlyDied");
    }

}
