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

import ar.edu.austral.view.elements.Arrow2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class EdgeLine
{
    private Line2D line;
    private String text;
    private double angle;
    private Color color;

    public Color getColor(  )
    {
        return color;
    }

    public void setColor( Color color )
    {
        this.color = color;
    }

    public EdgeLine( double x1, double y1, double x2, double y2, String text )
    {
        line = new Line2D.Double( x1, y1, x2, y2 );
        this.text = text;

        Double a =
            ( x2 - x1 ) / ( 
                              Math.sqrt( ( Math.pow( x2, 2 ) + Math.pow( y2, 2 ) ) ) -
                              ( Math.sqrt( Math.pow( x1, 2 ) + Math.pow( y1, 2 ) ) )
                           );

        angle = Math.acos( a );
    }

    public Line2D getLine(  )
    {
        return line;
    }

    public void setLine( Line2D line )
    {
        this.line = line;
    }

    public String getText(  )
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public double getAngle(  )
    {
        return angle;
    }

    public void setAngle( double angle )
    {
        this.angle = angle;
    }

    public void paint( Graphics g, Color c )
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint( c );
        g2d.draw( this.line );

        Arrow2D arrow = new Arrow2D( line.getP1(  ),
                                     line.getP2(  ) );
        g2d.draw( arrow );

        g2d.setFont( new Font( "Comic Sans", 0, 18 ) );

        double xmedio = ( ( line.getX2(  ) - line.getX1(  ) ) / 2 ) + line.getX1(  );
        double ymedio = ( ( line.getY2(  ) - line.getY1(  ) ) / 2 ) + line.getY1(  );
        g2d.setColor( c );
        g2d.drawString( text, (int) xmedio, (int) ymedio );
    }
}
