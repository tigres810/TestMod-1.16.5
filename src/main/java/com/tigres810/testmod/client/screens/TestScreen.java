package com.tigres810.testmod.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tigres810.testmod.TestMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TestScreen extends Screen {
	
	private static final int WIDTH = 179;
    private static final int HEIGHT = 151;

    private ResourceLocation GUI = new ResourceLocation(TestMod.MOD_ID, "textures/gui/book1big_gui.png");

    private int currentpage = 0;
    private static final int maxpages = 2;
    
    private Button closeButton;
    private Button arrowleftButton;
    private Button arrowrightButton;
    private String currentpagetext;
    public TestScreen() {
        super(new TranslationTextComponent("test"));
    }

    @Override
    protected void init() {
    	this.buttons.clear();
		this.children.clear();
    	int centerY = (this.height - HEIGHT) / 2;
    	closeButton = new Button((this.width / 2+90) - this.font.width("Prueba") / 2, centerY + 12, 15, 20, new StringTextComponent("X"), button -> close());
    	arrowleftButton = new ChangePageButton((this.width / 2-60) - this.font.width("Prueba") / 2, centerY + HEIGHT - 30, false, button -> changepage(1), true);
    	arrowrightButton = new ChangePageButton((this.width / 2+75) - this.font.width("Prueba") / 2, centerY + HEIGHT - 30, true, button -> changepage(2), true);
    	this.insertText(currentpagetext, false);
    	this.addButton(closeButton);
    	this.addButton(arrowleftButton);
    	this.addButton(arrowrightButton);
    	arrowleftButton.visible = false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
    private void changepage(int buttonid) {
    	if(currentpage > 0) {
    		if(currentpage < maxpages) {
    			if(currentpage == 1) {
    				if(buttonid == 1) {
    					arrowrightButton.visible = true;
    					arrowleftButton.visible = false;
    					currentpage--;
    				} else {
    					if(currentpage + 1 == maxpages) {
    						arrowleftButton.visible = true;
    						arrowrightButton.visible = false;
    						currentpage++;
    					} else {
    						arrowleftButton.visible = true;
    						arrowrightButton.visible = true;
    						currentpage++;
    					}
    				}
    			}
    		} else {
    			if(buttonid == 1) {
    				if(currentpage - 1 == 0) {
	    				arrowleftButton.visible = false;
	    				arrowrightButton.visible = true;
	    				currentpage--;
    				} else {
    					arrowleftButton.visible = true;
	    				arrowrightButton.visible = true;
	    				currentpage--;
    				}
    			}
    		}
    	} else {
			if(buttonid == 2) {
				arrowleftButton.visible = true;
				arrowrightButton.visible = true;
				currentpage++;
			}
    	}
    	
    	currentpagetext = "" + currentpage;
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
        this.font.draw(matrixStack, I18n.get("gui.title"), ((width / 2) - this.font.width("test") / 2), relY + 10, 0x000000);
        this.font.draw(matrixStack, currentpage + "/" + maxpages, ((width / 2+10) - this.font.width("Prueba") / 2), relY + HEIGHT - 16, 0x000000);
        if(currentpage == 0) {
        	this.font.draw(matrixStack, I18n.get("gui.leftpage0"), ((width / 2) - this.font.width("Prueba") / 2) - 60, relY + 12, 0x000000);
        	this.font.draw(matrixStack, I18n.get("gui.leftpage0.1"), ((width / 2) - this.font.width("Prueba") / 2) - 60, relY + 21, 0x000000);
        	this.font.draw(matrixStack, I18n.get("gui.leftpage0.2"), ((width / 2) - this.font.width("Prueba") / 2) - 60, relY + 30, 0x000000);
        	this.font.draw(matrixStack, I18n.get("gui.leftpage0.3"), ((width / 2) - this.font.width("Prueba") / 2) - 60, relY + 39, 0x000000);
        }
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }


    public static void open() {
        Minecraft.getInstance().setScreen(new TestScreen());
    }

}
