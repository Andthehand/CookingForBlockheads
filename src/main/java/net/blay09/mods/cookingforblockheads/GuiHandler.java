package net.blay09.mods.cookingforblockheads;

import cpw.mods.fml.common.network.IGuiHandler;
import net.blay09.mods.cookingforblockheads.tile.TileEntityCookingOven;
import net.blay09.mods.cookingforblockheads.tile.TileEntityCookingTable;
import net.blay09.mods.cookingforblockheads.tile.TileEntityFridge;
import net.blay09.mods.cookingforblockheads.client.GuiCookingOven;
import net.blay09.mods.cookingforblockheads.client.GuiFridge;
import net.blay09.mods.cookingforblockheads.client.GuiRecipeBook;
import net.blay09.mods.cookingforblockheads.container.ContainerCookingOven;
import net.blay09.mods.cookingforblockheads.container.ContainerFridge;
import net.blay09.mods.cookingforblockheads.container.ContainerRecipeBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_ID_RECIPEBOOK = 1;
    public static final int GUI_ID_CRAFTBOOK = 2;
    public static final int GUI_ID_NOFILTERBOOK = 3;
    public static final int GUI_ID_COOKINGTABLE = 4;
    public static final int GUI_ID_COOKINGOVEN = 5;
    public static final int GUI_ID_FRIDGE = 6;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case GUI_ID_RECIPEBOOK:
                return new ContainerRecipeBook(player, false, false, false);
            case GUI_ID_CRAFTBOOK:
                if(player.getHeldItem() != null && player.getHeldItem().getItemDamage() == 1) {
                    return new ContainerRecipeBook(player, true, false, false);
                }
                break;
            case GUI_ID_NOFILTERBOOK:
                if(player.getHeldItem() != null && player.getHeldItem().getItemDamage() == 3) {
                    return new ContainerRecipeBook(player, false, false, false).setNoFilter();
                }
                break;
            case GUI_ID_COOKINGTABLE:
                if(world.getBlock(x, y, z) == CookingForBlockheads.blockCookingTable) {
                    TileEntityCookingTable tileEntity = (TileEntityCookingTable) world.getTileEntity(x, y, z);
                    if(tileEntity.hasNoFilterBook()) {
                        return new ContainerRecipeBook(player, true, true, false).setNoFilter().setKitchenMultiBlock(new KitchenMultiBlock(world, x, y, z));
                    } else {
                        return new ContainerRecipeBook(player, true, true, false).setKitchenMultiBlock(new KitchenMultiBlock(world, x, y, z));
                    }
                }
                break;
            case GUI_ID_COOKINGOVEN:
                if(world.getBlock(x, y, z) == CookingForBlockheads.blockCookingOven) {
                    return new ContainerCookingOven(player.inventory, (TileEntityCookingOven) world.getTileEntity(x, y, z));
                }
                break;
            case GUI_ID_FRIDGE:
                if(world.getBlock(x, y, z) == CookingForBlockheads.blockFridge) {
                    return new ContainerFridge(player.inventory, getInventoryForFridge((TileEntityFridge) world.getTileEntity(x, y, z)));
                }
                break;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case GUI_ID_RECIPEBOOK:
                return new GuiRecipeBook(new ContainerRecipeBook(player, false, false, true));
            case GUI_ID_CRAFTBOOK:
                return new GuiRecipeBook(new ContainerRecipeBook(player, true, false, true));
            case GUI_ID_NOFILTERBOOK:
                return new GuiRecipeBook(new ContainerRecipeBook(player, false, false, true));
            case GUI_ID_COOKINGTABLE:
                return new GuiRecipeBook(new ContainerRecipeBook(player, true, true, true));
            case GUI_ID_COOKINGOVEN:
                return new GuiCookingOven(player.inventory, (TileEntityCookingOven) world.getTileEntity(x, y, z));
            case GUI_ID_FRIDGE:
                return new GuiFridge(player.inventory, getInventoryForFridge((TileEntityFridge) world.getTileEntity(x, y, z)));
        }
        return null;
    }

    private IInventory getInventoryForFridge(TileEntityFridge tileEntity) {
        tileEntity.updateMultiblock();
        return tileEntity.getInventory();
    }

}
