package com.bueld.snek.classes;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Grid extends Group {

	private int mouseId = 0;
	private Rectangle style;

	private double minX;
	private double maxX;
	private double minY;
	private double maxY;

	public Grid(int size, int playerID, Rectangle style) {

		this.style = new Rectangle();
		this.style = style;

		if (size > 26 || size <= 0) {
			size = 26;
		}

		createLayout(size);

		create(size);
	}

	private void createLayout(int size) {

		// i is Letters

		for (int i = 0; i < size; i++) {

			for (int a = 0; a <= (size + 1); a += (size + 1)) {
				Rectangle r = new Rectangle(0, 0, style.getWidth(), style.getHeight());
				r.setStrokeType(style.getStrokeType());

				r.setStroke(Color.RED);
				r.setFill(Color.DARKSLATEGRAY);

				r.setTranslateX((i + 1) * style.getWidth());
				r.setTranslateY(a * style.getHeight());

				setRListeners(r, 1);

				r.setId("Edge");

				Text t = new Text(Character.toString((char) ((i) + 'A')));

				t.setTranslateX(
						(i + 1) * style.getWidth() + (style.getWidth() / 2) - t.getLayoutBounds().getWidth() / 2);
				t.setTranslateY(a * style.getHeight() + (style.getHeight() / 2) + t.getLayoutBounds().getHeight() / 4);

				if (i == 0 && a == 0) {
					minY = r.getTranslateY();
				}
				if (i == 0 && a == (size + 1)) {
					maxY = r.getTranslateY();
				}

				getChildren().addAll(r, t);
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

				setRListeners(r, 2);

				r.setId("Edge");

				Text t = new Text(new String((j + 1) + ""));

				t.setTranslateY(
						(j + 1) * style.getHeight() + (style.getHeight() / 2) + t.getLayoutBounds().getHeight() / 4);
				t.setTranslateX(a * style.getWidth() + (style.getWidth() / 2) - t.getLayoutBounds().getWidth() / 2);

				if (j == 0 && a == 0) {
					minX = r.getTranslateX();
				}
				if (j == 0 && a == (size + 1)) {
					maxX = r.getTranslateX();
				}

				getChildren().addAll(r, t);
			}

		}
	}

	private void create(int size) {

		// i is Letters
		// j is Numbers

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Rectangle r = new Rectangle(0, 0, style.getWidth(), style.getHeight());

				r.setStrokeType(style.getStrokeType());

				setRListeners(r, 0);

				r.setFill(style.getFill());
				r.setStroke(style.getStroke());
				r.setTranslateX((i + 1) * style.getWidth());
				r.setTranslateY((j + 1) * style.getHeight());

				r.setId("None");

				getChildren().add(r);
			}
		}

	}

	private void setRListeners(Rectangle r, int id) {
		r.setOnMouseEntered(e -> {
			Color c = (Color) r.getStroke();
			r.setStroke(c.darker());
			r.setStrokeWidth(r.getStrokeWidth() + 1);

			highlightHov(r, id);
		});

		r.setOnMouseExited(e -> {
			Color c = (Color) r.getStroke();
			r.setStroke(c.brighter());
			r.setStrokeWidth(r.getStrokeWidth() - 1);

			reHighlightHov(r, id);
		});

		r.setOnMousePressed(e -> {
			if (id == 0) {
				switch (mouseId) {

				case 0:
					Color c = (Color) r.getFill();
					r.setFill(c.darker());

					highlightPress(r, id);
					break;
				case 1:
					addObject(r);
					break;
				case 2:
					removeObject(r);
					break;
				case 3:
					attack(r);
					break;
				}
			} else {
				Color c = (Color) r.getFill();
				r.setFill(c.darker());

				highlightPress(r, id);
			}

		});

		r.setOnMouseReleased(e -> {

			if (id == 0) {
				switch (mouseId) {

				case 0:
					Color c = (Color) r.getFill();
					r.setFill(c.brighter());

					reHighlightPress(r, id);
					break;
				case 1:
					addObject(r);
					break;
				case 2:
					removeObject(r);
					break;

				}
			} else {
				Color c = (Color) r.getFill();
				r.setFill(c.brighter());

				reHighlightPress(r, id);
			}
		});

	}

	private void highlightPress(Rectangle r, int id) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX() && (id == 0 || id == 1)) {
					Color c = (Color) ((Rectangle) buff).getFill();
					((Rectangle) buff).setFill(c.darker());
				} else if (((Rectangle) buff).getTranslateY() == r.getTranslateY() && (id == 0 || id == 2)) {
					Color c = (Color) ((Rectangle) buff).getFill();
					((Rectangle) buff).setFill(c.darker());
				}
			}
		}
	}

	private void reHighlightPress(Rectangle r, int id) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX() && (id == 0 || id == 1)) {
					Color c = (Color) ((Rectangle) buff).getFill();
					((Rectangle) buff).setFill(c.brighter());
				} else if (((Rectangle) buff).getTranslateY() == r.getTranslateY() && (id == 0 || id == 2)) {
					Color c = (Color) ((Rectangle) buff).getFill();
					((Rectangle) buff).setFill(c.brighter());
				}
			}
		}
	}

	private void highlightHov(Rectangle r, int id) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX() && (id == 0 || id == 1)) {
					Color c = (Color) ((Rectangle) buff).getStroke();
					((Rectangle) buff).setStroke(c.darker().darker());
				} else if (((Rectangle) buff).getTranslateY() == r.getTranslateY() && (id == 0 || id == 2)) {
					Color c = (Color) ((Rectangle) buff).getStroke();
					((Rectangle) buff).setStroke(c.darker().darker());
				}
			}
		}
	}

	private void reHighlightHov(Rectangle r, int id) {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() == r.getTranslateX() && (id == 0 || id == 1)) {
					Color c = (Color) ((Rectangle) buff).getStroke();
					((Rectangle) buff).setStroke(c.brighter().brighter());
				} else if (((Rectangle) buff).getTranslateY() == r.getTranslateY() && (id == 0 || id == 2)) {
					Color c = (Color) ((Rectangle) buff).getStroke();
					((Rectangle) buff).setStroke(c.brighter().brighter());
				}
			}
		}
	}

	private void addObject(Rectangle r) {
		r.setFill(Color.DARKVIOLET);
		r.setId("Ship");
	}

	private void removeObject(Rectangle r) {
		r.setFill(style.getFill());
		r.setStroke(style.getStroke());
		r.setStrokeWidth(style.getStrokeWidth());
		r.setId("None");
	}

	private void attack(Rectangle r) {
		if (r.getId().equals("Ship")) {
			r.setId("Hit");
			r.setStrokeWidth(5);
			r.setStroke(Color.DARKGOLDENROD);
		} else if (r.getId().equals("None")) {
			r.setId("Miss");
			r.setStrokeWidth(5);
			r.setStroke(Color.STEELBLUE);
		}
	}

	public int getMouseId() {
		return mouseId;
	}

	public void setMouseId(int mouseId) {
		this.mouseId = mouseId;
	}

	public void clearAll() {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (((Rectangle) buff).getTranslateX() != minX && ((Rectangle) buff).getTranslateX() != maxX
						&& ((Rectangle) buff).getTranslateY() != maxY && ((Rectangle) buff).getTranslateY() != minY) {
					removeObject((Rectangle) buff);
				}
			}
		}
	}

	public void takeoverIDs(String str, Bounds b) {

		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				if (!buff.getId().equals("Edge") && buff.getBoundsInParent().equals(b)) {
					buff.setId(str);
				}
			}
		}
	}

	public void changeToID() {
		for (Node buff : this.getChildren()) {
			if (buff instanceof Rectangle) {
				switch (buff.getId()) {
				/*
				 * case "Ship": ((Rectangle) buff).setFill(Color.DARKVIOLET); break;
				 */
				case "Hit":
					((Rectangle) buff).setStrokeWidth(5);
					((Rectangle) buff).setStroke(Color.DARKGOLDENROD);
					break;
				case "Miss":
					((Rectangle) buff).setStrokeWidth(5);
					((Rectangle) buff).setStroke(Color.STEELBLUE);
					break;
				case "None":
					((Rectangle) buff).setStroke(style.getStroke());
					((Rectangle) buff).setFill(style.getFill());
					((Rectangle) buff).setStrokeWidth(style.getStrokeWidth());
					break;
				}
			}
		}
	}

}
