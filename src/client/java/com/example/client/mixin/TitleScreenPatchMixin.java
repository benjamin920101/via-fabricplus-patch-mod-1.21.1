package com.example.client.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenPatchMixin {
    /**
     * This patches the title screen initialization to log a message.
     * It demonstrates client-side dynamic class modification.
     */
    @Inject(at = @At("HEAD"), method = "init")
    private void onInit(CallbackInfo info) {
        if (com.example.ExampleMod.PATCH_ENABLED) {
            com.example.ExampleMod.LOGGER.info("The Title Screen has been successfully patched by ExampleMod!");
        }
    }
}
