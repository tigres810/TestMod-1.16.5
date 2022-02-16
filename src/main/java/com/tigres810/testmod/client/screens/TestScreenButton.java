package com.tigres810.testmod.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tigres810.testmod.TestMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TestScreenButton extends Button {
	
	final ResourceLocation information_tabletgui = new ResourceLocation(TestMod.MOD_ID, "textures/gui/book1right.png");
	
	int buttonwidth = 16;
	int buttonheight = 14;
	int u = 175;
	int v = 1;
	int button;

	public TestScreenButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction,
			ITooltip onTooltip) {
		super(x, y, width, height, title, pressedAction, onTooltip);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if(visible) {
			Minecraft.getInstance().getTextureManager().bind(information_tabletgui);
			if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
				isHovered = true;
			} else {
				isHovered = false;
			}
			if (isHovered) {
				v = 18;
			} else {
				v = 1;
			}
			if(button == 2) {
				this.blit(matrixStack, x, y, 192, v, buttonwidth, buttonheight);
			} else {
				this.blit(matrixStack, x, y, u, v, buttonwidth, buttonheight);
			}
		}
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

}
