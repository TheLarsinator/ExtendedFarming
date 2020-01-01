package thelarsinator.extfar.registry;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import thelarsinator.extfar.tileentity.*;

import static thelarsinator.extfar.Reference.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityRegistry {
    public static final TileEntityType<FrierTileEntity> frier = null;

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
        IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();
        registry.register(TileEntityType.Builder.create(FrierTileEntity::new, BlockRegistry.frier)
                .build(null).setRegistryName("frier"));
    }

    public static void registerTileEntityRenderer(){
        ClientRegistry.bindTileEntitySpecialRenderer(FrierTileEntity.class, new FrierRenderer());
    }
}
