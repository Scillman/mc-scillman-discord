package com.github.scillman.minecraft.discord.render.entity;

import com.github.scillman.minecraft.discord.ModClient;
import com.github.scillman.minecraft.discord.ModMain;
import com.github.scillman.minecraft.discord.entity.DemoEntity;
import com.github.scillman.minecraft.discord.entity.model.DemoEntityModel;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class DemoEntityRenderer extends MobEntityRenderer<DemoEntity, DemoEntityModel>
{
    public DemoEntityRenderer(EntityRendererFactory.Context context)
    {
        super(context, new DemoEntityModel(context.getPart(ModClient.MODEL_DEMO_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(DemoEntity entity)
    {
        return Identifier.of(ModMain.MOD_ID, "textures/entity/demo/demo.png");
    }
}
