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

import ar.edu.austral.view.test.EdgeLine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class GraficalEdge
{
    private static final Point POINT = new Point( 10, 10 );
    private EdgeLine edgeLine;
    private int startVertex;
    private int endVertex;

    /**
     * gets a grafical edge to search in a list if contains one that has //
     * startVertex,endVertex,cost
     *
     * @param startVertex
     * @param endVertex
     * @param cost
     * @return
     */
    public static GraficalEdge getZeroGraficalEdge( int startVertex, int endVertex, int cost )
    {
        // Point point=new Point(10,10);
        GraficalEdge graficalEdge =
            new GraficalEdge( GraficalEdge.POINT, GraficalEdge.POINT, startVertex, endVertex, cost );

        return graficalEdge;
    }

    public boolean equals( Object object )
    {
        GraficalEdge graficalEdge = (GraficalEdge) object;

        return this.equalsTo( graficalEdge );
    }

    public boolean equalsTo( GraficalEdge graficalEdge )
    {
        boolean answer = false;
        boolean check0 = startVertex == graficalEdge.getStartVertex(  );
        boolean check1 = endVertex == graficalEdge.getEndVertex(  );

        Integer myCost = new Integer( edgeLine.getText(  ) );
        Integer otherCost = new Integer( graficalEdge.getEdgeLine(  ).getText(  ) );
        boolean check2 = myCost.equals( otherCost );

        answer = check0 && check1 && check2;

        return answer;
    }

    /**
     *
     * @param startPoint
     * @param endPoint
     * @param startVertex
     * @param endVertex
     * @param vertexCost
     */
    public GraficalEdge( Point startPoint, Point endPoint, int startVertex, int endVertex, int vertexCost )
    {
        double x1 = startPoint.getX(  );
        double y1 = startPoint.getY(  );
        double x2 = endPoint.getX(  );
        double y2 = endPoint.getY(  );
        String integerString = String.valueOf( vertexCost );
        EdgeLine edgeLine = new EdgeLine( x1, y1, x2, y2, integerString );
        this.edgeLine = edgeLine;
    }

    public Point getStartPoint(  )
    {
        Line2D line = edgeLine.getLine(  );

        return (Point) ( line.getP1(  ) );
    }

    public Point getEndPoint(  )
    {
        Line2D line = edgeLine.getLine(  );

        return (Point) ( line.getP2(  ) );
    }

    public GraficalEdge( EdgeLine edgeLine, int startVertex, int endVertex )
    {
        this.edgeLine = edgeLine;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public EdgeLine getEdgeLine(  )
    {
        return edgeLine;
    }

    public int getEdgeCost(  )
    {
        int a = Integer.parseInt( edgeLine.getText(  ) );

        return a;
    }

    public void setEdgeCost( Integer a )
    {
        edgeLine.setText( a.toString(  ) );
    }

    public void deleteEdge( Graphics2D g, Color c )
    { // Enviamos el color de fondo
      // para repintar
        edgeLine.paint( g, c );
        edgeLine = null;
    }

    public void setEdgeLine( EdgeLine edgeLine )
    {
        this.edgeLine = edgeLine;
    }

    public int getStartVertex(  )
    {
        return startVertex;
    }

    public void setStartVertex( int startVertex )
    {
        this.startVertex = startVertex;
    }

    public int getEndVertex(  )
    {
        return endVertex;
    }

    public void setEndVertex( int endVertex )
    {
        this.endVertex = endVertex;
    }
}
