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

import ar.edu.austral.model.interfaces.IWeight;
import ar.edu.austral.model.interfaces.Undirected;
import ar.edu.austral.model.utils.Pair;
import ar.edu.austral.model.utils.VertexCost;
import ar.edu.austral.model.utils.enums.GraphType;
import ar.edu.austral.model.utils.enums.UndirectedConstants;
import ar.edu.austral.view.gui.GraphObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AdjacentListWeightGraph<T>
    implements Undirected<T>,
               IWeight
{
    private int vertexQty;
    private int edgeQty;
    private List<T> vertexObjectList;
    private Comparator<Pair<Integer, Integer>> comparatorPair;
    private Comparator<Pair<Integer, Integer>> comparatorPairWeight;
    private Comparator<Integer> comparatorInteger;
    private static final Object OBJECT = new Object(  );
    private static final Integer ZERO = new Integer( 0 );
    private static Pair<Integer, Integer> ZERO_PAIR;
    private boolean classHasBeenInstantiated;

    // first is vertex second is cost
    private List<Pair<Integer, Integer>[]> adjacentList;
    private List<Boolean> vertexWithLoop;
    private GraphType graphType;
    private List<GraphObserver> graphObserverList;

    private void assingComparators(  )
    {
        comparatorPair = new ComparatorPair(  );
        comparatorInteger = new ComparatorInteger(  );
        comparatorPairWeight = new ComparatorPairWeight(  );
        ZERO_PAIR = UndirectedConstants.getPairZero(  );
    }

    private void subConstructor( int vertexQty )
    {
        // solve this after
        this.vertexQty = vertexQty;
        edgeQty = 0;

        vertexObjectList = new ArrayList<T>( vertexQty );
        vertexWithLoop = new ArrayList<Boolean>( vertexQty );
        adjacentList = new List[vertexQty];

        /*
         * fill the object list to avoid the use exception in the method set(int
         * index,OBejct object)
         */
        for ( int i = 0; i < vertexQty; i++ )
        {
            vertexObjectList.add( (T) AdjacentListWeightGraph.OBJECT );
        }

        //
        for ( int i = 0; i < vertexQty; i++ )
        {
            vertexWithLoop.add( false );
        }

        //
        for ( int i = 0; i < vertexQty; i++ )
        {
            adjacentList[i] = new LinkedList<Pair<Integer, Integer>>(  );
        }

        /*
         * private GraphType graphType; private List<GraphType> graphTypeList;
         */
        graphType = GraphType.UNDIRECTED;
        this.graphObserverList = new ArrayList( 20 );
    }

    public AdjacentListWeightGraph( int vertexQty )
                            throws Exception
    {
        if ( vertexQty <= 0 )
        {
            throw new Exception( "Invalid vertex qty" );
        } else
        {
            subConstructor( vertexQty );
            assingComparators(  );
        }
    }

    private class ComparatorPairWeight
        implements Comparator<Pair<Integer, Integer>>
    {
        public int compare( Pair<Integer, Integer> o1, Pair<Integer, Integer> o2 )
        {
            Integer firstWeight = o1.getSecond(  );
            Integer secondWeight = o2.getSecond(  );

            return ( firstWeight - secondWeight );
        }
    }

    private class ComparatorPair
        implements Comparator<Pair<Integer, Integer>>
    {
        public int compare( Pair<Integer, Integer> arg0, Pair<Integer, Integer> arg1 )
        {
            return ( arg0.getFirst(  ) - arg1.getFirst(  ) );
        }
    }

    private class ComparatorInteger
        implements Comparator<Integer>
    {
        public int compare( Integer arg0, Integer arg1 )
        {
            return ( arg0 - arg1 );
        }
    }

    private boolean isInvalidVertex( int vertex )
    {
        return ( ( vertex < 0 ) || ( vertex >= vertexQty ) );
    }

    private List<Integer> subGetAdjacentList( int vertex )
    {
        List<Integer> adjacentListAnswer = new ArrayList<Integer>( vertexQty );

        Iterator<Pair<Integer, Integer>> iterator = adjacentList[vertex].iterator(  );

        Integer tempInteger;

        Pair<Integer, Integer> tempPair;

        while ( iterator.hasNext(  ) )
        {
            tempPair = iterator.next(  );
            tempInteger = tempPair.getFirst(  );

            adjacentListAnswer.add( tempInteger );
        }

        Collections.sort( adjacentListAnswer );

        int position = Collections.binarySearch( adjacentListAnswer, vertex );

        if ( position >= 0 )
        {
            adjacentListAnswer.removeAll( Collections.singleton( new Integer( vertex ) ) );
        }

        return adjacentListAnswer;
    }

    /**
     * dont return the list with the loops here
     *
     */
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
        int otherVertexQty = vertexQty;

        subConstructor( otherVertexQty );

        Iterator<GraphObserver> iterator = graphObserverList.iterator(  );

        GraphObserver graphObserver;

        while ( iterator.hasNext(  ) )
        {
            graphObserver = iterator.next(  );
            graphObserver.graphEmpty(  );
        }
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

    private void subDeleteVertex( int vertexToDelete )
                          throws Exception
    {
        int oldVertexQty = vertexQty;

        List<Pair<Integer, Integer>> tempList;

        for ( int i = vertexToDelete + 1; ( i >= ( vertexToDelete + 1 ) ) && ( i < oldVertexQty ); i++ )
        {
            tempList = adjacentList[i];
            adjacentList[i - 1] = tempList;
        }

        adjacentList[oldVertexQty - 1] = null;

        vertexQty--;
    }

    private void subDeleteVertexObjectList( int vertexToDelete )
                                    throws Exception
    {
        int oldVertexQty = vertexQty;

        // overwrite the object in the the adyacent list
        T tempT;

        for ( int i = vertexToDelete + 1; ( i >= ( vertexToDelete + 1 ) ) && ( i < oldVertexQty ); i++ )
        {
            tempT = vertexObjectList.get( i );
            vertexObjectList.set( ( i - 1 ), tempT );
        }

        vertexObjectList.set( oldVertexQty - 1, null );

        // dont use the last object;
    }

    private void decreaseVertexInAdyacentList( int vertex )
                                       throws Exception
    {
        List listPair;
        Iterator<Pair<Integer, Integer>> iterator;
        List<Pair<Integer, Integer>> tempList = new LinkedList<Pair<Integer, Integer>>(  );
        Pair<Integer, Integer> pair;
        Integer tempVertex;
        Integer tempCost;
        Pair<Integer, Integer> otherPair;

        for ( int i = 0; i < vertexQty; i++ )
        {
            tempList.clear(  );

            listPair = adjacentList[i];

            iterator = listPair.iterator(  );

            while ( iterator.hasNext(  ) )
            {
                pair = iterator.next(  );

                if ( pair.equalsTo( AdjacentListWeightGraph.ZERO_PAIR ) )
                {
                    tempList.add( pair );

                    continue;
                } else
                {
                    tempVertex = pair.getFirst(  ) - 1;
                    tempCost = pair.getSecond(  );
                    otherPair = new VertexCost( tempVertex, tempCost );

                    tempList.add( otherPair );
                }
            }

            listPair.clear(  );
            listPair.addAll( tempList );
        }
    }

    /**
     * this graph can only contain loops with zero cost otherwise explodes
     *
     * @throws Exception
     */
    private void resetVertexWithLoop(  )
                              throws Exception
    {
        int position;
        List<Pair<Integer, Integer>> listPair;
        Pair<Integer, Integer> tempPair;

        for ( int i = 0; i < vertexQty; i++ )
        {
            listPair = adjacentList[i];
            tempPair = new VertexCost( new Integer( i ),
                                       AdjacentListWeightGraph.ZERO );
            listPair.removeAll( Collections.singleton( tempPair ) );
        }

        for ( int i = 0; i < vertexQty; i++ )
        {
            if ( this.vertexWithLoop.get( i ) )
            {
                tempPair = new VertexCost( new Integer( i ),
                                           AdjacentListWeightGraph.ZERO );
                adjacentList[i].add( tempPair );
            }
        }
    }

    public void deleteVertex( int vertexToDelete )
                      throws Exception
    {
        boolean vertexHasAdjacent = ( ! adjacentList[vertexToDelete].isEmpty(  ) );

        if ( isInvalidVertex( vertexToDelete ) || ( vertexHasAdjacent ) )
        {
            throw new Exception( "bad vertex to delete" );
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

            System.out.println( "in model direct graph with weight notifying the obsevers" );

            Iterator<GraphObserver> iterator = graphObserverList.iterator(  );

            int count = -1;

            while ( iterator.hasNext(  ) )
            {
                count++;
                System.out.println( "size" + count );

                iterator.next(  ).vertexDeleted( vertexToDelete );
            }
        }
    }

    public void addVertex( T t )
    {
        int oldVertexQty = vertexQty;
        List<T> oldList = vertexObjectList;
        List<Pair<Integer, Integer>[]> oldAdyacentList = adjacentList;
        List<Boolean> oldVertexWithLoop = vertexWithLoop;

        int newVertexQty = vertexQty + 1;
        List<T> newList = new ArrayList<T>( newVertexQty );
        List<Pair<Integer, Integer>[]> newAdyacentList = new LinkedList[newVertexQty];
        List<Boolean> newVertexWithLoop = new ArrayList<Boolean>( newVertexQty );

        newList.addAll( oldList );
        newList.add( t );

        newVertexWithLoop.addAll( oldVertexWithLoop );
        newVertexWithLoop.add( false );

        for ( int i = 0; i < oldVertexQty; i++ )
        {
            newAdyacentList[i] = oldAdyacentList[i];
        }

        for ( int i = oldVertexQty; i < newVertexQty; i++ )
        {
            newAdyacentList[i] = new LinkedList<Pair<Integer, Integer>>(  );
        }

        vertexQty = newVertexQty;
        this.vertexObjectList = newList;
        adjacentList = newAdyacentList;
        vertexWithLoop = newVertexWithLoop;

        Iterator<GraphObserver> iterator = graphObserverList.iterator(  );

        int count = -1;

        while ( iterator.hasNext(  ) )
        {
            count++;
            System.out.println( count );
            iterator.next(  ).vertexAdded(  );
        }
    }

    private void subAddEdge( int startVertex, int endVertex )
                     throws Exception
    {
        if ( startVertex == endVertex )
        {
            vertexWithLoop.set( startVertex, true ); // put the cycle here
        }

        Pair<Integer, Integer> pair;

        pair = new VertexCost( endVertex, 0 );
        adjacentList[startVertex].add( pair );

        pair = new VertexCost( startVertex, 0 );
        adjacentList[endVertex].add( pair );
    }

    public void addEdge( int startVertex, int endVertex )
                 throws Exception
    {
        throw new RuntimeException( "Not implemented the method use weight" );
    }

    private boolean subExistDirectEdge( int startVertex, int endVertex )
    {
        boolean existEdge = false;

        List<Pair<Integer, Integer>> list = adjacentList[startVertex];

        Iterator<Pair<Integer, Integer>> iterator = list.iterator(  );

        Integer integerTemp;
        boolean foundNumber = false;
        Pair<Integer, Integer> pair;

        while ( iterator.hasNext(  ) )
        {
            pair = iterator.next(  );

            integerTemp = pair.getFirst(  );

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
     * get the qty of the vertex deleted
     *
     * @param list
     * @param vertexToDelete
     * @return
     * @throws Exception
     */
    private int vertexDelete( List<Pair<Integer, Integer>> list, Integer vertexToDelete )
                      throws Exception
    {
        int qty = 0;
        Pair<Integer, Integer> pairToDelete = new VertexCost( vertexToDelete, 0 );
        Pair<Integer, Integer> otherPair;

        Iterator<Pair<Integer, Integer>> iterator = list.iterator(  );

        boolean foundVertex;

        while ( iterator.hasNext(  ) )
        {
            otherPair = iterator.next(  );

            foundVertex = pairToDelete.equalsTo( otherPair );

            if ( foundVertex )
            {
                iterator.remove(  );
                qty++;
            }
        }

        return qty;
    }

    private void subDeleteEdge( int startVertex, int endVertex )
                        throws Exception
    {
        if ( startVertex == endVertex )
        {
            vertexWithLoop.set( startVertex, false );
        }

        int qty = 0;
        int positionFound;
        int tempInt;

        if ( subExistDirectEdge( startVertex, endVertex ) )
        {
            qty = vertexDelete( adjacentList[startVertex], endVertex );
        }

        if ( subExistDirectEdge( endVertex, startVertex ) )
        {
            tempInt = vertexDelete( adjacentList[endVertex], startVertex );
        }

        edgeQty = edgeQty - qty;
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

    private T subGetVertexObject( int vertex )
    {
        Object object = vertexObjectList.get( vertex );
        T t = null;

        if ( object.equals( AdjacentListWeightGraph.OBJECT ) )
        {
            return t;
        } else
        {
            return (T) ( object );
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

    private List<Integer> subGetWeightList( int startVertex, int endVertex )
                                    throws Exception
    {
        List<Integer> answer = new LinkedList<Integer>(  );
        List<Pair<Integer, Integer>> pairList = adjacentList[startVertex];
        Iterator<Pair<Integer, Integer>> iterator = pairList.iterator(  );

        Pair<Integer, Integer> pairVertexCost;
        int weight;

        while ( iterator.hasNext(  ) )
        {
            pairVertexCost = iterator.next(  );
            weight = pairVertexCost.getSecond(  );
            answer.add( weight );
        }

        return answer;
    }

    private boolean isIntegerEquals( Integer o1, Integer o2 )
    {
        int compareState = o1.compareTo( o2 );

        return ( compareState == 0 );
    }

    private List<Integer> subGetWeight( int startVertex, int endVertex )
                                throws Exception
    {
        List<Integer> answer = new LinkedList<Integer>(  );

        Iterator<Pair<Integer, Integer>> iteratorPair;
        Integer otherVertex;
        Integer cost;
        boolean foundVertex;
        Pair<Integer, Integer> pair;

        iteratorPair = adjacentList[startVertex].iterator(  );

        while ( iteratorPair.hasNext(  ) )
        {
            pair = iteratorPair.next(  );
            otherVertex = pair.getFirst(  );
            cost = pair.getSecond(  );
            foundVertex = isIntegerEquals( endVertex, otherVertex );

            if ( foundVertex )
            {
                answer.add( cost );
            }
        }

        return answer;
    }

    public List<Integer> getWeight( int startVertex, int endVertex )
                            throws Exception
    {
        if ( isInvalidVertex( startVertex ) || isInvalidVertex( endVertex ) )
        {
            throw new Exception( "bad vertex used" );
        }

        return subGetWeight( startVertex, endVertex );
    }

    private boolean subFoundWeight( int startVertex, int endVertex, int weight )
                            throws Exception
    {
        List<Pair<Integer, Integer>> listWeight = subGetAdjcentListWithWeight( startVertex );
        Pair<Integer, Integer> pairToSearch = new VertexCost( endVertex, weight );
        int position = listWeight.indexOf( pairToSearch );

        return ( position >= 0 );
    }

    private int subFoundWeightIndex( int startVertex, int endVertex, int weight )
                             throws Exception
    {
        List<Pair<Integer, Integer>> listWeight = subGetAdjcentListWithWeight( startVertex );
        Pair<Integer, Integer> pairToSearch = new VertexCost( endVertex, weight );
        int position = listWeight.indexOf( pairToSearch );

        return position;
    }

    private List<Pair<Integer, Integer>> subGetAdjcentListWithWeight( int vertex )
    {
        List<Pair<Integer, Integer>> adjacentListAnswer = new ArrayList<Pair<Integer, Integer>>( vertexQty );

        Iterator<Pair<Integer, Integer>> iterator = adjacentList[vertex].iterator(  );

        Pair<Integer, Integer> tempPair;

        while ( iterator.hasNext(  ) )
        {
            tempPair = iterator.next(  );

            adjacentListAnswer.add( tempPair );
        }

        return adjacentListAnswer;
    }

    public boolean foundWeight( int startVertex, int endVertex, int weight )
                        throws Exception
    {
        if ( isInvalidVertex( startVertex ) || isInvalidVertex( endVertex ) )
        {
            throw new Exception( "Bad vertex used " );
        }

        return ( subFoundWeight( startVertex, endVertex, weight ) );
    }

    private void subSetWeight( int startVertex, int endVertex, int weight, int newWeight )
    {
        throw new RuntimeException( "not implemented this" );
    }

    public void setWeight( int startVertex, int endVertex, int weight, int newWeight )
                   throws Exception
    {
        throw new RuntimeException( "not implemented this" );
    }

    private void subAddWeight( int startVertex, int endVertex, int weight )
                       throws Exception
    {
        Pair<Integer, Integer> pair0 = new VertexCost( endVertex, weight );
        Pair<Integer, Integer> pair1 = new VertexCost( startVertex, weight );

        adjacentList[startVertex].add( pair0 );
        adjacentList[endVertex].add( pair1 );
        edgeQty++;
    }

    public void addWeight( int startVertex, int endVertex, int weight )
                   throws Exception
    {
        if ( isInvalidVertex( startVertex ) || isInvalidVertex( endVertex ) )
        {
            throw new Exception( "Invalid vertex" );
        } else if ( subFoundWeight( startVertex, endVertex, weight ) )
        {
            throw new Exception( "vertex already added not necessary" );
        }

        subAddWeight( startVertex, endVertex, weight );

        Iterator<GraphObserver> iterator = graphObserverList.iterator(  );

        while ( iterator.hasNext(  ) )
        {
            iterator.next(  ).weightAdded( startVertex, endVertex, weight );
        }
    }

    private void subDeleteWeight( int startVertex, int endVertex, int weight )
    {
        boolean foundWeight;
        Iterator<Pair<Integer, Integer>> iterator;
        Pair<Integer, Integer> tempPair;
        List<Pair<Integer, Integer>> listPair;

        listPair = adjacentList[startVertex];
        iterator = listPair.iterator(  );

        Integer weightTemp;

        while ( iterator.hasNext(  ) )
        {
            tempPair = iterator.next(  );
            weightTemp = tempPair.getSecond(  );
            foundWeight = weightTemp.equals( weight );

            if ( foundWeight )
            {
                iterator.remove(  );
            }
        }

        listPair = adjacentList[endVertex];
        iterator = listPair.iterator(  );

        while ( iterator.hasNext(  ) )
        {
            tempPair = iterator.next(  );
            weightTemp = tempPair.getSecond(  );
            foundWeight = weightTemp.equals( weight );

            if ( foundWeight )
            {
                iterator.remove(  );
            }
        }

        edgeQty--;
    }

    public void deleteWeight( int startVertex, int endVertex, int weight )
                      throws Exception
    {
        if ( isInvalidVertex( startVertex ) || isInvalidVertex( endVertex ) )
        {
            throw new Exception( "Invalid vertex" );
        } else if ( ! subFoundWeight( startVertex, endVertex, weight ) )
        {
            throw new Exception( "vertex not added cant do that" );
        }

        subDeleteWeight( startVertex, endVertex, weight );

        Iterator<GraphObserver> iterator = graphObserverList.iterator(  );

        while ( iterator.hasNext(  ) )
        {
            iterator.next(  ).weightDeleted( startVertex, endVertex, weight );
        }
    }

    @Override
    public void addObserver( GraphObserver observer )
    {
        if ( observer == null )
        {
            throw new NullPointerException( "Null parameter " );
        } else
        {
            this.graphObserverList.add( observer );
        }
    }

    @Override
    public GraphType getGraphType(  )
    {
        return GraphType.UNDIRECTED;
    }

    @Override
    public void setGraphType( GraphType type )
    {
    }

    @Override
    public void clearObservers(  )
    {
        graphObserverList.clear(  );
    }

    @Override
    public void deleteObserver( GraphObserver graphObserver )
    {
        boolean contains = graphObserverList.contains( graphObserver );

        if ( contains )
        {
            graphObserverList.remove( Collections.singleton( graphObserver ) );
        }
    }
}
