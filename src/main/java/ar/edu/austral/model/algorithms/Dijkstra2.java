/*
 * Copyright (C) 2009 	Almada Emiliano
 * 						Miura Agustín
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import ar.edu.austral.model.implementations.AdjacentListWeightGraph;
import ar.edu.austral.model.interfaces.IGraph;
import ar.edu.austral.model.interfaces.IWeight;

public class Dijkstra2<T> {

	NodoA[] resultado;

	public Dijkstra2(IGraph grafo, int v) throws Exception {
		int nodo_origen = v + 1;
		resultado = resolver(grafo, nodo_origen - 1);
		for (int i = 0; i < resultado.length; i++) {
			System.out.println("Desde v" + nodo_origen + " hasta v" + (i + 1)
					+ ": " + resultado[i].distancia);
			System.out.print("Camino: ");
			for (int j = 0; j < resultado[i].camino.size() - 1; j++) {
				System.out.print("v"
						+ (resultado[i].camino.get(j).intValue() + 1) + " -> ");

			}
			System.out.println("v"
					+ (resultado[i].camino.get(resultado[i].camino.size() - 1)
							.intValue() + 1) + "\n");
		}

	}

	public NodoA[] getResult() {

		return resultado;

	}

	private NodoA[] resolver(IGraph grafo, int nodo_origen) throws Exception {

		int n = grafo.getVertexQty();
		boolean[] visitados = new boolean[n];

		NodoA[] retorno = new NodoA[n];
		for (int i = 0; i < retorno.length; i++)
			retorno[i] = new NodoA(nodo_origen, 0);

		Stack<NodoA> soluciones = new Stack<NodoA>();
		soluciones.push(new NodoA(nodo_origen, 0));
		while (!soluciones.isEmpty()) {
			NodoA origen = soluciones.pop();
			for (int i = 0; i < soluciones.size(); i++)
				if (origen.distancia > soluciones.get(i).distancia)
					origen = soluciones.get(i);
			soluciones.remove(origen);

			visitados[origen.nodo] = true;
			for (int i = 0; i < n; i++) {
				if (i == 5) {
					System.out.println("jajaa");
				}
				Integer in = getMinWeight(grafo, origen.nodo, i);
				if (visitados[i] || in < 0)
					continue;
				if ((in + origen.distancia) < retorno[i].distancia
						|| retorno[i].distancia == 0) {
					retorno[i] = new NodoA(i, (in + origen.distancia), origen);
					soluciones.push(new NodoA(i, retorno[i].distancia, origen));
				}
			}
		}
		return retorno;
	}

	public Integer getMinWeight(IGraph<T> grafo, int origen, int destino)
			throws Exception {

		IWeight w = (IWeight) grafo;
		List temp = w.getWeight(origen, destino);
		Integer in = new Integer(-1);

		for (int j = 0; j < temp.size(); j++) {
			Integer cost = (Integer) temp.get(j);
			if (cost.compareTo(in) < 0 || in < 0) {
				in = cost;
			}
		}
		return in;
	}
}
