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

import ar.edu.austral.model.algorithms.Dijkstra2;
import ar.edu.austral.model.algorithms.NodoA;
import ar.edu.austral.model.implementations.AdjacentMatrixWeightDirected;
import ar.edu.austral.model.interfaces.IGraph;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MyListDijkstraDialog
    extends MyListDialog
{
    private static final long serialVersionUID = 1L;
    private NodoA[] resultado;
    private int nodo_origen;
    private IGraph g;
    private Dijkstra2<Integer> d;

    public MyListDijkstraDialog( Frame frame, List list, NodoA[] camino, int nodo_origen,
                                 AdjacentMatrixWeightDirected<Integer> g, Dijkstra2<Integer> d )
    {
        super( frame, list );
        this.resultado = camino;
        this.nodo_origen = nodo_origen;
        this.d = d;
        this.g = g;
    }

    protected void acceptButtonActionPerformed( ActionEvent actionEvent )
    {
        int n = jList.getSelectedIndex(  );

        if ( n >= 0 )
        {
            System.out.println( "N=" + n );

            int i = n;
            ArrayList res = new ArrayList<Integer>( 0 );
            ArrayList cost = new ArrayList<Integer>( 0 );

            for ( int j = 0; j < ( resultado[i].camino.size(  ) - 1 ); j++ )
            {
                res.add( resultado[i].camino.get( j ).intValue(  ) + 1 );
            }

            res.add( resultado[i].camino.get( resultado[i].camino.size(  ) - 1 ).intValue(  ) + 1 );

            for ( int j = 0; j < ( res.size(  ) - 1 ); j++ )
            {
                try
                {
                    cost.add( d.getMinWeight( (IGraph) g, (Integer) ( res.get( j ) ) - 1, (Integer) res.get( j + 1 ) -
                                              1 ) );
                } catch ( Exception e )
                {
                    e.printStackTrace(  );
                }
            }

            go.notifyWayLight( res, cost, true );
        }
    }
}
