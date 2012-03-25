/*
 * Copyright (C) 2009         Almada Emiliano
 *                                                 Miura Agustín
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

import ar.edu.austral.controller.GraphAppConstants;
import ar.edu.austral.model.interfaces.IWeight;
import ar.edu.austral.view.gui.GraphObserver;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DeleteEdgeDialog
    extends AddEdgeDialog
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DeleteEdgeDialog( JFrame frame, GraphObserver go, IWeight g )
    {
        super( frame, go, g );
    }

    protected void acceptButtonActionPerformed( ActionEvent evt )
    {
        int v1 = Integer.parseInt( origenTextField.getText(  ) );

        int v2 = Integer.parseInt( destinoTextField.getText(  ) );

        int cost = Integer.parseInt( costoTextField.getText(  ) );

        try
        {
            g.deleteWeight( v1, v2, cost );
        } catch ( NumberFormatException e1 )
        {
            JOptionPane.showMessageDialog( jFrame, GraphAppConstants.MESSAGE_ERROR_INVALID_PARAMETER );
        } catch ( NullPointerException e1 )
        {
            JOptionPane.showMessageDialog( jFrame, GraphAppConstants.MESSAGE_ERROR_GRAPH_UNINITIALIZED );
        } catch ( Exception e1 )
        {
            JOptionPane.showMessageDialog( jFrame, GraphAppConstants.MESSAGE_ERROR_EDGE_UNINITIALIZED );
        }
    }
}
