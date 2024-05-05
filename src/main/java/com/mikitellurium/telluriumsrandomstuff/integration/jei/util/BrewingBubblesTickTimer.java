package com.mikitellurium.telluriumsrandomstuff.integration.jei.util;

import com.mikitellurium.telluriumsrandomstuff.integration.util.TickTimer;
import com.mikitellurium.telluriumsrandomstuff.lib.ITimer;
import mezz.jei.api.gui.ITickTimer;

public class BrewingBubblesTickTimer implements ITickTimer {

    private static final int[] BUBBLE_LENGTHS = new int[]{29, 23, 18, 13, 9, 5, 0};
    private final ITimer internalTimer;

    public BrewingBubblesTickTimer() {
        this.internalTimer = new TickTimer(14, BUBBLE_LENGTHS.length - 1, false);
    }

    @Override
    public int getValue() {
        int timerValue = this.internalTimer.getValue();
        return BUBBLE_LENGTHS[timerValue];
    }

    @Override
    public int getMaxValue() {
        return BUBBLE_LENGTHS[0];
    }

}
