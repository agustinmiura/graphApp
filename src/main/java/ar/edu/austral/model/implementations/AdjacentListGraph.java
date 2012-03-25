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

import ar.edu.austral.model.interfaces.Undirected;
import ar.edu.austral.model.utils.enums.GraphType;
import ar.edu.austral.view.gui.GraphObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AdjacentListGraph<T>
    implements Undirected<T>
{
    private List<Integer>[] adyacentList;
    private int vertexQty;
    private int edgeQty;
    private List<T> vertexObjectList;
    private static final Object object = new Object(  );
    private static final Integer ZERO = new Integer( 0 );
    private List<Boolean> vertexWithLoop;
    private Comparator<Integer> comparatorInteger;
    private GraphType graphType;

    private void subConstructor( int qty )
    {
        vertexQty = qty;
        edgeQty = 0;
        adyacentList = new LinkedList[qty];

        for ( int i = 0; i < adyacentList.length; i++ )
        {
            adyacentList[i] = new LinkedList<Integer>(  );
        }

        vertexObjectList = new ArrayList<T>( qty );

        for ( int i = 0; i < qty; i++ )
        {
            vertexObjectList.add( (T) AdjacentListGraph.object );
        }

        vertexWithLoop = new ArrayList<Boolean>( vertexQty );

        for ( int i = 0; i < vertexQty; i++ )
        {
            vertexWithLoop.add( false );
        }

        comparatorInteger = new ComparatorInteger(  );
    }

    private class ComparatorInteger
        implements Comparator<Integer>
    {
        public int compare( Integer arg0, Integer arg1 )
        {
            return ( arg0 - arg1 );
        }
    }

    public AdjacentListGraph( int vertexQty )
                      throws Exception
    {
        if ( vertexQty <= 0 )
        {
            throw new Exception( "Bad parameter used" );
        } else
        {
            subConstructor( vertexQty );
        }
    }

    private boolean isInvalidVertex( int vertex )
    {
        return ( ( vertex < 0 ) || ( vertex >= vertexQty ) );
    }

    private List<Integer> subGetAdjacentList( int vertex )
    {
        List<Integer> adjacentListAnswer = new ArrayList<Integer>( vertexQty );

        Iterator<Integer> iterator = adyacentList[vertex].iterator(  );

        while ( iterator.hasNext(  ) )
        {
            adjacentListAnswer.add( iterator.next(  ) );
        }

        Collections.sort( adjacentListAnswer );

        int position = Collections.binarySearch( adjacentListAnswer, vertex );

        if ( position >= 0 )
        {
            adjacentListAnswer.remove( Collections.singleton( vertex ) );
        }

        return adjacentListAnswer;
    }

    public List<Integer> getAdjacentList( int vertex )
                                  throws Exception
    {
        if ( isInvalidVertex( vertex ) )
        {
            throw new Exception( "Bad vertex used" );
        }

        return subGetAdjacentList( vertex );
    }

    public int getVertexQty(  )
    {
        return vertexQty;
    }

    public boolean isEmpty(  )
    {
        return ( edgeQty == 0 );
    }

    public void empty(  )
    {
        int oldVertexQty = vertexQty;
        subConstructor( oldVertexQty );
    }

    public int getEdgeQty(  )
    {
        return edgeQty;
    }

    private void subDeleteVertexWithLoop( int vertexToDelete )
                                  throws Exception
    {
        int oldVertexQty = vertexQty;

        Boolean check;

        for ( int i = vertexToDelete + 1; ( i >= ( vertexToDelete + 1 ) ) && ( i < oldVertexQty ); i++ )
        {
            check = vertexWithLoop.get( i );
            vertexWithLoop.set( ( i - 1 ), check );
        }
    }

    private void subDeleteVertexObjectList( int vertexToDelete )
                                    throws Exception
    {
        int oldVertexQty = vertexQty;
        T tempT;

        for ( int i = vertexToDelete + 1; ( i >= ( vertexToDelete + 1 ) ) && ( i < oldVertexQty ); i++ )
        {
            tempT = vertexObjectList.get( i );
            vertexObjectList.set( ( i - 1 ), tempT );
        }

        vertexObjectList.set( oldVertexQty - 1, null );
    }

    private void removeFromAdjacentList( int vertex )
                                 throws Exception
    {
        for ( int i = 0; i < vertexQty; i++ )
        {
            adyacentList[i].removeAll( Collections.singleton( new Integer( vertex ) ) );
        }
    }

    private void decreaseVertexInAdyacentList( int vertex )
                                       throws Exception
    {
        List listInteger;
        Iterator<Integer> iterator;
        List<Integer> tempList = new LinkedList<Integer>(  );
        Integer integer;

        for ( int i = 0; i < vertexQty; i++ )
        {
            tempList.clear(  );

            listInteger = adyacentList[i];

            iterator = listInteger.iterator(  );

            while ( iterator.hasNext(  ) )
            {
                integer = iterator.next(  );

                if ( integer.equals( AdjacentListGraph.ZERO ) )
                {
                    tempList.add( new Integer( 0 ) );

                    continue;
                } else
                {
                    integer = integer - 1;
                    tempList.add( integer );
                }
            }

            listInteger.clear(  );
            listInteger.addAll( tempList );
        }
    }

    private void subDeleteVertex( int vertexToDelete )
                          throws Exception
    {
        int oldVertexQty = vertexQty;
        List<Integer> tempList;

        for ( int i = vertexToDelete + 1; ( i >= ( vertexToDelete + 1 ) ) && ( i < oldVertexQty ); i++ )
        {
            tempList = adyacentList[i];
            adyacentList[i - 1] = tempList;
        }

        vertexQty--;
    }

    private void resetVertexWithLoop(  )
    {
        int position;
        List<Integer> listInteger;

        for ( int i = 0; i < vertexQty; i++ )
        {
            listInteger = adyacentList[i];
            listInteger.removeAll( Collections.singleton( i ) );
        }

        for ( int i = 0; i < vertexQty; i++ )
        {
            if ( this.vertexWithLoop.get( i ) )
            {
                adyacentList[i].add( i );
            }
        }
    }

    public void deleteVertex( int vertexToDelete )
                      throws Exception
    {
        if ( isInvalidVertex( vertexToDelete ) || ( ! adyacentList[vertexToDelete].isEmpty(  ) ) )
        {
            throw new Exception( "bad vertex to delete or the vertex has adjacent vertex" );
        } else
        {
            // update the objects
            subDeleteVertexObjectList( vertexToDelete );

            // update the loops for the graph
            subDeleteVertexWithLoop( vertexToDelete );

            // decrease the vertex
            decreaseVertexInAdyacentList( -1 );

            // update the integers in the adjacentlist
            subDeleteVertex( vertexToDelete );

            subDeleteVertexWithLoop( vertexToDelete );

            resetVertexWithLoop(  ); // update the loops
        }
    }

    public void addVertex( T t )
    {
        int oldVertexQty = vertexQty;
        List<T> oldList = vertexObjectList;
        List<Integer>[] oldAdyacentList = adyacentList;
        List<Boolean> oldLoopList = vertexWithLoop;

        int newVertexQty = vertexQty + 1;
        List<T> newList = new ArrayList<T>( newVertexQty );
        List<Integer>[] newAdyacentList = new LinkedList[newVertexQty];
        List<Boolean> newLoopList = new ArrayList<Boolean>( newVertexQty );

        newList.addAll( oldList );
        newList.add( t );

        newLoopList.addAll( oldLoopList );
        newLoopList.add( false );

        for ( int i = 0; i < oldVertexQty; i++ )
        {
            newAdyacentList[i] = oldAdyacentList[i];
        }

        for ( int i = oldVertexQty; i < newVertexQty; i++ )
        {
            newAdyacentList[i] = new LinkedList<Integer>(  );
        }

        vertexQty = newVertexQty;
        this.vertexObjectList = newList;
        adyacentList = newAdyacentList;
        vertexWithLoop = newLoopList;
    }

    private void subAddEdge( int startVertex, int endVertex )
    {
        if ( startVertex == endVertex )
        {
            vertexWithLoop.set( startVertex, true );
        }

        adyacentList[startVertex].add( endVertex );
        adyacentList[endVertex].add( startVertex );
        edgeQty++;
    }

    public void addEdge( int startVertex, int endVertex )
                 throws Exception
    {
        if ( isInvalidVertex( startVertex ) || isInvalidVertex( endVertex ) )
        {
            throw new Exception( "Using invalid vertex here" );
        } else
        {
            if ( ! subExistEdge( startVertex, endVertex ) )
            {
                subAddEdge( startVertex, endVertex );
            }
        }
    }

    private void subDeleteEdge( int startVertex, int endVertex )
                        throws Exception
    {
        boolean hasDeleted = false;
        int positionFound;

        if ( subExistDirectEdge( startVertex, endVertex ) )
        {
            if ( startVertex == endVertex )
            {
                vertexWithLoop.set( startVertex, false );
            }

            Collections.sort( adyacentList[startVertex] );
            positionFound = Collections.binarySearch( adyacentList[startVertex], endVertex );
            adyacentList[startVertex].remove( positionFound );
            edgeQty--;
            hasDeleted = true;
        }

        if ( subExistDirectEdge( endVertex, startVertex ) )
        {
            Collections.sort( adyacentList[endVertex] );
            positionFound = Collections.binarySearch( adyacentList[endVertex], startVertex );
            adyacentList[endVertex].remove( positionFound );

            if ( ! hasDeleted )
            {
                edgeQty--;
            }
        }
    }

    public void deleteEdge( int startVertex, int endVertex )
                    throws Exception
    {
        if ( isInvalidVertex( startVertex ) || isInvalidVertex( endVertex ) )
        {
            throw new Exception( "Using invalid vertex here" );
        }

        subDeleteEdge( startVertex, endVertex );
    }

    private boolean subExistDirectEdge( int startVertex, int endVertex )
    {
        boolean existEdge = false;

        List<Integer> list = adyacentList[startVertex];

        Iterator<Integer> iterator = list.iterator(  );

        Integer integerTemp;
        boolean foundNumber = false;

        while ( iterator.hasNext(  ) )
        {
            integerTemp = iterator.next(  );

            foundNumber = integerTemp.equals( endVertex );

            if ( foundNumber )
            {
                existEdge = true;

                break;
            }
        }

        if ( existEdge )
        {
            return true;
        } else
        {
            return false;
        }
    }

    private boolean subExistEdge( int startVertex, int endVertex )
    {
        return ( subExistDirectEdge( startVertex, endVertex ) || subExistDirectEdge( endVertex, startVertex ) );
    }

    public boolean existEdge( int startVertex, int endVertex )
                      throws Exception
    {
        if ( isInvalidVertex( startVertex ) || isInvalidVertex( endVertex ) )
        {
            throw new Exception( "Using invalid vertex here" );
        } else
        {
            return subExistEdge( startVertex, endVertex );
        }
    }

    /**
     * because i filled the array list so i dont get the indexout exception for
     * the list in the method get . if there is an object in the and not a T i
     * return null
     *
     * @param vertex
     * @return
     */
    private T subGetVertexObject( int vertex )
                          throws ClassCastException
    {
        Object object = null;
        T t = null;
        object = vertexObjectList.get( vertex );

        if ( object.equals( AdjacentListGraph.object ) )
        {
            return null;
        } else
        {
            return t;
        }
    }

    public T getVertexObject( int vertex )
                      throws Exception
    {
        if ( isInvalidVertex( vertex ) )
        {
            throw new Exception( "Invalid vertex" );
        } else
        {
            return subGetVertexObject( vertex );
        }
    }

    public void setVertexObject( T t, int Vertex )
                         throws Exception
    {
        if ( isInvalidVertex( Vertex ) )
        {
            throw new Exception( "Invalid vertex" );
        } else
        {
            vertexObjectList.set( Vertex, t );
        }
    }

    @Override
    public void addObserver( GraphObserver observer )
    {
        throw new RuntimeException( "Not implemented GUI for this" );
    }

    @Override
    public GraphType getGraphType(  )
    {
        return graphType;
    }

    @Override
    public void setGraphType( GraphType type )
    {
        this.graphType = type;
    }

    @Override
    public void clearObservers(  )
    {
        throw new RuntimeException(  );
    }

    @Override
    public void deleteObserver( GraphObserver graphObserver )
    {
        throw new RuntimeException(  );
    }
}
