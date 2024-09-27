package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.test.KeyEvents;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeScreenMixin {

    @Inject(method = "keyPressed", at = @At(value = "HEAD"))
    private void keyEvent(int pKeyCode, int pScanCode, int pModifiers, CallbackInfoReturnable<Boolean> cir) {
        KeyEvents.handleKey(pKeyCode, pScanCode);
    }

}
