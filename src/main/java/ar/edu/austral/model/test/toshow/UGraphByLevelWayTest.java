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
package ar.edu.austral.model.test.toshow;

import ar.edu.austral.model.algorithms.UGraphByLevelWay;
import ar.edu.austral.model.implementations.AdjacentListWeightGraph;
import ar.edu.austral.model.implementations.AdjacentMatrixGraph;
import ar.edu.austral.model.interfaces.IWeight;
import ar.edu.austral.model.interfaces.Undirected;

import java.util.List;

public class UGraphByLevelWayTest
{
    private static void doTest2(  )
    {
        try
        {
            IWeight undirected = new AdjacentListWeightGraph( 7 );

            undirected.addWeight( 0, 1, 10 );
            undirected.addWeight( 1, 4, 10 );

            undirected.addWeight( 4, 6, 10 );
            undirected.addWeight( 4, 5, 10 );

            undirected.addWeight( 5, 3, 10 );
            undirected.addWeight( 6, 3, 10 );

            undirected.addWeight( 0, 3, 10 );
            undirected.addWeight( 3, 2, 10 );

            int state = -1;

            List<Integer>[] listArray = UGraphByLevelWay.getByLevelWayUW( undirected, 0 );

            state = -1;
        } catch ( Exception e )
        {
            e.printStackTrace(  );
        }
    }

    private static void doTest1(  )
    {
        try
        {
            Undirected<String> undirected = new AdjacentMatrixGraph( 7 );
            int state = -1;

            List<Integer>[] listArray = UGraphByLevelWay.getByLevelWay( undirected, 0 );

            state = -1;
        } catch ( Exception e )
        {
            e.printStackTrace(  );
        }
    }

    private static void doTest0(  )
    {
        try
        {
            Undirected<String> undirected = new AdjacentMatrixGraph( 7 );
            undirected.addEdge( 0, 1 );
            undirected.addEdge( 1, 4 );

            undirected.addEdge( 4, 6 );
            undirected.addEdge( 4, 5 );

            undirected.addEdge( 5, 3 );
            undirected.addEdge( 6, 3 );

            undirected.addEdge( 0, 3 );
            undirected.addEdge( 3, 2 );

            int state = -1;

            List<Integer>[] listArray = UGraphByLevelWay.getByLevelWay( undirected, 0 );

            state = -1;
        } catch ( Exception e )
        {
            e.printStackTrace(  );
        }
    }
}
