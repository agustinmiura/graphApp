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
package ar.edu.austral.view.gui;

import ar.edu.austral.model.utils.enums.GraphType;

import java.awt.Color;
import java.util.List;

public interface GraphObserver
{
    public void graphEmpty(  );

    public void vertexDeleted( int v );

    public void vertexAdded(  );

    public void edgeDeleted( int startVertex, int endVertex );

    public void edgeAdded( int startVertex, int endVertex );

    public void weightAdded( int startVertex, int endVertex, int weight );

    public void weightDeleted( int startVertex, int endVertex, int weight );

    public void setGraphType( GraphType type );

    public void deletedAllEdges(  );

    public boolean equals( GraphObserver graphObserver );

    public int getId(  );

    public void setId( int id );

    public void notifyAddEdge( int startVertex, int endVertex, int cost );

    public void notifyWayLight( List vertex, List cost, boolean doAfterRepaint );

    public void paintAllEdges( Color color );

    public void paintSpecialEdge( int startVertex, int endVertex, int cost, Color color );

    public Color getSpecialColor(  );
}
