package net.blay09.mods.cookingforblockheads.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockKitchenFloor extends ItemBlock {

	public ItemBlockKitchenFloor(Block block) {
		super(block);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean flag) {
		super.addInformation(itemStack, player, list, flag);

		list.add("\u00a7e" + I18n.format("cookingforblockheads:multiblockKitchen"));
		for (String s : I18n.format("cookingforblockheads:kitchen_floor.tooltipDesc").split("\\\\n")) {
			list.add("\u00a77" + s);
		}
	}


}
