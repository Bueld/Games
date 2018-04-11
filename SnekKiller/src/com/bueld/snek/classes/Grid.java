package com.bueld.snek.classes;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Grid extends Group {

	public Grid(int size, int playerID, Rectangle style) {

		if (size > 26 || size <= 0) {
			size = 26;
		}

		createLayout(size, style);

		create(size, style);
	}

	public void createLayout(int size, Rectangle style) {

		// i is Letters

		for (int i = 0; i < size; i++) {

			for (int a = 0; a <= (size + 1); a += (size + 1)) {
				Rectangle r = new Rectangle(0, 0, style.getWidth(), style.getHeight());
				r.setStrokeType(style.getStrokeType());

				r.setStroke(Color.RED);
				r.setFill(Color.DARKSLATEGRAY);

				r.setTranslateX((i + 1) * style.getWidth());
				r.setTranslateY(a * style.getHeight());
				
				setRListeners(r,1);

				Text t = new Text(Character.toString((char)((i) + 'A')));

				t.setTranslateX(
						(i + 1) * style.getWidth() + (style.getWidth() / 2) - t.getLayoutBounds().getWidth() / 2);
				t.setTranslateY(a * style.getHeight() + (style.getHeight() / 2) + t.getLayoutBounds().getHeight() / 4);

				getChildren().addAll(r,t);
			}
		}

		// j is Numbers

		for (int j = 0; j < size; j++) {
			for (int a = 0; a <= (size + 1); a += (size + 1)) {
				Rectangle r = new Rectangle(0, 0, style.getWidth(), style.getHeight());
				r.setStrokeType(style.getStrokeType());

				r.setStroke(Color.RED);
				r.setFill(Color.DARKSLATEGRAY);

				r.setTranslateY((j + 1) * style.getHeight());
				r.setTranslateX(a * style.getWidth());
				
				setRListeners(r,2);

				Text t = new Text(new String((j + 1) + ""));

				t.setTranslateY(
						(j + 1) * style.getHeight() + (style.getHeight() / 2) + t.getLayoutBounds().getHeight() / 4);
				t.setTranslateX(a * style.getWidth() + (style.getWidth() / 2) - t.getLayoutBounds().getWidth() / 2);

				getChildren().addAll(r, t);
			}

		}
	}

	public void create(int size, Rectangle style) {

		// i is Letters
		// j is Numbers

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Rectangle r = new Rectangle(0, 0, style.getWidth(), style.getHeight());

				r.setStrokeType(style.getStrokeType());

				setRListeners(r,0);

				r.setFill(style.getFill());
				r.setStroke(style.getStroke());
				r.setTranslateX((i + 1) * style.getWidth());
				r.setTranslateY((j + 1) * style.getHeight());

				getChildren().add(r);
			}
		}

	}

	public void setRListeners(Rectangle r, int id) {
		r.setOnMouseEntered(e -> {
			Color c = (Color) r.getStroke();
			r.setStroke(c.darker());
			r.setStrokeWidth(r.getStrokeWidth() + 1);

			highlightHov(r,id);
		});

		r.setOnMouseExited(e -> {
			Color c = (Color) r.getStroke();
			r.setStroke(c.brighter());
			r.setStrokeWidth(r.getStrokeWidth() - 1);

			reHighlightHov(r,id);
		});

		r.setOnMousePressed(e -> {
			Color c = (Color) r.getFill();
			r.setFill(c.darker());

			highlightPress(r,id);
		});

		r.setOnMouseReleased(e -> {
			Color c = (Color) r.getFill();
			r.setFill(c.brighter());

			reHighlightPress(r,id);
		});
	}

	public void highlightPress(Rectangle r, int id) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX()&&( id == 0 || id == 1)) {
					Color c = (Color) ((Rectangle) buff).getFill();
					((Rectangle) buff).setFill(c.darker());
				} else if (((Rectangle) buff).getTranslateY() == r.getTranslateY()&&( id == 0 || id == 2)) {
					Color c = (Color) ((Rectangle) buff).getFill();
					((Rectangle) buff).setFill(c.darker());
				}
			}
		}
	}

	public void reHighlightPress(Rectangle r, int id) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX() &&( id == 0 || id == 1)) {
					Color c = (Color) ((Rectangle) buff).getFill();
					((Rectangle) buff).setFill(c.brighter());
				} else if (((Rectangle) buff).getTranslateY() == r.getTranslateY()&&( id == 0 || id == 2)) {
					Color c = (Color) ((Rectangle) buff).getFill();
					((Rectangle) buff).setFill(c.brighter());
				}
			}
		}
	}

	public void highlightHov(Rectangle r, int id) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX() &&( id == 0 || id == 1)) {
					Color c = (Color) ((Rectangle) buff).getStroke();
					((Rectangle) buff).setStroke(c.darker().darker());
				} else if (((Rectangle) buff).getTranslateY() == r.getTranslateY()&&( id == 0 || id == 2)) {
					Color c = (Color) ((Rectangle) buff).getStroke();
					((Rectangle) buff).setStroke(c.darker().darker());
				}
			}
		}
	}

	public void reHighlightHov(Rectangle r, int id) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX() &&( id == 0 || id == 1)) {
					Color c = (Color) ((Rectangle) buff).getStroke();
					((Rectangle) buff).setStroke(c.brighter().brighter());
				} else if (((Rectangle) buff).getTranslateY() == r.getTranslateY()&&( id == 0 || id == 2)) {
					Color c = (Color) ((Rectangle) buff).getStroke();
					((Rectangle) buff).setStroke(c.brighter().brighter());
				}
			}
		}
	}

	public void addObjects() {

	}

}
