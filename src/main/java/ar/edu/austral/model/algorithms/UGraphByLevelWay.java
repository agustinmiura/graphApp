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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ar.edu.austral.model.implementations.AdjacentListWeightGraph;
import ar.edu.austral.model.implementations.AdjacentMatrixGraph;
import ar.edu.austral.model.interfaces.Directed;
import ar.edu.austral.model.interfaces.IGraph;
import ar.edu.austral.model.interfaces.IWeight;
import ar.edu.austral.model.interfaces.Undirected;

/**
 * recorrido en anchura del grafo sirve para grafos , no dirigidos , y con peso
 * 
 * se debe testear esto
 * 
 * @author user1_2 tested functions ok this
 * 
 * 
 */
public class UGraphByLevelWay {

	public UGraphByLevelWay() {
		// testing this

	}

	public static List<Integer>[] getByLevelWayUW(IWeight weight, int vertex)
			throws Exception {

		Undirected undirected;

		try {
			undirected = (Undirected) weight;

			return UGraphByLevelWay.getByLevelWay(undirected, vertex);

		} catch (ClassCastException e) {
			throw new Exception("User must provide a IWeight "
					+ "that implements Undirected");
		}

	}

	public static List<Integer>[] getByLevelWay(Undirected graph, int vertex)
			throws Exception {

		UGraphByLevelWay byLevelWay = new UGraphByLevelWay();

		return byLevelWay.byLevelWay(graph, vertex);

	}

	public List<Integer>[] byLevelWay(Undirected graph, int vertex)
			throws Exception {

		if (invalidParameters(graph, vertex)) {

			throw new Exception("graph null or invalid vertex");
		}

		List<Integer> listTemp;
		int vertexQty = graph.getVertexQty();
		boolean[] visited = new boolean[vertexQty];
		Queue<List<Integer>> queue = new LinkedList<List<Integer>>();
		int qty = 0;
		int iterations = 0;
		for (int i = vertex; iterations < vertexQty; iterations++) {

			listTemp = subByLevelWayConnectedComponent(graph, i, visited);
			if (!listTemp.isEmpty()) {

				queue.offer(listTemp);
				qty++;
			}
			(i) = (i + 1) % (vertexQty);
		}

		Iterator<List<Integer>> iterator = queue.iterator();
		int index = -1;
		List<Integer>[] answer = (new List[qty]);
		while (iterator.hasNext()) {
			index++;
			answer[index] = queue.poll();

		}

		return answer;

	}

	private boolean invalidParameters(Undirected graph, int vertex) {

		if (graph == null || vertex < 0 || vertex >= graph.getVertexQty()) {

			return true;
		} else {

			return false;
		}

	}

	private List<Integer> subByLevelWayConnectedComponent(Undirected graph,
			int vertex, boolean[] visited) throws Exception {
		List<Integer> answer = new LinkedList<Integer>();

		if (visited[vertex]) {

			return answer;
		}
		boolean state;
		List<Integer> tempList;
		Queue<Integer> queue = new LinkedList<Integer>();
		Integer vertexTemp;
		state = queue.offer(vertex);
		Iterator<Integer> iterator;
		Integer integerTemp;
		if (!state) {

			throw new Exception("Cant insert element in queue what happened");
		}

		while (!queue.isEmpty()) {
			vertexTemp = queue.peek();
			queue.remove();

			if (visited[vertexTemp]) {

				continue;
			}
			answer.add(vertexTemp);
			visited[vertexTemp] = true;
			tempList = graph.getAdjacentList(vertexTemp);

			iterator = tempList.iterator();

			while (iterator.hasNext()) {

				integerTemp = iterator.next();

				if (!visited[integerTemp]) {

					queue.offer(integerTemp);
				}

			}
		}
		return answer;

	}

	private List<Integer> byLevelWayConnectedComponent(Undirected graph,
			int vertex, boolean[] visited) throws Exception {

		List<Integer> answer = null;

		if (invalidParameters(graph, vertex)) {

			throw new Exception("null graph or invalid vertex");
		}

		return answer = subByLevelWayConnectedComponent(graph, vertex, visited);

	}

}
