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
package ar.edu.austral.view.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ar.edu.austral.model.interfaces.IGraph;
import ar.edu.austral.model.utils.enums.GraphType;
import ar.edu.austral.view.elements.GraficalEdge;
import ar.edu.austral.view.elements.GraficalVertex;
import ar.edu.austral.view.test.EdgeLine;

public class GraphView2 extends JPanel implements GraphObserver {

	public static final String VERTEX_QTY_ERROR = "All the vertex have beed added";
	public static final String GRAFICAL_EDGE_ERROR = "Grafical edge doesnt exist";

	private PanelState panelState;
	private int vertexToPut;
	private int vertexQtyToPut;
	private Color defaultColor;
	private Color specialColor;
	private Color fontColor;
	private GraficalVertex[] vertexArray;
	private Font defaultFont;

	private List<GraficalEdge> graficalListEdge;
	private Color vertexColor;
	private Font vertexFont;

	public static void main(String[] arfs) {

		JFrame jFrame = new JFrame("GraphView test");

		GraphView2 graphView2 = new GraphView2();
		graphView2.setPanelState(PanelState.TEST_GRAFICAL_EDGE);

		graphView2.setVertexQtyToPut(10);
		jFrame.add(graphView2);
		jFrame.setVisible(true);

	}

	public GraphView2() {
		super.setBackground(Color.WHITE);
		panelState = PanelState.NONE;
		vertexToPut = 0;
		vertexQtyToPut = 0;
		addMouseListener(new MyMouseListener(this));
		defaultColor = Color.BLACK;
		specialColor = Color.RED;
		vertexColor = Color.BLUE;
		vertexFont = new Font("Arial", Font.BOLD, 20);
		fontColor = Color.GREEN;
		defaultFont = new Font("Arial", 0, 10);
		graficalListEdge = new ArrayList(20);

	}

	public void redrawAllVertex() {

		for (int i = 0; i < vertexArray.length; i++) {
			if (vertexArray[i] == null) {
				continue;
			}
			vertexArray[i].draw((Graphics2D) getGraphics(), vertexColor);

		}

	}

	public void addGraficalEdge(GraficalEdge graficalEdge) throws Exception {
		boolean contains = graficalListEdge.contains(graficalEdge);

		if (contains) {

			throw new Exception("Edge already added");
		} else {

			graficalListEdge.add(graficalEdge);
			EdgeLine edgeLine = graficalEdge.getEdgeLine();
			edgeLine.paint(this.getGraphics(), this.defaultColor);
		}

	}

	public void highlightGraficalEdge(int startVertex, int endVertex, int cost,
			Color color) {
		try {
			GraficalEdge otherGraficalEdge;
			GraficalEdge graficalEdge = GraficalEdge.getZeroGraficalEdge(
					startVertex, endVertex, cost);
			int position = graficalListEdge.indexOf(graficalEdge);

			boolean contains = position != -1;
			EdgeLine edgeLine = null;
			if (contains) {
				otherGraficalEdge = graficalListEdge.get(position);
				edgeLine = otherGraficalEdge.getEdgeLine();
				edgeLine.paint(super.getGraphics(), color);
				edgeLine.setColor(color);
			} else {
				JOptionPane.showMessageDialog(this,
						GraphView2.GRAFICAL_EDGE_ERROR);

			}
		} catch (NullPointerException e) {
		}
	}

	public void highlightGraficalEdge(GraficalEdge graficalEdge, Color color) {
		int position = graficalListEdge.indexOf(graficalEdge);
		boolean contains = position != -1;
		EdgeLine edgeLine = null;
		if (contains) {
			edgeLine = graficalEdge.getEdgeLine();
			edgeLine.paint(super.getGraphics(), color);
			edgeLine.setColor(color);
		} else {
			JOptionPane.showMessageDialog(this, GraphView2.GRAFICAL_EDGE_ERROR);
		}
	}

	public void clearVertexArray() {

		for (int i = 0; i < vertexArray.length; i++) {

			vertexArray[i] = null;
		}
	}

	public void clearEdges() {

		graficalListEdge.clear();

	}

