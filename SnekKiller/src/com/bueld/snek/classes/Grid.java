package com.bueld.snek.classes;

import javafx.scene.shape.Rectangle;

public class Grid {
	
	private Rectangle[][] grid;

	public Grid(int size, int playerID, Rectangle style) {
		grid = new Rectangle[size][size];
		
		create(size, style);
	}
	
	public void create(int size, Rectangle style) {
		
		// i is Letters
		// j is Numbers
		
		for (int i = 0;i<size;i++) {
			for (int j = 0;j<size;j++) {
				grid[i][j] = style;
				grid[i][j].setTranslateX(i*style.getWidth());
				grid[i][j].setTranslateY(j*style.getHeight());
			}
		}
	}
	
	public void addObjects() {
		
	}
}
