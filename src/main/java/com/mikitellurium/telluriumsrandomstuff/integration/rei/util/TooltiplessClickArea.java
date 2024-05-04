package com.mikitellurium.telluriumsrandomstuff.integration.rei.util;

import com.mikitellurium.telluriumsrandomstuff.client.gui.screen.AbstractSoulFuelScreen;
import com.mikitellurium.telluriumsrandomstuff.integration.rei.category.SoulFurnaceSmeltingCategory;
import com.mikitellurium.telluriumsrandomstuff.util.MouseUtils;
import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.registry.screen.ClickArea;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TooltiplessClickArea<T extends AbstractSoulFuelScreen<?>> implements ClickArea<T> {

    @Override
    public Result handle(ClickAreaContext<T> context) {
        Rect2i area = context.getScreen().getSoulLavaStorage();
        Point mousePos = context.getMousePosition();
        boolean isAboveArea = MouseUtils.isAboveArea(mousePos.x, mousePos.y, area.getX(), area.getY(), area.getWidth(), area.getHeight());
        // Custom Result object that doesn't show tooltips
        return isAboveArea ? new ClickArea.Result() {
            private BooleanSupplier execute = () -> false;

            @Override
            public Result executor(BooleanSupplier task) {
                this.execute = task;
                return this;
            }

            @Override
            public Result category(CategoryIdentifier<?> category) {
                return this;
            }

            @Override
            public Result tooltip(Supplier<Component @Nullable []> tooltip) {
                return this;
            }

            @Override
            public boolean isSuccessful() {
                return true;
            }

            @Override
            public boolean execute() {
                return this.execute.getAsBoolean();
            }

            @Override
            public Component @Nullable [] getTooltips() {
                return new Component[]{Component.empty()};
            }

            @Override
            public Stream<CategoryIdentifier<?>> getCategories() {
                return Stream.of(SoulFurnaceSmeltingCategory.ID);
            }
        } : Result.fail();
    }

}
