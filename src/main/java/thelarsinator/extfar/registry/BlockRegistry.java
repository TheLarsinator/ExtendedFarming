package thelarsinator.extfar.registry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import thelarsinator.extfar.blocks.*;

import static thelarsinator.extfar.Reference.MODID;

@SuppressWarnings("WeakerAccess")
@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
    public static final Block sprayer = null;
    public static final Block hose = null;
    public static final Block pump = null;
    public static final Block frier = null;
    public static final Block milking_station = null;

    public static final Block crop_support_stick = null;
    public static final CropBlock beans = null;
    public static final CropBlock chilli_pepper = null;
    public static final CropBlock canola = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        //Machines of some sort
        registry.register(new SprayerBlock().setRegistryName(MODID, "sprayer"));
        registry.register(new HoseBlock().setRegistryName(MODID, "hose"));
        registry.register(new PumpBlock().setRegistryName(MODID, "pump"));
        registry.register(new FrierBlock().setRegistryName(MODID, "frier"));
        registry.register(new MilkingStationBlock().setRegistryName(MODID, "milking_station"));

        //Crops
        registry.register(new CropSupportBlock().setRegistryName(MODID, "crop_support_stick"));
        registry.register(new CropBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP)).setRegistryName(MODID, "beans"));
        registry.register(new CropBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP)).setRegistryName(MODID, "chilli_pepper"));
        registry.register(new CropBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP)).setRegistryName(MODID, "canola"));
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new BlockItem(sprayer, new Item.Properties().group(ExtfarItemGroup.EXT_FAR))
                .setRegistryName(MODID, "sprayer"));
        registry.register(new BlockItem(hose, new Item.Properties().group(ExtfarItemGroup.EXT_FAR))
                .setRegistryName(MODID, "hose"));
        registry.register(new BlockItem(frier, new Item.Properties().group(ExtfarItemGroup.EXT_FAR))
                .setRegistryName(MODID, "frier"));
        registry.register(new BlockItem(crop_support_stick, new Item.Properties().group(ExtfarItemGroup.EXT_FAR))
                .setRegistryName(MODID, "crop_support_stick"));
        registry.register(new BlockItem(pump, new Item.Properties().group(ExtfarItemGroup.EXT_FAR))
                .setRegistryName(MODID, "pump"));
        registry.register(new BlockItem(milking_station, new Item.Properties().group(ExtfarItemGroup.EXT_FAR))
                .setRegistryName(MODID, "milking_station"));
    }
}
