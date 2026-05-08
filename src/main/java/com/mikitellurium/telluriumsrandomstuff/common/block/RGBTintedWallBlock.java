package com.mikitellurium.telluriumsrandomstuff.common.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RGBTintedWallBlock extends WallBlock implements RGBTinted {
    private final Map<BlockState, VoxelShape> shapeByIndex;
    private final Map<BlockState, VoxelShape> collisionShapeByIndex;
    
    public RGBTintedWallBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HUE, 0));
        this.shapeByIndex = this.makeShapes(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
        this.collisionShapeByIndex = this.makeShapes(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
    }

    // WallBlock class getShape() return a null value because the blockstate has an additional hue property and
    // the map key doesn't match, this change adds hue values to possible blockstates.
    private Map<BlockState, VoxelShape> makeShapes(float width, float depth, float wallPostHeight, float wallMinY, float wallLowHeight, float wallTallHeight) {
        float f = 8.0F - width;
        float f1 = 8.0F + width;
        float f2 = 8.0F - depth;
        float f3 = 8.0F + depth;
        VoxelShape voxelshape = Block.box(f, 0.0D, f, f1, wallPostHeight, f1);
        VoxelShape voxelshape1 = Block.box(f2, wallMinY, 0.0D, f3, wallLowHeight, f3);
        VoxelShape voxelshape2 = Block.box(f2, wallMinY, f2, f3, wallLowHeight, 16.0D);
        VoxelShape voxelshape3 = Block.box(0.0D, wallMinY, f2, f3, wallLowHeight, f3);
        VoxelShape voxelshape4 = Block.box(f2, wallMinY, f2, 16.0D, wallLowHeight, f3);
        VoxelShape voxelshape5 = Block.box(f2, wallMinY, 0.0D, f3, wallTallHeight, f3);
        VoxelShape voxelshape6 = Block.box(f2, wallMinY, f2, f3, wallTallHeight, 16.0D);
        VoxelShape voxelshape7 = Block.box(0.0D, wallMinY, f2, f3, wallTallHeight, f3);
        VoxelShape voxelshape8 = Block.box(f2, wallMinY, f2, 16.0D, wallTallHeight, f3);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (int hue = 0; hue < HUE_RANGE; hue++) {
            for (Boolean obool : UP.getPossibleValues()) {
                for (WallSide wallside : EAST_WALL.getPossibleValues()) {
                    for (WallSide wallside1 : NORTH_WALL.getPossibleValues()) {
                        for (WallSide wallside2 : WEST_WALL.getPossibleValues()) {
                            for (WallSide wallside3 : SOUTH_WALL.getPossibleValues()) {
                                VoxelShape voxelshape9 = Shapes.empty();
                                voxelshape9 = applyWallShape(voxelshape9, wallside, voxelshape4, voxelshape8);
                                voxelshape9 = applyWallShape(voxelshape9, wallside2, voxelshape3, voxelshape7);
                                voxelshape9 = applyWallShape(voxelshape9, wallside1, voxelshape1, voxelshape5);
                                voxelshape9 = applyWallShape(voxelshape9, wallside3, voxelshape2, voxelshape6);
                                if (obool) {
                                    voxelshape9 = Shapes.or(voxelshape9, voxelshape);
                                }

                                BlockState blockstate = this.defaultBlockState().setValue(UP, obool).setValue(EAST_WALL, wallside).setValue(WEST_WALL, wallside2).setValue(NORTH_WALL, wallside1).setValue(SOUTH_WALL, wallside3).setValue(HUE, hue);
                                builder.put(blockstate.setValue(WATERLOGGED, Boolean.FALSE), voxelshape9);
                                builder.put(blockstate.setValue(WATERLOGGED, Boolean.TRUE), voxelshape9);
                            }
                        }
                    }
                }
            }
        }

        return builder.build();
    }

    private static VoxelShape applyWallShape(VoxelShape baseShape, WallSide height, VoxelShape lowShape, VoxelShape tallShape) {
        if (height == WallSide.TALL) {
            return Shapes.or(baseShape, tallShape);
        } else {
            return height == WallSide.LOW ? Shapes.or(baseShape, lowShape) : baseShape;
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapeByIndex.get(state);
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.collisionShapeByIndex.get(pState);
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HUE);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack stack = context.getItemInHand();
        if (stack.hasTag() && stack.getTag().contains("hue")) {
            return super.defaultBlockState().setValue(HUE, stack.getTag().getInt("hue"));
        }
        return super.defaultBlockState();
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);
        for (ItemStack stack: drops) {
            RGBTinted.setStackHue(stack, state.getValue(HUE));
        }
        return drops;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = state.getBlock().asItem().getDefaultInstance();
        RGBTinted.setStackHue(stack, state.getValue(HUE));
        return stack;
    }
}
