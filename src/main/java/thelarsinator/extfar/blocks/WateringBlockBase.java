package thelarsinator.extfar.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class WateringBlockBase extends Block {
    public static final BooleanProperty IS_ACTIVE = BooleanProperty.create("is_active");

    public static final BooleanProperty EAST_CON = BooleanProperty.create("east_con");
    public static final BooleanProperty WEST_CON = BooleanProperty.create("west_con");
    public static final BooleanProperty NORTH_CON = BooleanProperty.create("north_con");
    public static final BooleanProperty SOUTH_CON = BooleanProperty.create("south_con");

    public static final IntegerProperty WATER_PRESSURE = IntegerProperty.create("water_pressure", 0, 15);

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WEST_CON);
        builder.add(EAST_CON);
        builder.add(NORTH_CON);
        builder.add(SOUTH_CON);
        builder.add(IS_ACTIVE);
        builder.add(WATER_PRESSURE);
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
        BlockState tempState = checkForConnections(stateIn, worldIn.getWorld(), currentPos);
        return checkWaterPressure(tempState, worldIn.getWorld(), currentPos);
    }

    protected BlockState checkWaterPressure(@Nonnull BlockState stateIn, @Nonnull World world, @Nonnull BlockPos pos){
        int highestPressure = 0;
        if(stateIn.get(NORTH_CON)){
            int pres = world.getBlockState(pos.north()).get(WATER_PRESSURE);
            if(world.getBlockState(pos.north()).get(WATER_PRESSURE) > highestPressure){
                highestPressure = pres;
            }
        }
        if(stateIn.get(SOUTH_CON)){
            int pres = world.getBlockState(pos.south()).get(WATER_PRESSURE);
            if(world.getBlockState(pos.south()).get(WATER_PRESSURE) > highestPressure){
                highestPressure = pres;
            }
        }
        if(stateIn.get(EAST_CON)){
            int pres = world.getBlockState(pos.east()).get(WATER_PRESSURE);
            if(world.getBlockState(pos.east()).get(WATER_PRESSURE) > highestPressure){
                highestPressure = pres;
            }
        }
        if(stateIn.get(WEST_CON)){
            int pres = world.getBlockState(pos.west()).get(WATER_PRESSURE);
            if(world.getBlockState(pos.west()).get(WATER_PRESSURE) > highestPressure){
                highestPressure = pres;
            }
        }
        int water_pressure = Math.max(highestPressure-1, 0);
        boolean is_active = water_pressure > 0;
        return stateIn.with(WATER_PRESSURE, water_pressure).with(IS_ACTIVE, is_active);
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
}
