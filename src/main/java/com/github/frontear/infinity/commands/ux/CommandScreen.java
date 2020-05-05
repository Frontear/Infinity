package com.github.frontear.infinity.commands.ux;

import com.github.frontear.infinity.commands.CommandContainer;
import java.util.function.IntSupplier;
import lombok.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

public final class CommandScreen extends Screen {
    public final ConsoleHUD hud;
    private final CommandContainer container;
    private final ConsoleField field;

    public CommandScreen(@NonNull final MinecraftClient client,
        @NonNull final CommandContainer container) {
        super(new LiteralText("CommandScreen"));

        val width = 320;
        final IntSupplier x = () -> client.getWindow().getScaledWidth() - width
            - 2; // mc width changes, this will get the best value each time
        val height = 180;
        val y = 2;

        this.hud = new ConsoleHUD(client, x, y, width, height);
        this.field = new ConsoleField(client, x, y + height, width, "");

        this.container = container;
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float delta) {
        this.setFocused(field);
        field.setSelected(true);

        hud.render(mouseX, mouseY, delta);
        field.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(final double mouseX, final double mouseY, final double amount) {
        return hud.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean keyPressed(final int keyCode, final int scanCode, final int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            val words = field.clear().split(" ");
            val optional = container.stream()
                .filter(x -> x.getPropertyName().toLowerCase().equals(words[0]))
                .findFirst();

            if (optional.isPresent()) {
                val command = optional.get();
                val args = ArrayUtils.remove(words, 0); // remove command name

                if (args.length < command.getArgs()) {
                    hud.println("Too few args (min " + command.getArgs() + ")");
                }
                else {
                    try {
                        command.process(args);
                    }
                    catch (final Exception e) {
                        hud.println(
                            "An unknown error has occured [" + e.getClass().getSimpleName() + "]");
                        e.printStackTrace();
                    }
                }
            }
            else {
                hud.println("Unknown command, use help for a list of commands.");
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
