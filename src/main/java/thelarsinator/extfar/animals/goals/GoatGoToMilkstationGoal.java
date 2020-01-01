package thelarsinator.extfar.animals.goals;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import thelarsinator.extfar.animals.entity.GoatEntity;
import thelarsinator.extfar.registry.BlockRegistry;

public class GoatGoToMilkstationGoal extends MoveToBlockGoal {
    private final GoatEntity goat;

    public GoatGoToMilkstationGoal(GoatEntity goat, double speedIn) {
        super(goat, speedIn, 10);
        this.goat = goat;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        return this.goat.hasMilk();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        super.startExecuting();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        super.resetTask();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();
    }

    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        if (!worldIn.isAirBlock(pos.up())) {
            return false;
        }

        BlockState blockstate = worldIn.getBlockState(pos);
        Block block = blockstate.getBlock();
        return block == BlockRegistry.milking_station;
    }
}