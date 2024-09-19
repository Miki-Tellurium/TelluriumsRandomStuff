package com.mikitellurium.telluriumsrandomstuff.mixin;

import com.mikitellurium.telluriumsrandomstuff.test.KeyBindings;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.mikitellurium.telluriumsrandomstuff.test.KeyEvents.*;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeScreenMixin {

    @Inject(method = "keyPressed", at = @At(value = "HEAD"))
    private void keyEvent(int pKeyCode, int pScanCode, int pModifiers, CallbackInfoReturnable<Boolean> cir) {
        if (KeyBindings.TRANSLATE_X_UP_KEY.matches(pKeyCode, pScanCode)) {
            translateX += increase;
        } else if (KeyBindings.TRANSLATE_X_DOWN_KEY.matches(pKeyCode, pScanCode)) {
            translateX -= increase;
        } else if (KeyBindings.TRANSLATE_Y_UP_KEY.matches(pKeyCode, pScanCode)) {
            translateY += increase;
        } else if (KeyBindings.TRANSLATE_Y_DOWN_KEY.matches(pKeyCode, pScanCode)) {
            translateY -= increase;
        } else if (KeyBindings.TRANSLATE_Z_UP_KEY.matches(pKeyCode, pScanCode)) {
            translateZ += increase;
        } else if (KeyBindings.TRANSLATE_Z_DOWN_KEY.matches(pKeyCode, pScanCode)) {
            translateZ -= increase;
        } else if (KeyBindings.SCALE_UP_KEY.matches(pKeyCode, pScanCode)) {
            scale += increaseScale;
        } else if (KeyBindings.SCALE_DOWN_KEY.matches(pKeyCode, pScanCode)) {
            scale -= increaseScale;
        } else if (KeyBindings.RESET_KEY.matches(pKeyCode, pScanCode)) {
            scale = initScale;
            translateX = initX;
            translateY = initY;
            translateZ = initZ;
        }
    }

}
