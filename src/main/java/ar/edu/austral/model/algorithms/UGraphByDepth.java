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
package ar.edu.austral.model.algorithms;

import ar.edu.austral.model.interfaces.IWeight;
import ar.edu.austral.model.interfaces.Undirected;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class UGraphByDepth
{
    public UGraphByDepth(  )
    {
    }

    public static List<Integer>[] getByDepthWayUW( IWeight weight, int vertex )
                                           throws Exception
    {
        Undirected undirected;

        try
        {
            undirected = (Undirected) weight;

            return UGraphByLevelWay.getByLevelWay( undirected, vertex );
        } catch ( ClassCastException e )
        {
            throw new Exception( "User must provide a IWeight " + "that implements Undirected" );
        }
    }

    public static List<Integer>[] getByDepthlWay( Undirected graph, int vertex )
                                          throws Exception
    {
        UGraphByLevelWay byLevelWay = new UGraphByLevelWay(  );

        return byLevelWay.byLevelWay( graph, vertex );
    }

    public List<Integer>[] byDepthWay( Undirected graph, int vertex )
                               throws Exception
    {
        if ( invalidParameters( graph, vertex ) )
        {
            throw new Exception( "graph null or invalid vertex" );
        }

        List<Integer> listTemp;
        int vertexQty = graph.getVertexQty(  );
        boolean[] visited = new boolean[vertexQty];

        Queue<List<Integer>> queue = new LinkedList<List<Integer>>(  );
        Object objectTemp;

        int qty = 0;
        int iterations = 0;

        for ( int i = vertex; iterations < vertexQty; iterations++ )
        {
            listTemp = subByDepthWayConnectedComponent( graph, i, visited );

            if ( ! listTemp.isEmpty(  ) )
            {
                queue.offer( listTemp );
                qty++;
            }

            ( i ) = ( i + 1 ) % ( vertexQty );
        }

        Iterator<List<Integer>> iterator = queue.iterator(  );
        int index = -1;
        List<Integer>[] answer = ( new List[qty] );

        while ( iterator.hasNext(  ) )
        {
            index++;
            answer[index] = queue.poll(  );
        }

        return answer;
    }

    private boolean invalidParameters( Undirected graph, int vertex )
    {
        if ( ( graph == null ) || ( vertex < 0 ) || ( vertex >= graph.getVertexQty(  ) ) )
        {
            return true;
        } else
        {
            return false;
        }
    }

    private List<Integer> subByDepthWayConnectedComponent( Undirected graph, int vertex, boolean[] visited )
                                                   throws Exception
    {
        List<Integer> answer = new LinkedList<Integer>(  );

        if ( visited[vertex] )
        {
            return answer;
        }

        boolean state;
        List<Integer> tempList;
        Stack<Integer> stack = new Stack<Integer>(  );
        Integer vertexTemp;
        Object tempObject;
        tempObject = stack.push( vertex );

        Iterator<Integer> iterator;
        Integer integerTemp;

        while ( ! stack.isEmpty(  ) )
        {
            vertexTemp = stack.pop(  );

            if ( visited[vertexTemp] )
            {
                continue;
            }

            answer.add( vertexTemp );
            visited[vertexTemp] = true;
            tempList = graph.getAdjacentList( vertexTemp );

            iterator = tempList.iterator(  );

            while ( iterator.hasNext(  ) )
            {
                integerTemp = iterator.next(  );

                if ( ! visited[integerTemp] )
                {
                    stack.push( integerTemp );
                }
            }
        }

        return answer;
    }

    private List<Integer> byDepthWayConnectedComponent( Undirected graph, int vertex, boolean[] visited )
                                                throws Exception
    {
        List<Integer> answer = null;

        if ( invalidParameters( graph, vertex ) )
        {
            throw new Exception( "null graph or invalid vertex" );
        }

        return answer = subByDepthWayConnectedComponent( graph, vertex, visited );
    }
}
