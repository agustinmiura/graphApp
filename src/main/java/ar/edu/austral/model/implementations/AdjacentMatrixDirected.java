/*
 * Copyright (C) 2009         Almada Emiliano
 *                            Miura Agustín
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
package ar.edu.austral.model.implementations;

import ar.edu.austral.model.interfaces.Directed;
import ar.edu.austral.model.utils.enums.GraphType;
import ar.edu.austral.view.gui.GraphObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AdjacentMatrixDirected<T>
    implements Directed<T>
{
    private List<Integer>[][] matrix;
    private int vertexQty;
    private int edgeQty;
    private List<T> vertexObjectList;
    private List<GraphObserver> observers;
    private static final Object OBJECT = new Object(  );
    private static final int DEFAULT_QTY = 10;

    private void subConstructor( int vertexQty )
    {
        this.vertexQty = vertexQty;
        edgeQty = 0;

        vertexObjectList = new ArrayList( vertexQty );

        for ( int i = 0; i < vertexQty; i++ )
        {
            vertexObjectList.add( (T) OBJECT );
        }

        matrix = new List[vertexQty][vertexQty];

        for ( int i = 0; i < vertexQty; i++ )
        {
            for ( int j = 0; j < vertexQty; j++ )
            {
                matrix[i][j] = new LinkedList<Integer>(  );
            }
        }

        List observers = new ArrayList(  );
    }

    public AdjacentMatrixDirected(  )
                           throws Exception
    {
        this( AdjacentMatrixDirected.DEFAULT_QTY );
    }

    public AdjacentMatrixDirected( int vertexQty )
                           throws Exception
    {
        if ( vertexQty <= 0 )
        {
            throw new Exception( "Using bad vertex qty in graph" );
        } else
        {
            subConstructor( vertexQty );
        }
    }

    @Override
    public List<Integer> getPredeccesorList( int vertex )
                                     throws Exception
    {
        if ( isInvalidVertex( vertex ) )
        {
            throw new Exception( "Bad vertex qty" );
        }

        List<Integer> answerList = new ArrayList<Integer>( vertexQty );
        int colToCheck = vertex;
        boolean isEmptyList;
        boolean foundVertex;

        int max = vertex - 1;

        for ( int i = 0; i <= max; i++ )
        {
            isEmptyList = matrix[i][colToCheck].isEmpty(  );

            if ( ! isEmptyList )
            {
                answerList.add( i );
            }
        }

        int min = ( vertex + 1 );

        for ( int i = min; i < vertexQty; i++ )
        {
            isEmptyList = matrix[i][colToCheck].isEmpty(  );

            if ( ! isEmptyList )
            {
                answerList.add( i );
            }
        }

        return answerList;
    }

    @Override
    public List<Integer> getSuccesorList( int vertex )
                                  throws Exception
    {
        if ( isInvalidVertex( vertex ) )
        {
            throw new Exception( "Bad vertex qty" );
        }

        List<Integer> answerList = new ArrayList<Integer>( vertexQty );
        int filToCheck = vertex;
        boolean isEmptyList;

        for ( int i = 0; i < vertexQty; i++ )
        {
            isEmptyList = matrix[filToCheck][i].isEmpty(  );

            if ( ! isEmptyList )
            {
                answerList.add( i );
            }
        }

        return answerList;
    }

    @Override
    public void addEdge( int startVertex, int endVertex )
                 throws Exception
    {
        boolean invalidVertex0 = isInvalidVertex( startVertex );
        boolean invalidVertex1 = isInvalidVertex( endVertex );

        if ( invalidVertex0 || invalidVertex1 )
        {
            throw new Exception( "Invalid vertex used" );
        }

        throw new Exception( "Method not implemented cant use this" );
    }

    private void subAddVertex( T t )
    {
        int oldVertexQty = matrix.length;
        List<Integer>[][] oldMatrix = matrix;
        List<T> oldList = vertexObjectList;

        int newVertexQty = ( oldVertexQty + 1 );
        List<Integer>[][] newMatrix = new List[newVertexQty][newVertexQty];
        List<T> newList = new ArrayList<T>( newVertexQty );

        newList.addAll( oldList );

        //
        newList.add( t );

        int size = oldVertexQty;

        for ( int i = 0; i < size; i++ )
        {
            for ( int j = 0; j < size; j++ )
            {
                newMatrix[i][j] = oldMatrix[i][j];
            }
        }

        vertexObjectList = newList;
        vertexQty = newVertexQty;
        matrix = newMatrix;

        for ( int i = 0; i < vertexQty; i++ )
        {
            matrix[vertexQty - 1][i] = new LinkedList<Integer>(  );
        }

        for ( int i = 0; i < vertexQty; i++ )
        {
            matrix[i][vertexQty - 1] = new LinkedList<Integer>(  );
        }
    }

    @Override
    public void deleteEdge( int startVertex, int endVertex )
                    throws Exception
    {
        boolean invalidVertex0 = isInvalidVertex( startVertex );
        boolean invalidVertex1 = isInvalidVertex( endVertex );

        if ( invalidVertex0 || invalidVertex1 )
        {
            throw new Exception( "Bad vertex used" );
        }

        matrix[startVertex][endVertex].clear(  );
    }

    private void subDeleteVertexObject( int vertexToDelete )
    {
        int oldVertexQty = vertexQty;

        T tempT;

        for ( int i = vertexToDelete + 1; i < oldVertexQty; i++ )
        {
            tempT = vertexObjectList.get( i );
            vertexObjectList.set( ( i - 1 ), tempT );
        }
    }

    private void subDeleteVertex( int vertexToDelete )
    {
        int filToReplace;

        for ( int i = ( vertexToDelete + 1 ); ( i < vertexQty ) && ( ( i - 1 ) >= 0 ); i++ )
        {
            filToReplace = ( i - 1 );

            for ( int col = 0; col < vertexQty; col++ )
            {
                matrix[filToReplace][col] = matrix[i][col];
            }
        }

        int colToReplace;

        for ( int j = ( vertexToDelete + 1 ); ( j < vertexQty ) && ( ( j - 1 ) >= 0 ); j++ )
        {
            colToReplace = ( j - 1 );

            for ( int fil = 0; fil < vertexQty; fil++ )
            {
                matrix[fil][colToReplace] = matrix[fil][j];
            }
        }
    }

    @Override
    public void deleteVertex( int vertexToDelete )
                      throws Exception
    {
        if ( isInvalidVertex( vertexToDelete ) )
        {
            throw new Exception( "Cant delete that vertex" );
        } else
        {
            subDeleteVertexObject( vertexToDelete );
            subDeleteVertex( vertexToDelete );
            vertexQty--;

            Iterator<GraphObserver> iterator = observers.iterator(  );

            while ( iterator.hasNext(  ) )
            {
                iterator.next(  ).vertexDeleted( vertexToDelete );
            }
        }
    }

    @Override
    public void empty(  )
    {
        int oldVertexQty = vertexQty;

        subConstructor( oldVertexQty );
    }

    @Override
    public boolean existEdge( int startVertex, int endVertex )
                      throws Exception
    {
        boolean answer = false;

        if ( isInvalidVertex( startVertex ) || isInvalidVertex( endVertex ) )
        {
            throw new Exception( "Bad vertex used" );
        }

        answer = matrix[startVertex][endVertex].isEmpty(  );

        return ( ! answer );
    }

    @Override
    public int getEdgeQty(  )
    {
        return edgeQty;
    }

    private boolean isInvalidVertex( int vertex )
    {
        if ( ( vertex <= -1 ) || ( vertex >= matrix.length ) )
        {
            return true;
        } else
        {
            return false;
        }
    }

    private boolean isInvalidParameterException( int vertexQty )
                                         throws Exception
    {
        boolean answer = false;

        if ( vertexQty <= -1 )
        {
            throw new Exception( "Bad vertex used" );
        } else
        {
            return answer;
        }
    }

    @Override
    public T getVertexObject( int vertex )
                      throws Exception
    {
        boolean badParameter = isInvalidParameterException( vertex );
        T t = vertexObjectList.get( vertex );
        Object objectTemp = t;
        boolean isNull = objectTemp.equals( AdjacentMatrixDirected.OBJECT );

        if ( isNull )
        {
            return null;
        } else
        {
            return t;
        }
    }

    @Override
    public int getVertexQty(  )
    {
        return vertexQty;
    }

    @Override
    public boolean isEmpty(  )
    {
        return ( edgeQty == 0 );
    }

    @Override
    public void setVertexObject( T t, int vertex )
                         throws Exception
    {
        if ( isInvalidVertex( vertex ) )
        {
            throw new Exception( "Bad parameter used vertex, or t to add" );
        } else if ( t == null )
        {
            vertexObjectList.set( vertex, (T) AdjacentMatrixDirected.OBJECT );
        } else
        {
            vertexObjectList.set( vertex, t );
        }
    }

    @Override
    public void addObserver( GraphObserver observer )
    {
        observers.add( observer );
    }

    @Override
    public GraphType getGraphType(  )
    {
        return GraphType.DIRECTED;
    }

    @Override
    public void setGraphType( GraphType type )
    {
    }

    @Override
    public void addVertex( T t )
    {
        subAddVertex( t );
    }

    @Override
    public void clearObservers(  )
    {
    }

    @Override
    public void deleteObserver( GraphObserver graphObserver )
    {
    }
}
