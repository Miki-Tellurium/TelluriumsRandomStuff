package com.mikitellurium.telluriumsrandomstuff.common.capability;

import com.mikitellurium.telluriumsrandomstuff.common.block.SoulAnchorBlock;
import com.mikitellurium.telluriumsrandomstuff.common.blockentity.SoulAnchorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class SoulAnchorCapability {

    private boolean hasChargedAnchor;
    private boolean canRecoverInventory;
    private SimpleContainer inventory = new SimpleContainer(41) {
        @Override
        public ListTag createTag() {
            ListTag listTag = new ListTag();
            for(int i = 0; i < this.getContainerSize(); ++i) {
                CompoundTag tag = new CompoundTag();
                ItemStack itemstack = this.getItem(i);
                if (!itemstack.isEmpty()) {
                    tag.putInt("index", i);
                    itemstack.save(tag);
                    listTag.add(tag);
                }
            }

            return listTag;
        }

        @Override
        public void fromTag(ListTag listTag) {
            this.clearContent();
            for(int i = 0; i < listTag.size(); ++i) {
                CompoundTag tag = listTag.getCompound(i);
                int slot = tag.getInt("index");
                ItemStack itemstack = ItemStack.of(tag);
                if (!itemstack.isEmpty()) {
                    this.setItem(slot, itemstack);
                }
            }
        }
    };

    public boolean hasChargedAnchor() {
        return hasChargedAnchor;
    }

    public void setChargedAnchor(boolean b) {
        this.hasChargedAnchor = b;
    }

    public boolean canRecoverInventory() {
        return canRecoverInventory;
    }

    public void setCanRecoverInventory(boolean b) {
        this.canRecoverInventory = b;
    }

    public boolean hasSavedInventory() {
        return !inventory.isEmpty();
    }

    public void saveInventory(Inventory playerInventory) {
        this.clearInventory();
        for (int i = 0; i < playerInventory.getContainerSize(); i++) {
            ItemStack itemStack = playerInventory.getItem(i);
            inventory.setItem(i, itemStack.copy());
        }
    }

    public void clearInventory() {
        inventory.clearContent();
    }

    public void putInventoryInAnchor(SoulAnchorBlockEntity soulAnchor) {
        soulAnchor.savePlayerInventory(inventory);
        this.clearInventory();
    }

    public void charge(Entity entity, Level level, BlockPos pos, BlockState blockState) {
        level.setBlockAndUpdate(pos, blockState.setValue(SoulAnchorBlock.CHARGED, true));
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState));
        level.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D,
                (double)pos.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS,
                1.0F, 1.0F);
        this.setChargedAnchor(true);
    }

    public void discharge(Entity entity, Level level, BlockPos pos, BlockState blockState) {
        level.setBlockAndUpdate(pos, blockState.setValue(SoulAnchorBlock.CHARGED, false));
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState));
        level.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D,
                (double)pos.getZ() + 0.5D, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS,
                1.0F, 1.0F);
        this.setChargedAnchor(false);
    }

    public void copyFrom(SoulAnchorCapability source) {
        this.hasChargedAnchor = source.hasChargedAnchor;
        this.inventory = source.inventory;
        this.canRecoverInventory = source.canRecoverInventory;
    }

    public void saveNBTDAta(CompoundTag nbt) {
        nbt.putBoolean("hasChargedAnchor", hasChargedAnchor);
        nbt.putBoolean("canRecoverInventory", canRecoverInventory);
        nbt.put("inventory", inventory.createTag());
    }

    public void loadNBTData(CompoundTag nbt) {
        this.hasChargedAnchor = nbt.getBoolean("hasChargedAnchor");
        this.canRecoverInventory = nbt.getBoolean("canRecoverInventory");
        this.inventory.fromTag(nbt.getList("inventory", Tag.TAG_COMPOUND));
    }

}
