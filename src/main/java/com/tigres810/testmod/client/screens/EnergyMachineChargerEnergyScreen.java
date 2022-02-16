package com.tigres810.testmod.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tigres810.testmod.TestMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EnergyMachineChargerEnergyScreen extends Screen {
	
	private static final int WIDTH = 179;
    private static final int HEIGHT = 151;

    private ResourceLocation GUI = new ResourceLocation(TestMod.MOD_ID, "textures/gui/book1big_gui.png");
    
    public static int energy = 0;
    
    private Button closeButton;
    public EnergyMachineChargerEnergyScreen() {
        super(new TranslationTextComponent("test"));
    }

    @Override
    protected void init() {
    	this.buttons.clear();
		this.children.clear();
    	int centerY = (this.height - HEIGHT) / 2;
    	closeButton = new Button((this.width / 2+90) - this.font.width("Prueba") / 2, centerY + 12, 15, 20, new StringTextComponent("X"), button -> close());
    	this.addButton(closeButton);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void close() {
        minecraft.setScreen(null);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    	RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, WIDTH, HEIGHT);
        this.font.draw(matrixStack, "Energy: " + energy, ((width / 2) - this.font.width("test") / 2), relY + 10, 0x000000);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }


    public static void open(int ENERGY) {
    	energy = ENERGY;
        Minecraft.getInstance().setScreen(new EnergyMachineChargerEnergyScreen());
    }

}
