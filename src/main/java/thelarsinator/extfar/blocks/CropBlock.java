package thelarsinator.extfar.blocks;

import net.minecraft.block.CropsBlock;

public class CropBlock extends CropsBlock {
    public CropBlock(Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0));
    }
}
