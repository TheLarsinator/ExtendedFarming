package thelarsinator.extfar.registry;

import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static thelarsinator.extfar.Reference.MODID;

@SuppressWarnings({"unused", "WeakerAccess"})
@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {

    //Goat items
    public static final Item goat_milk_bucket = null;

    //Farming items
    public static final Item net = null;

    //Crop seeds
    public static final Item beans = null;
    public static final Item chilli_pepper = null;
    public static final Item canola_seeds = null;

    //Oil-related
    public static final Item cannister_empty = null;
    public static final Item cannister_canola_oil = null;

    //Food
    public static final Item raw_fries = null;
    public static final Item fries = null;

    private static final Food FRIES = (new Food.Builder()).hunger(6).saturation(0.1f).fastToEat().build();


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new MilkBucketItem(new Item.Properties().group(ExtfarItemGroup.EXT_FAR).maxStackSize(1)).setRegistryName(MODID, "goat_milk_bucket"));
        registry.register(new Item(new Item.Properties().group(ExtfarItemGroup.EXT_FAR).maxStackSize(64)).setRegistryName(MODID, "net"));
        registry.register(new Item(new Item.Properties().group(ExtfarItemGroup.EXT_FAR).maxStackSize(64).food(Foods.CARROT)).setRegistryName(MODID, "beans"));
        registry.register(new Item(new Item.Properties().group(ExtfarItemGroup.EXT_FAR).maxStackSize(64).food(Foods.APPLE)).setRegistryName(MODID, "chilli_pepper"));
        registry.register(new BlockNamedItem(BlockRegistry.canola, (new Item.Properties()).group(ExtfarItemGroup.EXT_FAR)).setRegistryName(MODID, "canola_seeds"));
        registry.register(new Item(new Item.Properties().group(ExtfarItemGroup.EXT_FAR).maxStackSize(64)).setRegistryName(MODID, "cannister_canola_oil"));
        registry.register(new Item(new Item.Properties().group(ExtfarItemGroup.EXT_FAR).maxStackSize(64)).setRegistryName(MODID, "cannister_empty"));
        registry.register(new Item(new Item.Properties().group(ExtfarItemGroup.EXT_FAR).maxStackSize(64)).setRegistryName(MODID, "raw_fries"));
        registry.register(new Item(new Item.Properties().group(ExtfarItemGroup.EXT_FAR).maxStackSize(64).food(FRIES)).setRegistryName(MODID, "fries"));
    }

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event) {
        if (event.getName().equals(new ResourceLocation("minecraft","abandoned_mineshaft"))) {
            event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(MODID,"extfar:chests/abandoned_mineshaft"))).build());
        }
    }
}
