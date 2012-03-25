package ar.edu.austral.view.elements;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.NoSuchElementException;

/**
 * Iterator for Arrow2D shape.</P>
 *
 * <b>Change history</b><BR>
 * [ 2004-09-27 ] C. Schalkwijk - Initial setup.<BR>
 * [ 2004-09-28 ] C. Schalkwijk - Updated path iterators to use the polygon.<BR>
 * <BR>
 *
 * @author C. Schalkwijk
 * @version 0.2
 *
 */
public class ArrowIterator
    implements PathIterator
{
    // Attributes
    private int index = 0;
    private Arrow2D arrow = null;
    private AffineTransform affine = null;
    private PathIterator itArrow = null;

    /**
    *
    */
    public ArrowIterator( Arrow2D arrow, AffineTransform affine )
    {
        this.arrow = arrow;
        this.affine = affine;
    }

    /**
    *
    */
    public int getWindingRule(  )
    {
        return WIND_NON_ZERO;
    }

    /**
    *
    */
    public void next(  )
    {
        this.index++;
    }

    /**
    *
    */
    public boolean isDone(  )
    {
        return ( this.index > 4 );
    }

    /**
    *
    */
    public int currentSegment( double[] coords )
    {
        if ( this.isDone(  ) )
        {
            throw new NoSuchElementException( "Arrow iterator out of bounds" );
        }

        int type;

        if ( this.index == 0 )
        {
            this.itArrow = this.arrow.getArrow(  ).getPathIterator( this.affine );
            this.itArrow.currentSegment( coords );
            this.itArrow.next(  );
            type = SEG_MOVETO;
        } else if ( this.index == 1 )
        {
            this.itArrow.currentSegment( coords );
            this.itArrow.next(  );
            type = SEG_LINETO;
        } else if ( this.index == 2 )
        {
            this.itArrow.currentSegment( coords );
            this.itArrow.next(  );
            type = SEG_LINETO;
        } else if ( this.index == 3 )
        {
            this.itArrow.currentSegment( coords );
            this.itArrow.next(  );
            type = SEG_MOVETO;
        } else
        {
            this.itArrow.currentSegment( coords );
            this.itArrow.next(  );
            this.itArrow = null;
            type = SEG_LINETO;
        }

        return type;
    }

    /**
    *
    */
    public int currentSegment( float[] coords )
    {
        if ( this.isDone(  ) )
        {
            throw new NoSuchElementException( "Arrow iterator out of bounds" );
        }

        int type;

        if ( this.index == 0 )
        {
            this.itArrow = this.arrow.getArrow(  ).getPathIterator( this.affine );
            this.itArrow.currentSegment( coords );
            this.itArrow.next(  );
            type = SEG_MOVETO;
        } else if ( this.index == 1 )
        {
            this.itArrow.currentSegment( coords );
            this.itArrow.next(  );
            type = SEG_LINETO;
        } else if ( this.index == 2 )
        {
            this.itArrow.currentSegment( coords );
            this.itArrow.next(  );
            type = SEG_LINETO;
        } else if ( this.index == 3 )
        {
            this.itArrow.currentSegment( coords );
            this.itArrow.next(  );
            type = SEG_MOVETO;
        } else
        {
            this.itArrow.currentSegment( coords );
            this.itArrow.next(  );
            this.itArrow = null;
            type = SEG_LINETO;
        }

        if ( this.affine != null )
        {
            this.affine.transform( coords, 0, coords, 0, 1 );
        }

        return type;
    }
}
