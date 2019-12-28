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
    public static final TileEntityType<SprayerTileEntity> sprayer = null;
    public static final TileEntityType<HoseTileEntity> hose = null;
    public static final TileEntityType<PumpTileEntity> pump = null;

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
        IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();
        registry.register(TileEntityType.Builder.create(SprayerTileEntity::new, BlockRegistry.sprayer)
                .build(null).setRegistryName("sprayer"));
        registry.register(TileEntityType.Builder.create(HoseTileEntity::new, BlockRegistry.hose)
                .build(null).setRegistryName("hose"));
        registry.register(TileEntityType.Builder.create(PumpTileEntity::new, BlockRegistry.pump)
                .build(null).setRegistryName("pump"));
    }

    public static void registerTileEntityRenderer(){
        ClientRegistry.bindTileEntitySpecialRenderer(SprayerTileEntity.class, new SprayerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(HoseTileEntity.class, new HoseRenderer());
    }

}
