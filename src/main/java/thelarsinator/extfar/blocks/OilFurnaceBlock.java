package thelarsinator.extfar.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import thelarsinator.extfar.animals.entity.GoatEntity;
import thelarsinator.extfar.registry.ItemRegistry;

import javax.annotation.Nonnull;
import java.util.Random;

public class OilFurnaceBlock extends HorizontalBlock {

    public static final int MAX_MILK_LEVEL = 5;

    public static final BooleanProperty HAS_OIL = BooleanProperty.create("has_oil");
    public static final BooleanProperty HAS_TANK = BooleanProperty.create("has_tank");
    public static final BooleanProperty DOOR_OPEN = BooleanProperty.create("door_open");

    private static final VoxelShape GUARD_SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 16.0D, 17.0D, 1.0D),
            Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 1.0D, 17.0D, 16.0D),
            Block.makeCuboidShape(15.0D, 1.0D, 0.0D, 16.0D, 17.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 1.0D, 15.0D, 16.0D, 17.0D, 16.0D));

    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D));

    public OilFurnaceBlock() {
        super(Properties.create(Material.ROCK).tickRandomly().hardnessAndResistance(3.0f));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(HAS_OIL, false).with(HAS_TANK, false).with(DOOR_OPEN, true);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        if(itemstack.getItem() == ItemRegistry.cannister_canola_oil)
            worldIn.setBlockState(pos, state.with(HAS_OIL, !state.get(HAS_OIL)), 3);
        else if(itemstack.getItem() == ItemRegistry.cannister_empty)
            worldIn.setBlockState(pos, state.with(HAS_TANK, !state.get(HAS_TANK)), 3);
        else
            worldIn.setBlockState(pos, state.with(DOOR_OPEN, !state.get(DOOR_OPEN)), 3);
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {

    }

    /**
     * Ticks the block if it's been scheduled
     */
    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState stateIn, World world, BlockPos pos, Random rand) {

    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HAS_OIL);
        builder.add(HAS_TANK);
        builder.add(DOOR_OPEN);
        builder.add(HORIZONTAL_FACING);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape out = SHAPE;

        return out;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return super.getCollisionShape(state, worldIn, pos, context);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isVariableOpacity() {
        return true;
    }

    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
