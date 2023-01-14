package com.dicedpixels.endermanholdables.mixin;

import com.dicedpixels.endermanholdables.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndermanEntity.PickUpBlockGoal.class)
abstract class MixinEndermanEntityPickUpBlockGoal {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean onBlockStateIsIn(BlockState state, TagKey<Block> blockTagKey) {
        return ConfigHandler.getValue(state.getBlock());
    }
}
