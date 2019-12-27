package thelarsinator.extfar.core;

import net.minecraft.block.Blocks;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import thelarsinator.extfar.Reference;
import thelarsinator.extfar.core.proxy.ClientProxy;
import thelarsinator.extfar.core.proxy.IProxy;
import thelarsinator.extfar.core.proxy.ServerProxy;

@Mod(Reference.MODID)
public class ExtendedFarming {
    public static final IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public ExtendedFarming(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        //Init proxy with registry of renderers etc.
        proxy.init();
    }
}
