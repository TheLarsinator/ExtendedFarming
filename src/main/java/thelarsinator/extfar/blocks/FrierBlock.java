package thelarsinator.extfar.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class FrierBlock extends HorizontalBlock {

    public static final BooleanProperty HAS_OIL = BooleanProperty.create("has_oil");

    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 1.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 13.0D, 16.0D),
            Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 13.0D, 16.0D));

    public FrierBlock() {
        super(Properties.create(Material.IRON).hardnessAndResistance(6.0f).tickRandomly());
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(HAS_OIL, false);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        builder.add(HAS_OIL);
    }

    private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    @SuppressWarnings("deprecation")
    public boolean isSolid(BlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return INSIDE;
    }


    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
