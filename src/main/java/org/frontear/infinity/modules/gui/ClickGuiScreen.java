package org.frontear.infinity.modules.gui;

import com.google.common.collect.Queues;
import net.minecraft.client.gui.GuiScreen;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.ui.Button;
import org.frontear.infinity.ui.Panel;

import java.awt.*;
import java.util.Arrays;
import java.util.Deque;

public class ClickGuiScreen extends GuiScreen {
	private Deque<Panel> categoryPanels = Queues.newArrayDeque();
	private boolean init = false; // prevents initGui from being called more than once

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		//this.drawDefaultBackground();
		categoryPanels.forEach(Panel::draw);
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
					panel.add(new Button(module.getName(), 0, 0, 0, 0, null)); // values set internally
				}

				x += width + 5 + 2;

				categoryPanels.add(panel);
			}

			init = true;
		}
	}
}
/*
	private Panel panel;
	private Panel panel2;

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		panel.draw();
		panel2.draw();
	}

	@Override public void initGui() {
		this.panel = new Panel(5, 5, 100, 40, new Color(255, 170, 0).darker().darker()); // dark-gold color
		this.panel2 = new Panel(5 + 100 + 10, 5, 100, 40, Color.LIGHT_GRAY);
		for (int i = 0; i < 4; i++) {
			panel.add(new Button("Test " + i, 0, 0, 0, 0, null));
			panel2.add(new Button("Test2 " + i, 0, 0, 0, 0, null));
		}
	}
*/