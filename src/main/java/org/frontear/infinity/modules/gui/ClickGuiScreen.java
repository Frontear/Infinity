package org.frontear.infinity.modules.gui;

import com.google.common.collect.Queues;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.GuiScreen;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.modules.ui.Button;
import org.frontear.infinity.modules.ui.Panel;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Deque;

@FieldDefaults(level = AccessLevel.PRIVATE) public final class ClickGuiScreen extends GuiScreen {
	Deque<Panel> categoryPanels = Queues.newArrayDeque();
	boolean init = false; // prevents initGui from being called more than once

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		//this.drawDefaultBackground();
		categoryPanels.forEach(Panel::draw);
	}

	@Override protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		categoryPanels.forEach(x -> x.mouse(mouseX, mouseY, mouseButton));
	}

	@Override public void initGui() {
		if (!init) {
			val width = 100;
			val height = 30;
			var x = 5;
			var y = 5;

			Category[] categories = Arrays.stream(Category.values()).filter(z -> z != Category.NONE)
					.toArray(Category[]::new);
			for (Category category : categories) {
				final Panel panel = new Panel(x, y, width, height, new Color(255, 170, 0).darker());

				Module[] categoryModules = Infinity.inst().getModules().getObjects()
						.filter(z -> z.getCategory() == category).toArray(Module[]::new);
				for (Module module : categoryModules) {
					panel.add(new Button(module.getName(), 0, 0, 0, 0, null) {
						@Override public void draw() {
							if (module.isActive()) {
								setColor(new Color(255, 170, 0).darker().darker());
							}
							else {
								setColor(new Color(255, 170, 0).darker());
							}

							super.draw();
						}

						@Override protected void click(int mouseX, int mouseY, boolean hover, int button) {
							if (hover) {
								if (button == 0) {
									module.toggle();
								}
								else if (button == 1) {
									mc.displayGuiScreen(new KeyBindScreen(module, ClickGuiScreen.this));
								}
							}
						}
					});
				}

				x += width + 5 + 2;

				categoryPanels.add(panel);
			}

			init = true;
		}
	}

	@Override public boolean doesGuiPauseGame() {
		return false;
	}
}