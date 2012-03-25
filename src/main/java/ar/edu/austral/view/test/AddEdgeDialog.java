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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.edu.austral.controller.GraphAppConstants;
import ar.edu.austral.model.interfaces.IWeight;
import ar.edu.austral.view.gui.GraphObserver;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class AddEdgeDialog extends javax.swing.JDialog {
	protected JLabel origenLabel;
	protected JPanel jPanel1;
	protected JButton cancelButton;
	protected JButton acceptButton;
	protected JTextField costoTextField;
	protected JLabel costoLabel;
	protected JTextField destinoTextField;
	protected JLabel destinoLabel;
	protected JTextField origenTextField;
	protected GraphObserver go;
	protected IWeight g;
	protected JFrame jFrame;

	public AddEdgeDialog(JFrame frame, GraphObserver go, IWeight g) {
		super(frame);
		this.jFrame = jFrame;
		this.go = go;
		this.g = g;
		initGUI();
		initActions();
	}

	private void initActions() {
		cancelButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});

		acceptButton.addActionListener(new AbstractAction() {
			// This method is called when the button is pressed
			public void actionPerformed(ActionEvent evt) {
				acceptButtonActionPerformed(evt);
			}

		});
	}

	protected void acceptButtonActionPerformed(ActionEvent evt) {

		int v1 = Integer.parseInt(origenTextField.getText());

		int v2 = Integer.parseInt(destinoTextField.getText());

		int cost = Integer.parseInt(costoTextField.getText());

		try {
			g.addWeight(v1, v2, cost);
			go.notifyAddEdge(v1, v2, cost);

		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(jFrame,
					GraphAppConstants.MESSAGE_ERROR_INVALID_PARAMETER);
		} catch (NullPointerException e1) {

			JOptionPane.showMessageDialog(jFrame,
					GraphAppConstants.MESSAGE_ERROR_GRAPH_UNINITIALIZED);

		} catch (Exception e1) {

			JOptionPane.showMessageDialog(jFrame,
					GraphAppConstants.MESSAGE_ERROR_ALREADY_EXISTS);
		}

	}

	protected void addObserver(GraphObserver go) {
		this.go = go;
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout(1, 1);
			thisLayout.setColumns(1);
			thisLayout.setHgap(5);
			thisLayout.setVgap(5);
			getContentPane().setLayout(thisLayout);

			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				FormLayout jPanel1Layout = new FormLayout(
						"26dlu, max(p;5dlu), max(p;5dlu), 25dlu, 42dlu, max(p;15dlu), max(p;15dlu)",
						"max(p;15dlu), max(p;5dlu), 9dlu, 17dlu, 8dlu, max(p;15dlu), 19dlu, max(p;15dlu)");
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setPreferredSize(new java.awt.Dimension(271, 228));
				jPanel1.setSize(292, 220);
				{
					origenLabel = new JLabel();
					jPanel1.add(origenLabel, new CellConstraints(
							"2, 2, 1, 1, default, default"));
					origenLabel.setText("Vertice origen");
				}
				{
					origenTextField = new JTextField();
					jPanel1.add(origenTextField, new CellConstraints(
							"4, 2, 1, 1, default, default"));
					origenTextField.setPreferredSize(new java.awt.Dimension(22,
							21));
				}
				{
					destinoLabel = new JLabel();
					jPanel1.add(destinoLabel, new CellConstraints(
							"2, 4, 1, 1, default, default"));
					destinoLabel.setText("Vertice destino");
				}
				{
					destinoTextField = new JTextField();
					jPanel1.add(destinoTextField, new CellConstraints(
							"4, 4, 1, 1, default, default"));
					destinoTextField.setPreferredSize(new java.awt.Dimension(
							21, 21));
				}
				{
					costoLabel = new JLabel();
					jPanel1.add(costoLabel, new CellConstraints(
							"2, 6, 1, 1, default, default"));
					costoLabel.setText("Costo");
				}
				{
					costoTextField = new JTextField();
					jPanel1.add(costoTextField, new CellConstraints(
							"4, 6, 1, 1, default, default"));
					costoTextField.setPreferredSize(new java.awt.Dimension(24,
							21));
				}
				{
					acceptButton = new JButton();
					jPanel1.add(acceptButton, new CellConstraints(
							"5, 8, 1, 1, default, default"));
					acceptButton.setText("Aceptar");
				}
				{
					cancelButton = new JButton();
					jPanel1.add(cancelButton, new CellConstraints(
							"6, 8, 1, 1, default, default"));
					cancelButton.setText("Cancelar");
				}
			}
			this.setSize(410, 250);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JTextField getDestinoTextField() {
		return destinoTextField;
	}

}
