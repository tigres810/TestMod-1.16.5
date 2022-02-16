package com.tigres810.testmod.core.interfaces;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public interface ICauldronRecipes {

	ItemStack[][][] recipes = new ItemStack[][][] {
		{{new ItemStack(Items.IRON_INGOT), new ItemStack(Items.CHARCOAL)}, {new ItemStack(Items.COAL_BLOCK), new ItemStack(Items.COAL)}}
	};
	
	public default ItemStack[][][] getRecipeList() {
		return recipes;
	}
	
	default List<Item> returnNewSelectedRecipe(ItemStack[][] selectedrecipe) {
		List<Item> newlItemStacks = new ArrayList<Item>();
		for(int i = 0; i < selectedrecipe[0].length; i++) {
			newlItemStacks.add(selectedrecipe[0][i].getItem());
		}
		return newlItemStacks;
	}
	
	default List<Item> returnNewRecipeList(List<ItemStack> recipe){
		List<Item> newItems = new ArrayList<Item>();
		for(int r = 0; r < recipe.size(); r++) {
			newItems.add(recipe.get(r).getItem());
		}
		return newItems;
	}
	
	public default ItemStack[][] checkRecipe(List<ItemStack> recipe){
		if(recipe != null) {
			for(int e = 0; e < recipes.length; e++) {
				for(int i = 0; i < recipes[e][0].length; i++) {
					for(int r = 0; r < recipe.size(); r++) {
						if(recipe.get(r).getItem() == recipes[e][0][i].getItem()) {
							return recipes[e];
						}
					}
				}
			}
		}
		return null;
	}
	
	public default boolean matchingRecipe(ItemStack[][] selectedrecipe, List<ItemStack> recipe) {
		if(selectedrecipe != null) {
			List<Item> newlItemStacks = returnNewSelectedRecipe(selectedrecipe);
			List<Item> newItems = returnNewRecipeList(recipe);
			return newItems.containsAll(newlItemStacks);
		} else {
			return false;
		}
	}
}