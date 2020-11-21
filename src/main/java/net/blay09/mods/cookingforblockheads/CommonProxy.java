package net.blay09.mods.cookingforblockheads;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.blay09.mods.cookingforblockheads.addon.VanillaAddon;
import net.blay09.mods.cookingforblockheads.block.*;
import net.blay09.mods.cookingforblockheads.item.*;
import net.blay09.mods.cookingforblockheads.item.ItemBlockSink;
import net.blay09.mods.cookingforblockheads.item.ItemBlockToaster;
import net.blay09.mods.cookingforblockheads.network.NetworkHandler;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.blay09.mods.cookingforblockheads.block.TileEntityCookingOven;
import net.blay09.mods.cookingforblockheads.block.TileEntityCookingTable;
import net.blay09.mods.cookingforblockheads.block.TileEntityFridge;
import net.blay09.mods.cookingforblockheads.block.TileEntitySink;
import net.blay09.mods.cookingforblockheads.block.TileEntityToaster;
import net.blay09.mods.cookingforblockheads.block.TileEntityToolRack;
import net.blay09.mods.cookingforblockheads.item.ItemBlockCookingOven;
import net.blay09.mods.cookingforblockheads.item.ItemBlockCookingTable;
import net.blay09.mods.cookingforblockheads.item.ItemBlockFridge;
import net.blay09.mods.cookingforblockheads.item.ItemBlockToolRack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CommonProxy {

	private boolean mineTweakerHasPostReload;

	public void preInit(FMLPreInitializationEvent event) {}

	public void init(FMLInitializationEvent event) {
		GameRegistry.registerItem(CookingForBlockheads.itemRecipeBook, "recipebook");
		GameRegistry.registerItem(CookingForBlockheads.itemToast, "toast");
		GameRegistry.registerBlock(CookingForBlockheads.blockCookingTable, ItemBlockCookingTable.class, "cookingtable");
		GameRegistry.registerBlock(CookingForBlockheads.blockCookingOven, ItemBlockCookingOven.class, "cookingoven");
		GameRegistry.registerBlock(CookingForBlockheads.blockFridge, ItemBlockFridge.class, "fridge");
		GameRegistry.registerBlock(CookingForBlockheads.blockSink, ItemBlockSink.class, "sink");
		GameRegistry.registerBlock(CookingForBlockheads.blockToolRack, ItemBlockToolRack.class, "toolrack");
		GameRegistry.registerBlock(CookingForBlockheads.blockToaster, ItemBlockToaster.class, "toaster");
		GameRegistry.registerTileEntity(TileEntityCookingOven.class, CookingForBlockheads.MOD_ID + ":cookingoven");
		GameRegistry.registerTileEntity(TileEntityFridge.class, CookingForBlockheads.MOD_ID + ":fridge");
		GameRegistry.registerTileEntity(TileEntityToolRack.class, CookingForBlockheads.MOD_ID + ":toolrack");
		GameRegistry.registerTileEntity(TileEntitySink.class, CookingForBlockheads.MOD_ID + ":sink");
		GameRegistry.registerTileEntity(TileEntityCookingTable.class, CookingForBlockheads.MOD_ID + ":cookingtable");
		GameRegistry.registerTileEntity(TileEntityToaster.class, CookingForBlockheads.MOD_ID + ":toaster");

		// #NoFilter Edition
		if(CookingConfig.enableNoFilter) {
			GameRegistry.addShapelessRecipe(new ItemStack(CookingForBlockheads.itemRecipeBook, 1, 3), Items.book, Items.painting);
		}

		// Cooking for Blockheads I
		GameRegistry.addSmelting(Items.book, new ItemStack(CookingForBlockheads.itemRecipeBook), 0f);
		if (CookingConfig.enableNoFilter) {
			GameRegistry.addSmelting(new ItemStack(CookingForBlockheads.itemRecipeBook, 1, 3), new ItemStack(CookingForBlockheads.itemRecipeBook), 0f);
		}

		// Cooking for Blockheads II
		if(CookingConfig.enableCraftingBook) {
			GameRegistry.addRecipe(new ItemStack(CookingForBlockheads.itemRecipeBook, 1, 1), " C ", "DBD", " C ", 'C', Blocks.crafting_table, 'D', Items.diamond, 'B', CookingForBlockheads.itemRecipeBook);
		}

		// Fridge
		GameRegistry.addShapelessRecipe(new ItemStack(CookingForBlockheads.blockFridge), Blocks.chest, Items.iron_door);

		// Sink
		if(CookingConfig.enableSink) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CookingForBlockheads.blockSink), "III", "WBW", "WWW", 'I', "ingotIron", 'W', "logWood", 'B', Items.water_bucket));
		}

		// Toaster
		if(CookingConfig.enableToaster) {
			// TODO Toaster Recipe
			//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CookingForBlockheads.blockSink), "III", "WBW", "WWW", 'I', "ingotIron", 'W', "logWood", 'B', Items.water_bucket));
		}

		// Cooking Table
		if(CookingConfig.enableCraftingBook) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CookingForBlockheads.blockCookingTable), "CCC", "WBW", "WWW", 'B', new ItemStack(CookingForBlockheads.itemRecipeBook, 1, 1), 'W', "logWood", 'C', new ItemStack(Blocks.stained_hardened_clay, 1, 15)));
		}

		// Cooking Oven
		if(CookingConfig.enableOven) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CookingForBlockheads.blockCookingOven), "GGG", "IFI", "III", 'G', new ItemStack(Blocks.stained_glass, 1, 15), 'I', "ingotIron", 'F', Blocks.furnace));
		}

		// Tool Rack
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CookingForBlockheads.blockToolRack), "PPP", "I I", 'P', Blocks.wooden_pressure_plate, 'I', "ingotIron"));

		NetworkHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(CookingForBlockheads.instance, new GuiHandler());
	}

	public void postInit(FMLPostInitializationEvent event) {
		if(CookingConfig.moduleVanilla) {
			new VanillaAddon();
		}

		try {
			Class mtClass = Class.forName("minetweaker.MineTweakerImplementationAPI");
			mtClass.getMethod("onPostReload", Class.forName("minetweaker.util.IEventHandler"));
			event.buildSoftDependProxy("MineTweaker3", "net.blay09.mods.cookingforblockheads.addon.MineTweakerAddon");
			mineTweakerHasPostReload = true;
		} catch (ClassNotFoundException | NoSuchMethodException ignored) {}

		if(CookingConfig.moduleHarvestCraft) {
			event.buildSoftDependProxy("harvestcraft", "net.blay09.mods.cookingforblockheads.addon.HarvestCraftAddon");
		}
		if(CookingConfig.moduleEnviroMine) {
			event.buildSoftDependProxy("enviromine", "net.blay09.mods.cookingforblockheads.addon.EnviroMineAddon");
		}
		if(CookingConfig.moduleAppleCore) {
			event.buildSoftDependProxy("AppleCore", "net.blay09.mods.cookingforblockheads.addon.AppleCoreAddon");
		}

		CookingRegistry.initFoodRegistry();
	}

	public void serverStarted(FMLServerStartedEvent event) {
		if(!mineTweakerHasPostReload && Loader.isModLoaded("MineTweaker3")) {
			CookingRegistry.initFoodRegistry();
		}
	}
}
