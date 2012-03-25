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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import ar.edu.austral.model.implementations.AdjacentListWeightGraph;
import ar.edu.austral.model.interfaces.IWeight;
import ar.edu.austral.model.interfaces.Undirected;
import ar.edu.austral.model.utils.Pair;
import ar.edu.austral.model.utils.enums.ComparatorsUsed;
import ar.edu.austral.model.utils.enums.OriginalPositionCost;

public class MinimumSpanningTreePrim2 {
	private static boolean usingInvalidArgument(IWeight graph, int startVertex)
			throws Exception {

		if (graph == null) {

			throw new Exception("graph is null");
		}

		if (startVertex <= -1) {

			throw new Exception("using invalid vertex here");
		}

		return true;

	}

	/**
	 * 
	 * @param graph
	 * @param startVertex
	 * @return
	 * @throws Exception
	 */
	public static IWeight getMinimumSpanningTree(IWeight graph, int startVertex)
			throws Exception {

		boolean check = MinimumSpanningTreePrim2.usingInvalidArgument(graph,
				startVertex);

		int[] fatherArray = MinimumSpanningTreePrim2.subGetFatherArray(graph,
				startVertex);

		int vertexQty = fatherArray.length;
		IWeight minimumSpanningTree = new AdjacentListWeightGraph(vertexQty);

		int position;
		boolean differentThan;
		List<Integer> listCost;
		int cost;
		for (int i = 0; i < vertexQty; i++) {
			position = fatherArray[i];
			differentThan = (position != -1);
			if (differentThan) {
				listCost = graph.getWeight(i, position);
				cost = Collections.min(listCost);
				minimumSpanningTree.addWeight(i, position, cost);
			}

		}
		return minimumSpanningTree;
	}

	private static int getMinimun(int[] dArray, Set<Integer> indexWhereSearch) {
		int answer = -1;

		int size = dArray.length;
		Iterator<Integer> iterator = indexWhereSearch.iterator();
		Queue<Pair<Integer, Integer>> heapPositionCost = new PriorityQueue(
				size, ComparatorsUsed.getComparatorSecondNumber());

		Integer cost;
		OriginalPositionCost pairPositionCost;
		Integer positionToSearch;
		while (iterator.hasNext()) {

			positionToSearch = iterator.next();
			cost = dArray[positionToSearch];
			pairPositionCost = new OriginalPositionCost(positionToSearch, cost);
			heapPositionCost.offer(pairPositionCost);
		}
		Pair<Integer, Integer> pairPositionMinimumCost = heapPositionCost
				.poll();
		answer = pairPositionMinimumCost.getFirst();

		return answer;
	}

	private static boolean lessThan(Integer o1, Integer o2) {

		int compareState = o1.compareTo(o2);
		return (compareState < 0);

	}

	public static int[] subGetFatherArray(IWeight graph, int startVertex)
			throws Exception {

		Undirected undirected = (Undirected) graph;
		int vertexQty = undirected.getVertexQty();
		int[] answer = new int[vertexQty];

		Set<Integer> setVertex = new TreeSet<Integer>();
		int[] dArray = new int[vertexQty];
		int[] fatherArray = answer;

		for (int i = 0; i < vertexQty; i++) {

			setVertex.add(i);
		}

		for (int i = 0; i < vertexQty; i++) {

			dArray[i] = IWeight.MAX_VALUE;
		}
		for (int i = 0; i < vertexQty; i++) {

			fatherArray[i] = -1;
		}

		dArray[startVertex] = 0;

		int vertexU;
		List<Integer> listAdjacent;
		Iterator<Integer> iteratorVertex;
		Integer otherVertex;
		List<Integer> costUV;
		int minimumCostUV;
		boolean existV;
		boolean uvLessThanD = false;
		while (!setVertex.isEmpty()) {
			vertexU = getMinimun(dArray, setVertex);
			setVertex.remove(vertexU);
			listAdjacent = (undirected).getAdjacentList(vertexU);
			iteratorVertex = listAdjacent.iterator();
			while (iteratorVertex.hasNext()) {
				uvLessThanD = false;
				otherVertex = iteratorVertex.next();
				existV = setVertex.contains(otherVertex);
				if (existV) {
					costUV = graph.getWeight(vertexU, otherVertex);

					minimumCostUV = Collections.min(costUV);
					uvLessThanD = lessThan(minimumCostUV, dArray[otherVertex]);

					if (uvLessThanD) {
						fatherArray[otherVertex] = vertexU;
						dArray[otherVertex] = minimumCostUV;
					}
				}
			}

		}
		answer = fatherArray;
		return answer;
	}

}
