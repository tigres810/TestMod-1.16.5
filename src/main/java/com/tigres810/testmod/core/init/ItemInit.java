package com.tigres810.testmod.core.init;

import com.tigres810.testmod.TestMod;
import com.tigres810.testmod.common.blocks.BlockItemBase;
import com.tigres810.testmod.common.items.InformationTabletItem;
import com.tigres810.testmod.common.items.ItemBase;
import com.tigres810.testmod.common.items.MagicStickItem;
import com.tigres810.testmod.common.items.MikuTellWorldMusicDiscItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MOD_ID);
	
	// Items
	public static final RegistryObject<Item> TESTITEM = ITEMS.register("testitem", () -> new ItemBase(new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<MagicStickItem> MAGIC_STICK = ITEMS.register("magic_stick", () -> new MagicStickItem(new Item.Properties().tab(TestMod.TAB).stacksTo(1)));
	public static final RegistryObject<InformationTabletItem> INFORMATION_TABLET = ITEMS.register("information_tablet", () -> new InformationTabletItem(new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> SMALL_GLASS = ITEMS.register("small_glass", () -> new ItemBase(new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> MORTARNPESTLE = ITEMS.register("mortarnpestle", () -> new ItemBase(new Item.Properties().tab(TestMod.TAB)));
	
	// Block Items
	public static final RegistryObject<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItemBase(BlockInit.TEST_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> FLUIDTANK_BLOCK_ITEM = ITEMS.register("fluidtank_block", () -> new BlockItemBase(BlockInit.FLUIDTANK_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> CAULDRON_BLOCK_ITEM = ITEMS.register("cauldron_block", () -> new BlockItemBase(BlockInit.CAULDRON_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> ENERGYDISPENSER_BLOCK_ITEM = ITEMS.register("energydispenser_block", () -> new BlockItemBase(BlockInit.ENERGYDISPENSER_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> ENERGYMACHINECHARGER_BLOCK_ITEM = ITEMS.register("energymachinecharger_block", () -> new BlockItemBase(BlockInit.ENERGYMACHINECHARGER_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> FLUIDDISPENSER_BLOCK_ITEM = ITEMS.register("fluiddispenser_block", () -> new BlockItemBase(BlockInit.FLUIDDISPENSER_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> FLUIDTANKCHARGER_BLOCK_ITEM = ITEMS.register("fluidtankcharger_block", () -> new BlockItemBase(BlockInit.FLUIDTANKCHARGER_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> PLANTENERGIZER_BLOCK_ITEM = ITEMS.register("plantenergizer_block", () -> new BlockItemBase(BlockInit.PLANTENERGIZER_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> FLUIDJAR_BLOCK_ITEM = ITEMS.register("fluidjar_block", () -> new BlockItemBase(BlockInit.FLUIDJAR_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> CREATIVEENERGYDISPENSER_BLOCK_ITEM = ITEMS.register("creativeenergydispenser_block", () -> new BlockItemBase(BlockInit.CREATIVEENERGYDISPENSER_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	public static final RegistryObject<Item> TESTOBJ_BLOCK_ITEM = ITEMS.register("testobj_block", () -> new BlockItemBase(BlockInit.TESTOBJ_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	
	// Fluid Buckets
	public static final RegistryObject<BucketItem> FLUX_FLUID_BUCKET = ITEMS.register("flux_fluid_bucket", () -> new BucketItem(() -> FluidInit.FLUX_FLUID.get(), new Item.Properties().tab(TestMod.TAB)));
	
	// Discs Items
	public static final RegistryObject<MikuTellWorldMusicDiscItem> MIKU_TELL_WORLD_DISC_ITEM = ITEMS.register("miku_tell_world_disc", () -> new MikuTellWorldMusicDiscItem(1, SoundInit.MIKU_TELL_WORLD, new Item.Properties().stacksTo(1).tab(TestMod.TAB)));
	
	// Dust Items
	public static final RegistryObject<Item> FLUX_DUST = ITEMS.register("dust_flux", () -> new ItemBase(new Item.Properties().tab(TestMod.TAB)));
	
	// Ores Items
	public static final RegistryObject<Item> FLUX_ORE = ITEMS.register("ingot_flux", () -> new ItemBase(new Item.Properties().tab(TestMod.TAB)));
	
	// Mineral Blocks Items
	public static final RegistryObject<Item> FLUX_ORE_BLOCK_ITEM = ITEMS.register("flux_ore", () -> new BlockItemBase(BlockInit.FLUX_ORE_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
	
	// Flower Items
	public static final RegistryObject<Item> FLUX_FLOWER_ITEM = ITEMS.register("flux_flower", () -> new BlockItemBase(BlockInit.FLUX_FLOWER_BLOCK.get(), new Item.Properties().tab(TestMod.TAB)));
}