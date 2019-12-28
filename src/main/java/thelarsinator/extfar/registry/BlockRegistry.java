package thelarsinator.extfar.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import thelarsinator.extfar.blocks.HoseBlock;
import thelarsinator.extfar.blocks.PumpBlock;
import thelarsinator.extfar.blocks.SprayerBlock;
import thelarsinator.extfar.tileentity.PumpItemRenderer;
import thelarsinator.extfar.tileentity.SprayerItemRenderer;

import static thelarsinator.extfar.Reference.MODID;

@SuppressWarnings("WeakerAccess")
@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
    public static final Block sprayer = null;
    public static final Block hose = null;
    public static final Block pump = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        registry.register(new SprayerBlock().setRegistryName(MODID, "sprayer"));
        registry.register(new HoseBlock().setRegistryName(MODID, "hose"));
        registry.register(new PumpBlock().setRegistryName(MODID, "pump"));
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new BlockItem(sprayer, new Item.Properties().group(ItemGroup.MISC).setTEISR(() -> SprayerItemRenderer::new))
                .setRegistryName(MODID, "sprayer"));
        registry.register(new BlockItem(hose, new Item.Properties().group(ItemGroup.MISC))
                .setRegistryName(MODID, "hose"));
        registry.register(new BlockItem(pump, new Item.Properties().group(ItemGroup.MISC).setTEISR(() -> PumpItemRenderer::new))
                .setRegistryName(MODID, "pump"));
    }
}
