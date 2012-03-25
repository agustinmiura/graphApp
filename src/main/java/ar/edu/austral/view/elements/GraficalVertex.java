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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class GraficalVertex
{
    public static final Font DEFAULT_FONT = new Font( "Arial", Font.BOLD, 80 );
    private static final String NODE = ".";
    private String string;
    private Color color; // color for font and vertex
    private Font font;
    private Point startPoint;

    public boolean equals( GraficalVertex graficalVertex )
    {
        Integer myVertex = new Integer( string );
        Integer otherVertex = new Integer( graficalVertex.getString(  ) );

        return ( myVertex.equals( otherVertex ) );
    }

    public Integer getVertex(  )
    {
        return Integer.parseInt( string );
    }

    public void setVertex( Integer vertex )
    {
        String string = new String( vertex.toString(  ) );
        this.string = string;
    }

    public GraficalVertex( String string, Color color, Font font, Point startPoint )
    {
        this.string = string;
        this.color = color;
        this.font = font;
        this.startPoint = startPoint;
    }

    public void deleteVertex( Graphics2D graphics, Color color )
    {
        Color backColor = graphics.getBackground(  );
        graphics.setFont( font );
        graphics.drawString( string, startPoint.x, startPoint.y + 20 ); // era 1

        graphics.setFont( DEFAULT_FONT );
        graphics.drawString( GraficalVertex.NODE, startPoint.x, startPoint.y - 6 );
    }

    public void draw( Graphics2D graphics, Color otherColor )
    {
        graphics.setColor( otherColor );
        graphics.setFont( font );
        graphics.drawString( string, startPoint.x, startPoint.y + 20 ); // era 1

        graphics.setFont( DEFAULT_FONT );
        graphics.drawString( GraficalVertex.NODE, startPoint.x, startPoint.y - 6 );
    }

    public void draw( Graphics2D graphics )
    {
        graphics.setColor( color );
        graphics.setFont( font );
        graphics.drawString( string, startPoint.x, startPoint.y + 20 );

        graphics.setFont( DEFAULT_FONT );
        graphics.drawString( GraficalVertex.NODE, startPoint.x, startPoint.y - 6 );
    }

    public String getString(  )
    {
        return string;
    }

    public void setString( String string )
    {
        this.string = string;
    }

    public Color getColor(  )
    {
        return color;
    }

    public void setColor( Color color )
    {
        this.color = color;
    }

    public Font getFont(  )
    {
        return font;
    }

    public void setFont( Font font )
    {
        this.font = font;
    }

    public Point getStartPoint(  )
    {
        return startPoint;
    }

    public void setStartPoint( Point startPoint )
    {
        this.startPoint = startPoint;
    }
}
