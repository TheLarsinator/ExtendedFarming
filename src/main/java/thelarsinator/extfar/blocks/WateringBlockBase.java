package thelarsinator.extfar.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class WateringBlockBase extends HorizontalBlock {
    public static final BooleanProperty IS_ACTIVE = BooleanProperty.create("is_active");

    public static final BooleanProperty EAST_CON = BooleanProperty.create("east_con");
    public static final BooleanProperty WEST_CON = BooleanProperty.create("west_con");
    public static final BooleanProperty NORTH_CON = BooleanProperty.create("north_con");
    public static final BooleanProperty SOUTH_CON = BooleanProperty.create("south_con");

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WEST_CON);
        builder.add(EAST_CON);
        builder.add(NORTH_CON);
        builder.add(SOUTH_CON);
        builder.add(IS_ACTIVE);
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    public WateringBlockBase(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(IS_ACTIVE, false).with(WEST_CON,
                false).with(EAST_CON, false).with(NORTH_CON, false).with(SOUTH_CON, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockState updatePostPlacement(@Nonnull BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
                                          BlockPos currentPos, BlockPos facingPos) {
        return checkForConnections(stateIn, worldIn.getWorld(), currentPos);
    }

    protected BlockState checkForConnections(@Nonnull BlockState stateIn, @Nonnull World world, @Nonnull BlockPos pos){
        return stateIn
                .with(NORTH_CON, checkIfConnection(world, pos.north()))
                .with(SOUTH_CON, checkIfConnection(world, pos.south()))
                .with(EAST_CON, checkIfConnection(world, pos.east()))
                .with(WEST_CON, checkIfConnection(world, pos.west()));
    }

    protected boolean checkIfConnection(@Nonnull World world, @Nonnull BlockPos pos){
        return world.getBlockState(pos).getBlock() instanceof WateringBlockBase;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World world, BlockPos pos, Random rand) {
        if(!world.getBlockState(pos).get(IS_ACTIVE)){
            return;
        }
        if(stateIn.get(NORTH_CON)){
            world.setBlockState(pos.north(), world.getBlockState(pos.north()).with(IS_ACTIVE, true), 3);
        }
        if(stateIn.get(SOUTH_CON)){
            world.setBlockState(pos.south(), world.getBlockState(pos.south()).with(IS_ACTIVE, true), 3);
        }
        if(stateIn.get(EAST_CON)){
            world.setBlockState(pos.east(), world.getBlockState(pos.east()).with(IS_ACTIVE, true), 3);
        }
        if(stateIn.get(WEST_CON)){
            world.setBlockState(pos.west(), world.getBlockState(pos.west()).with(IS_ACTIVE, true), 3);
        }
    }
}
