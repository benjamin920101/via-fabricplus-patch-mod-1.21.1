package com.example.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityPatchMixin {
    /**
     * This is a dynamic patch that modifies the damage taken by any living entity.
     * It demonstrates how Mixins can "patch" existing class behavior at runtime.
     */
    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float applyDamagePatch(float amount, DamageSource source) {
        if (com.example.ExampleMod.PATCH_ENABLED) {
            // Increase damage by 20% as a demonstration of a behavioral patch
            return amount * 1.2f;
        }
        return amount;
    }
}
