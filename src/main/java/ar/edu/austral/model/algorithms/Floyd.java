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

import ar.edu.austral.model.interfaces.Directed;
import ar.edu.austral.model.interfaces.IWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Floyd
{
    public static <T> List<int[][]> floyd( Directed<T> directed )
                                   throws Exception
    {
        return ( subFloyd( directed ) );
    }

    public static List<Integer> decodeWay( int startVertex, int endVertex, int[][] floydSolution )
    {
        List<Integer> way = new ArrayList<Integer>(  );
        Queue<Integer> queue = new LinkedList<Integer>(  );
        queue.offer( startVertex );

        int vertexToGo = endVertex;
        int currentVertex;

        while ( vertexToGo != -1 )
        {
            currentVertex = floydSolution[startVertex][vertexToGo];

            if ( currentVertex == -1 )
            {
                vertexToGo = -1;
                queue.offer( endVertex );
            } else
            {
                queue.offer( currentVertex );
                vertexToGo = currentVertex;
            }
        }

        while ( ! queue.isEmpty(  ) )
        {
            way.add( queue.poll(  ) );
        }

        return way;
    }

    /**
     *
     * @param <T>
     * @param directed
     * @return
     * @throws Exception
     */
    private static <T> List<int[][]> subFloyd( Directed<T> directed )
                                       throws Exception
    {
        IWeight iWeight;
        iWeight = (IWeight) directed;

        int[][] answer = null;
        int[][] calculateMatrix = null;
        int vertexQty = directed.getVertexQty(  );

        calculateMatrix = new int[vertexQty][vertexQty];
        answer = new int[vertexQty][vertexQty];

        int minimumCost;
        boolean existEdge;
        List<Integer> listCost;

        for ( int i = 0; i < vertexQty; i++ )
        {
            for ( int j = 0; j < vertexQty; j++ )
            {
                listCost = iWeight.getWeight( i, j );

                if ( ! listCost.isEmpty(  ) )
                {
                    minimumCost = Collections.min( listCost );
                } else
                {
                    minimumCost = IWeight.MAX_VALUE;
                }

                calculateMatrix[i][j] = minimumCost;
            }
        }

        for ( int i = 0; i < vertexQty; i++ )
        {
            calculateMatrix[i][i] = 0;
        }

        for ( int i = 0; i < vertexQty; i++ )
        {
            for ( int j = 0; j < vertexQty; j++ )
            {
                answer[i][j] = -1;
            }
        }

        int state = -1;

        int step;
        int startVertex;
        int endVertex;

        int directCost;
        int indirectCost;

        for ( step = 0; step < vertexQty; step++ )
        {
            for ( startVertex = 0; startVertex < vertexQty; startVertex++ )
            {
                for ( endVertex = 0; endVertex < vertexQty; endVertex++ )
                {
                    directCost = calculateMatrix[startVertex][endVertex];
                    indirectCost = calculateMatrix[startVertex][step] + calculateMatrix[step][endVertex];

                    if ( indirectCost < directCost )
                    {
                        answer[startVertex][endVertex] = step;
                        calculateMatrix[startVertex][endVertex] = indirectCost;
                    }
                }
            }
        }

        state = -1;

        List<int[][]> answerList = new ArrayList(  );
        answerList.add( calculateMatrix );
        answerList.add( answer );

        return answerList;
    }
}
