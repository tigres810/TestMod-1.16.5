package com.tigres810.testmod.client.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.tigres810.testmod.common.tileentitys.TileFluidDispenserBlock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.*;

public class RenderFluidDispenserBlock extends TileEntityRenderer<TileFluidDispenserBlock>
{
    private static final Vector3d LOCAL_POS = new Vector3d(0.5f, 0.35f, 0.5f);
    private static final float LINE_WIDTH = .02F;
    private static final int LINE_RED = 32;
    private static final int LINE_GREEN = 61;
    private static final int LINE_BLUE = 100;
    private static final int LINE_ALPHA = 255;
    private static final int LIGHT_FULLBRIGHT = 0xF000F0;

    public RenderFluidDispenserBlock(TileEntityRendererDispatcher dispatcher) { super(dispatcher); }

    public void render(TileFluidDispenserBlock te, float partialTicks, MatrixStack mstack, IRenderTypeBuffer buffer, int light, int overlay)
    {
        if(te.isconnected) {
        	BlockPos difference = te.getConnectedTo().subtract(te.getBlockPos());
            Vector3d otherPos = LOCAL_POS.add(difference.getX(), difference.getY(), difference.getZ());
        	drawLineBetween(buffer, mstack, LOCAL_POS, otherPos, LINE_WIDTH, LINE_RED, LINE_GREEN, LINE_BLUE, LINE_ALPHA);
        }



        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = new ItemStack(Items.BLUE_STAINED_GLASS);
        IBakedModel model = itemRenderer.getModel(stack, te.getLevel(), null);

        //Local item
        mstack.pushPose();
        mstack.translate(LOCAL_POS.x, LOCAL_POS.y, LOCAL_POS.z);
        mstack.scale(.2F, .2F, .2F);
        itemRenderer.render(stack, ItemCameraTransforms.TransformType.FIXED, true, mstack, buffer, light, overlay, model);
        mstack.popPose();
    }

    private static void drawLineBetween(IRenderTypeBuffer buffer, MatrixStack mstack, Vector3d local, Vector3d target, float lineWidth, int r, int g, int b, int a)
    {
        IVertexBuilder builder = buffer.getBuffer(RenderType.leash());

        //Calculate yaw
        float rotY = (float) MathHelper.atan2(target.x - local.x, target.z - local.z);

        //Calculate pitch
        double distX = target.x - local.x;
        double distZ = target.z - local.z;
        float rotX = (float) MathHelper.atan2(target.y - local.y, MathHelper.sqrt(distX * distX + distZ * distZ));

        mstack.pushPose();

        //Translate to start point
        mstack.translate(local.x, local.y, local.z);
        //Rotate to point towards end point
        mstack.mulPose(Vector3f.YP.rotation(rotY));
        mstack.mulPose(Vector3f.XN.rotation(rotX));

        //Calculate distance between points -> length of the line
        float distance = (float) local.distanceTo(target);

        Matrix4f matrix = mstack.last().pose();
        float halfWidth = lineWidth / 2F;

        //Draw horizontal quad
        builder.vertex(matrix, -halfWidth, 0,        0).color(r, g, b, a).uv2(LIGHT_FULLBRIGHT).endVertex();
        builder.vertex(matrix,  halfWidth, 0,        0).color(r, g, b, a).uv2(LIGHT_FULLBRIGHT).endVertex();
        builder.vertex(matrix,  halfWidth, 0, distance).color(r, g, b, a).uv2(LIGHT_FULLBRIGHT).endVertex();
        builder.vertex(matrix, -halfWidth, 0, distance).color(r, g, b, a).uv2(LIGHT_FULLBRIGHT).endVertex();

        //Draw vertical Quad
        builder.vertex(matrix, 0, -halfWidth,        0).color(r, g, b, a).uv2(LIGHT_FULLBRIGHT).endVertex();
        builder.vertex(matrix, 0,  halfWidth,        0).color(r, g, b, a).uv2(LIGHT_FULLBRIGHT).endVertex();
        builder.vertex(matrix, 0,  halfWidth, distance).color(r, g, b, a).uv2(LIGHT_FULLBRIGHT).endVertex();
        builder.vertex(matrix, 0, -halfWidth, distance).color(r, g, b, a).uv2(LIGHT_FULLBRIGHT).endVertex();

        mstack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(TileFluidDispenserBlock te) { return true; }
    
    
}