package com.bueld.snek.classes;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
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
				Rectangle r = new Rectangle(0, 0, style.getWidth(), style.getHeight());
				
				r.setStrokeType(style.getStrokeType());
				
				setRListeners(r);

				r.setFill(style.getFill());
				r.setStroke(style.getStroke());
				r.setTranslateX(i * style.getWidth());
				r.setTranslateY(j * style.getHeight());

				getChildren().add(r);
			}
		}

	}

	public void setRListeners(Rectangle r) {
		r.setOnMouseEntered(e -> {
			Color c = (Color) r.getStroke();
			r.setStroke(c.darker());
			r.setStrokeWidth(r.getStrokeWidth()+1);
			
			highlightHov(r);
		});

		r.setOnMouseExited(e -> {
			Color c = (Color) r.getStroke();
			r.setStroke(c.brighter());
			r.setStrokeWidth(r.getStrokeWidth()-1);
			
			reHighlightHov(r);
		});
		
		r.setOnMousePressed(e -> {
			Color c = (Color) r.getFill();
			r.setFill(c.darker());
			
			highlightPress(r);
		});
		
		r.setOnMouseReleased(e -> {
			Color c = (Color) r.getFill();
			r.setFill(c.brighter());
			
			reHighlightPress(r);
		});
	}
	
	public void highlightPress(Rectangle r) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX()) {
					Color c = (Color) ((Rectangle)buff).getFill();
					((Rectangle)buff).setFill(c.darker());
				}
				else if (((Rectangle) buff).getTranslateY() == r.getTranslateY()) {
					Color c = (Color) ((Rectangle)buff).getFill();
					((Rectangle)buff).setFill(c.darker());
				}
			}
		}
	}
	
	public void reHighlightPress(Rectangle r) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX()) {
					Color c = (Color) ((Rectangle)buff).getFill();
					((Rectangle)buff).setFill(c.brighter());
				}
				else if (((Rectangle) buff).getTranslateY() == r.getTranslateY()) {
					Color c = (Color) ((Rectangle)buff).getFill();
					((Rectangle)buff).setFill(c.brighter());
				}
			}
		}
	}
	
	public void highlightHov(Rectangle r) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX()) {
					Color c = (Color) ((Rectangle)buff).getStroke();
					((Rectangle)buff).setStroke(c.darker().darker());
				}
				else if (((Rectangle) buff).getTranslateY() == r.getTranslateY()) {
					Color c = (Color) ((Rectangle)buff).getStroke();
					((Rectangle)buff).setStroke(c.darker().darker());
				}
			}
		}
	}
	
	public void reHighlightHov(Rectangle r) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX()) {
					Color c = (Color) ((Rectangle)buff).getStroke();
					((Rectangle)buff).setStroke(c.brighter().brighter());
				}
				else if (((Rectangle) buff).getTranslateY() == r.getTranslateY()) {
					Color c = (Color) ((Rectangle)buff).getStroke();
					((Rectangle)buff).setStroke(c.brighter().brighter());
				}
			}
		}
	}

	public void addObjects() {

	}

}
