package com.example.mixin;

import de.florianmichael.viafabricplus.ViaFabricPlus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ViaFabricPlus.class, remap = false)
public class ViaFabricPlusPatchMixin {
    @Inject(method = "onInitialize", at = @At("HEAD"))
    private void onInit(CallbackInfo ci) {
        System.out.println("ViaFabricPlus has been patched by ExampleMod!");
    }
}
