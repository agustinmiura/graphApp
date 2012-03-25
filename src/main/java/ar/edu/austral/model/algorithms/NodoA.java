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

import java.util.LinkedList;

public class NodoA
{
    int nodo;
    public int distancia;
    public LinkedList<Integer> camino;

    NodoA( int nodo, int distancia, NodoA anterior )
    {
        this.nodo = nodo;
        this.distancia = distancia;
        camino = (LinkedList<Integer>) anterior.camino.clone(  );
        camino.add( new Integer( nodo ) );
    }

    NodoA( int nodo, int distancia )
    {
        this.nodo = nodo;
        this.distancia = distancia;
        camino = new LinkedList<Integer>(  );
        camino.add( new Integer( nodo ) );
    }
}
