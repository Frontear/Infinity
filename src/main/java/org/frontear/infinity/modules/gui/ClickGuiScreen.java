package org.frontear.infinity.modules.gui;

import com.google.common.collect.Queues;
import net.minecraft.client.gui.GuiScreen;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.ui.Button;
import org.frontear.infinity.ui.Panel;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Deque;

public final class ClickGuiScreen extends GuiScreen {
	private Deque<Panel> categoryPanels = Queues.newArrayDeque();
	private boolean init = false; // prevents initGui from being called more than once

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		//this.drawDefaultBackground();
		categoryPanels.forEach(Panel::draw);
	}

	@Override protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		categoryPanels.forEach(x -> x.mouse(mouseX, mouseY, mouseButton));
	}

	@Override public void initGui() {
		if (!init) {
			final int width = 100, height = 30;
			int x = 5, y = 5;

			Category[] categories = Arrays.stream(Category.values()).filter(z -> z != Category.NONE)
					.toArray(Category[]::new);
			for (Category category : categories) {
				final Panel panel = new Panel(x, y, width, height, new Color(255, 170, 0).darker());

				Module[] categoryModules = Infinity.inst().getModules().getObjects()
						.filter(z -> z.getCategory() == category).toArray(Module[]::new);
				for (Module module : categoryModules) {
					panel.add(new Button(module.getName(), 0, 0, 0, 0, null, z -> {
						module.toggle();
					}) {
						@Override public void draw(float scale) {
							if (module.isActive()) {
								setColor(new Color(255, 170, 0).darker().darker());
							}
							else {
								setColor(new Color(255, 170, 0).darker());
							}
							super.draw(scale);
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