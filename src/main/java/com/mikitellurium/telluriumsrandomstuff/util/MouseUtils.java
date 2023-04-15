package com.mikitellurium.telluriumsrandomstuff.util;

public class MouseUtils {

    public static boolean isAboveArea(double mouseX, double mouseY, int xPos, int yPos, int sizeX, int sizeY) {
        return (mouseX >= xPos && mouseX <= xPos + sizeX) && (mouseY >= yPos && mouseY <= yPos + sizeY);
    }

}
