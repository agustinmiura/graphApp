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
package ar.edu.austral.view.elements;

import ar.edu.austral.model.algorithms.GraphUtils;
import ar.edu.austral.model.interfaces.IGraph;
import ar.edu.austral.model.interfaces.IWeight;
import ar.edu.austral.view.gui.GraphObserver;
import ar.edu.austral.view.gui.GraphView2;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MyPrimDialog
    extends MyListDialog
{
    private static final String STRING_NONE = "None";
    private static final String STRING_NORMAL = "Normal";
    private static final String STRING_SOLVE = "Show solution";
    private static final long serialVersionUID = 1L;
    private int[] primFatherArray;
    private GraphObserver graphObserver;
    private IGraph iGraph;

    public MyPrimDialog( Frame frame, List list, int[] primFatherArray, IGraph iGraph, GraphObserver graphObserver )
    {
        super( frame, list );
        this.graphObserver = graphObserver;
        this.primFatherArray = primFatherArray;
        this.iGraph = iGraph;
    }

    public GraphObserver getGraphObserver(  )
    {
        return graphObserver;
    }

    public void setObserver( GraphObserver graphObserver )
    {
        this.graphObserver = graphObserver;
    }

    public static List<String> getDialogPrimActions(  )
    {
        List<String> list = new ArrayList(  );
        list.add( MyPrimDialog.STRING_NONE );
        list.add( MyPrimDialog.STRING_NORMAL );
        list.add( MyPrimDialog.STRING_SOLVE );

        return list;
    }

    // here the exception
    private void paintSolutionInGraph(  )
    {
        try
        {
            int sizeArray = primFatherArray.length;
            IWeight iWeight = (IWeight) iGraph;
            boolean check0 = iWeight == null;
            boolean vertexIsNotConnected;
            int vertexToConnect;
            int startVertex;
            int endVertex;
            int minimumCost;

            for ( int i = 0; i < sizeArray; i++ )
            {
                vertexToConnect = primFatherArray[i];
                vertexIsNotConnected = ( vertexToConnect == -1 );

                if ( ! vertexIsNotConnected )
                {
                    startVertex = i;
                    endVertex = vertexToConnect;

                    minimumCost = GraphUtils.getMinWeight( iGraph, startVertex, endVertex );

                    GraphView2 graphView2 = (GraphView2) graphObserver;
                    int state = -1;

                    graphView2.highlightGraficalEdge( startVertex, endVertex, minimumCost, Color.BLUE );
                }
            }
        } catch ( Exception e )
        {
        }
    }

    protected void acceptButtonActionPerformed( ActionEvent actionEvent )
    {
        int n = jList.getSelectedIndex(  );

        switch ( n )
        {
            case 0:
                break;

            case 1:
            {
                graphObserver.paintAllEdges( null );

                break;
            }

            case 2:
            {
                paintSolutionInGraph(  );

                break;
            }

            default:
                break;
        }
    }
}
