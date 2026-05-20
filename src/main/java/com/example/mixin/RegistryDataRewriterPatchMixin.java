package com.example.mixin;

import com.example.logic.EnchantmentLogic1_21_11;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.rewriter.RegistryDataRewriter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.viaversion.nbt.tag.CompoundTag;
import java.util.Map;
import java.util.function.Consumer;

@Mixin(value = RegistryDataRewriter.class, remap = false)
public abstract class RegistryDataRewriterPatchMixin {

    @Shadow
    private Map<String, Consumer<CompoundTag>> enchantmentEffectHandlers;

    /**
     * Redirects updateEnchantments to our 1.21.11 logic.
     * This fixes the missing conversion of entity enchantment predicates.
     */
    @Inject(method = "updateEnchantments", at = @At("HEAD"), cancellable = true)
    private void patchUpdateEnchantments(RegistryEntry[] entries, CallbackInfo ci) {
        if (com.example.ExampleMod.PATCH_ENABLED) {
            for (RegistryEntry entry : entries) {
                if (entry.tag() instanceof CompoundTag) {
                    EnchantmentLogic1_21_11.updateEnchantment((CompoundTag) entry.tag(), enchantmentEffectHandlers);
                }
            }
            ci.cancel(); // Prevent the original (incomplete) logic from running
        }
    }
}
