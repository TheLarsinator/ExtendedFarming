package thelarsinator.extfar.tileentity;

import net.minecraft.tileentity.TileEntity;
import thelarsinator.extfar.registry.TileEntityRegistry;

public class PumpTileEntity extends TileEntity {
    public PumpTileEntity() {
        super(TileEntityRegistry.hose);
    }
}
