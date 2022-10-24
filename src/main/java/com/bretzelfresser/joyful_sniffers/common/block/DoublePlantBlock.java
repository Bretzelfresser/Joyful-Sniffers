package com.bretzelfresser.joyful_sniffers.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.common.ForgeHooks;

public class DoublePlantBlock extends CropBlock {

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public DoublePlantBlock(Properties p_52247_) {
        super(p_52247_);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(getAgeProperty(), 0));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.getBlock() instanceof FarmBlock;
    }

    public DoubleBlockHalf getHalf(BlockState state) {
        return state.getValue(HALF);
    }

    // Tick function
    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (!worldIn.isAreaLoaded(pos, 1))
            return;
        if (worldIn.getRawBrightness(pos, 0) >= 9) {
            int age = getAge(state);
            if (age < getMaxAge()) {
                float f = getGrowthSpeed(this, worldIn, pos);
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                    if (age >= getMaxAge() - 2) {
                        if (worldIn.getBlockState(pos.above()) == Blocks.AIR.defaultBlockState() && worldIn.getBlockState(pos.below()).getBlock() instanceof FarmBlock) {
                            worldIn.setBlock(pos.above(), this.getStateForAge(age + 1).setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
                            worldIn.setBlock(pos, this.getStateForAge(age + 1).setValue(HALF, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
                        }else if (worldIn.getBlockState(pos.above()).getBlock() instanceof DoublePlantBlock && worldIn.getBlockState(pos.below()).getBlock() instanceof FarmBlock){
                            worldIn.setBlock(pos.above(), this.getStateForAge(age + 1).setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
                            worldIn.setBlock(pos, this.getStateForAge(age + 1).setValue(HALF, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
                        }
                    } else {
                        worldIn.setBlock(pos, this.getStateForAge(age + 1), Block.UPDATE_CLIENTS);
                    }
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }

    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return (worldIn.getRawBrightness(pos, 0) >= 8 || worldIn.canSeeSky(pos)) && placementChecker(state, worldIn, pos);
    }

    private boolean placementChecker(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockState testState = worldIn.getBlockState(pos.below());
        if (testState.getBlock() instanceof FarmBlock)
            return true;
        return testState == this.getStateForAge(getMaxAge()).setValue(HALF, DoubleBlockHalf.LOWER) && worldIn.getBlockState(pos.below(2)).getBlock() instanceof FarmBlock;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !isMaxAge(state) && worldIn.getBlockState(pos.above()) == Blocks.AIR.defaultBlockState() && worldIn.getBlockState(pos.below()).getBlock() instanceof FarmBlock;
    }

    @Override
    public void growCrops(Level worldIn, BlockPos pos, BlockState state) {
        int maxAge = getMaxAge();
        int newAge = Math.min(getAge(state) + getBonemealAgeIncrease(worldIn), maxAge);

        if (newAge >= maxAge - 1 && worldIn.getBlockState(pos.above()) == Blocks.AIR.defaultBlockState() && worldIn.getBlockState(pos.below()).getBlock() instanceof FarmBlock) {
            worldIn.setBlock(pos, getStateForAge(maxAge), Block.UPDATE_CLIENTS);
            worldIn.setBlock(pos.above(), getStateForAge(maxAge).setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
            return;
        }

        worldIn.setBlock(pos, getStateForAge(newAge), Block.UPDATE_CLIENTS);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (!worldIn.isClientSide()) {
            Block below = worldIn.getBlockState(pos.below()).getBlock();
            if (state.getBlock() == this && below == this) { //make sure the block below is also a double crop block
                DoublePlantBlock crop = (DoublePlantBlock) worldIn.getBlockState(pos).getBlock();
                if (crop.getAge(state) == getMaxAge() && crop.getHalf(state) == DoubleBlockHalf.UPPER) {
                    worldIn.setBlock(pos.below(), crop.defaultBlockState(), Block.UPDATE_CLIENTS);
                }
            }
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }


    /*
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && isMaxAge(state)) {
            entity.makeStuckInBlock(state, new Vec3(0.8D, 0.75D, 0.8D));
        }
    }

     */
}