	public void clearPanel() {
		Graphics2D graphics = (Graphics2D) this.getGraphics();
		Color color = graphics.getBackground();
		int width = super.getWidth();
		int height = super.getHeight();
		graphics.clearRect(0, 0, width, height);
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public Color getSpecialColor() {
		return specialColor;
	}

	public void setSpecialColor(Color specialColor) {
		this.specialColor = specialColor;
	}

	public PanelState getPanelState() {
		return panelState;
	}

	public void setPanelState(PanelState panelState) {
		this.panelState = panelState;
	}

	public int getVertexToPut() {
		return vertexToPut;
	}

	public void setVertexToPut(int vertexToPut) {
		this.vertexToPut = vertexToPut;
	}

	public int getVertexQtyToPut() {
		return vertexQtyToPut;
	}

	public void setVertexQtyToPut(int vertexQtyToPut) {
		this.vertexQtyToPut = vertexQtyToPut;
		vertexArray = new GraficalVertex[vertexQtyToPut];

	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public Font getDefaultFont() {
		return defaultFont;
	}

	public void setDefaultFont(Font defaultFont) {
		this.defaultFont = defaultFont;
	}
	private class MyMouseListener implements MouseListener {
		private JPanel jPanel;
		private GraphView2 graphView;

		public MyMouseListener(JPanel jPanel) {
			this.jPanel = jPanel;
			this.graphView = (GraphView2) jPanel;
		}

		private void processPutVertex(MouseEvent arg0) {

			int mouseX = arg0.getX();
			int mouseY = arg0.getY();

			switch (panelState) {

			case NONE: {

				break;
			}

			case PUT_VERTEX: {
				Integer integer = new Integer(vertexToPut);

				if (vertexToPut < vertexQtyToPut) {
					// string,color,font,position
					GraficalVertex grafVertex = new GraficalVertex(
							integer.toString(), vertexColor, vertexFont,
							new Point(mouseX, mouseY));

					vertexArray[vertexToPut] = grafVertex;
					grafVertex.draw((Graphics2D) jPanel.getGraphics());

					vertexToPut++;

				} else if (vertexToPut >= vertexQtyToPut) {

					if (vertexToPut == (vertexQtyToPut)) {

						vertexArray[0].draw((Graphics2D) jPanel.getGraphics());
						vertexToPut++;
						JOptionPane.showMessageDialog(jPanel,
								"babsonicos putos");

					} else {

						JOptionPane.showMessageDialog(jPanel,
								GraphView2.VERTEX_QTY_ERROR);

					}

				}

				break;
			}

			case TEST_GRAFICAL_EDGE: {

				try {
					Point startPoint = new Point(mouseX, mouseY);
					Point endPoint = new Point(mouseX + 30, mouseY + 30);
					int cost = 10;// 0---->1
					GraficalEdge graficalEdge = new GraficalEdge(startPoint,
							endPoint, 0, 1, 10);

					graphView.addGraficalEdge(graficalEdge);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(jPanel, e.getMessage());
				}

				break;
			}

			default: {

				break;
			}

			}

		}

		public void mouseClicked(MouseEvent arg0) {
			processPutVertex(arg0);

		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}

		public void mouseEntered(MouseEvent arg0) {

		}

		public void mouseExited(MouseEvent arg0) {

		}

	}

	@Override
	public void edgeAdded(int startVertex, int endVertex) {

	}

	@Override
	public void edgeDeleted(int startVertex, int endVertex) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(GraphObserver graphObserver) {
		return false;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public void graphEmpty() {
		// TODO Auto-generated method stub
		this.clearPanel();
		this.clearEdges();

	}

	@Override
	public void notifyAddEdge(int startVertex, int endVertex, int cost) {
	}

	@Override
	public void setGraphType(GraphType type) {

	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void vertexAdded() {
		// TODO Auto-generated method stub
		GraficalVertex[] newVertexArray = new GraficalVertex[this.vertexQtyToPut + 1];

		for (int i = 0; i < vertexQtyToPut; i++) {

			newVertexArray[i] = vertexArray[i];
		}
		this.vertexArray = newVertexArray;
		this.vertexQtyToPut++;
		this.vertexToPut = vertexQtyToPut - 1;
		setPanelState(PanelState.PUT_VERTEX);

	}

	@Override
	public void vertexDeleted(int v) {

		GraficalVertex[] newVertexArray = new GraficalVertex[vertexQtyToPut - 1];

		for (int i = 0; i <= (v - 1); i++) {

			newVertexArray[i] = vertexArray[i];
		}

		for (int indexToOverwrite = (v + 1); indexToOverwrite < newVertexArray.length; indexToOverwrite++) {

			newVertexArray[indexToOverwrite - 1] = vertexArray[indexToOverwrite];
			newVertexArray[indexToOverwrite - 1]
					.setVertex(indexToOverwrite - 1);

		}

		vertexArray = newVertexArray;
		this.vertexQtyToPut = vertexArray.length;
		paint(getGraphics());

		// repaint();
	}

	@Override
	public void weightAdded(int startVertex, int endVertex, int weight) {
		Point startPoint = vertexArray[startVertex].getStartPoint();
		Point endPoint = vertexArray[endVertex].getStartPoint();

		GraficalEdge graficalEdge = new GraficalEdge(startPoint, endPoint,
				startVertex, endVertex, weight);

		System.out.println("cost is" + graficalEdge.getEdgeCost());

		graficalListEdge.add(graficalEdge);

		highlightGraficalEdge(graficalEdge, defaultColor);
	}

	@Override
	public void weightDeleted(int startVertex, int endVertex, int weight) {
		GraficalEdge graficalEdge = GraficalEdge.getZeroGraficalEdge(
				startVertex, endVertex, weight);
		int grafIndex = graficalListEdge.indexOf(graficalEdge);

		if (grafIndex != -1) {

			graficalEdge = graficalListEdge.get(grafIndex);
			graficalEdge.deleteEdge((Graphics2D) getGraphics(), defaultColor);
			graficalListEdge.remove(grafIndex);

		}
		paint(this.getGraphics());
	}
	public void deleteEdge(GraficalEdge ge, Graphics2D g, Color c) {
		ge.deleteEdge(g, c);
		graficalListEdge.remove(Collections.singleton(ge));
	}

	@Override
	public void deletedAllEdges() {
		this.clearPanel();
		this.clearEdges();

	}

	public void paint(Graphics graphics) {
		super.paint(graphics);

		if (vertexArray == null) {

			return;
		}

		System.out.println("painting");

		Iterator<GraficalEdge> iterator = this.graficalListEdge.iterator();

		GraficalEdge graficalEdge;
		EdgeLine edgeLine;
		while (iterator.hasNext()) {

			graficalEdge = iterator.next();
			edgeLine = graficalEdge.getEdgeLine();
			edgeLine.paint(graphics, edgeLine.getColor());
		}

		redrawAllVertex();
	}

	public void restoreConfig() {
		Iterator<GraficalEdge> iterator = this.graficalListEdge.iterator();

		GraficalEdge graficalEdge;
		EdgeLine edgeLine;
		while (iterator.hasNext()) {

			graficalEdge = iterator.next();
			edgeLine = graficalEdge.getEdgeLine();
			edgeLine.setColor(defaultColor);
			edgeLine.paint(getGraphics(), defaultColor);
		}

	}

	@Override
	public void notifyWayLight(List vertex, List cost, boolean doAfterRepaint) {// tenemos
		if (doAfterRepaint) {
			restoreConfig();
		}
		for (int j = 0; j < graficalListEdge.size(); j++) {

			for (int i = 0; i < vertex.size() - 1; i++) {

				if (graficalListEdge.get(j).equals(
						GraficalEdge.getZeroGraficalEdge(
								((Integer) vertex.get(i)),
								(Integer) vertex.get(i + 1),
								(Integer) cost.get(i)))) {
					highlightGraficalEdge(graficalListEdge.get(j), specialColor);
				}

			}
		}

		repaint();
	}

	@Override
	public void paintAllEdges(Color color) {
		restoreConfig();
	}

	@Override
	public void paintSpecialEdge(int startVertex, int endVertex, int cost,
			Color color) {
		highlightGraficalEdge(startVertex, endVertex, cost, color);

	}

}
