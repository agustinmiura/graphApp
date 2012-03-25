/*
 * Copyright (C) 2009 	Almada Emiliano
 * 						Miura Agustín
 * 					  	 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.edu.austral.view.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewPoint extends JPanel {

	private int x, y;

	public ViewPoint(int x, int y) {
		this.x = x;
		this.y = y;

	}
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setStroke(new BasicStroke(2));
		g2.setPaint(Color.blue);
		Shape circle = new Ellipse2D.Float(x, // superior izquierda x
				y, // superior izquierda y
				15, // ancho del cuadrado que lo circunscribe
				15); // altura de dicho cuadrado

		g2.draw(circle);
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		ViewPoint panel = new ViewPoint(1, 1);

		panel.setSize(new Dimension(300, 300));
		frame.add(panel);
		frame.setVisible(true);

	}

}
