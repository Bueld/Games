package com.bueld.snek.classes;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Grid extends Group {

	public Grid(int size, int playerID, Rectangle style) {

		create(size, style);
	}

	public void create(int size, Rectangle style) {

		// i is Letters
		// j is Numbers

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Rectangle r = style;
				r.setTranslateX(i * style.getWidth());
				r.setTranslateY(j * style.getHeight());

				this.getChildren().add(r);
			}
		}

	}

	public void addObjects() {

	}
}
