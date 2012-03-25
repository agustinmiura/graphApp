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

import ar.edu.austral.model.interfaces.IGraph;
public class MatrixWarshall {
	private static final MatrixWarshall MATRIX_WARSHALL = new MatrixWarshall();

	public static boolean[][] getAdjacentMatrix(IGraph graph) throws Exception {
		return (MATRIX_WARSHALL.subGetAdyacentMatrix(graph));
	}
	public static boolean[][] getWarshallMatrix(IGraph graph) throws Exception {

		if (graph == null) {

			throw new Exception("cant use null parameters");
		} else {

			return (MATRIX_WARSHALL.subGetMatrixWarshall(graph));
		}

	}

	private boolean[][] subGetAdyacentMatrix(IGraph graph) throws Exception {
		IGraph undirected = graph;
		int vertexQty = undirected.getVertexQty();
		boolean[][] answer = new boolean[vertexQty][vertexQty];

		for (int i = 0; i < vertexQty; i++) {
			for (int j = 0; j < vertexQty; j++) {
				if (undirected.existEdge(i, j)) {
					answer[i][j] = true;
				}

			}
		}

		return answer;
	}

	private boolean[][] getAdyacentMatrix(IGraph graph) throws Exception {

		if (graph == null) {

			throw new Exception("cant use null graph for warshall");
		}

		return (subGetAdyacentMatrix(graph));

	}

	private boolean[][] subGetMatrixWarshall(IGraph graph) throws Exception {
		boolean[][] matrix = subGetAdyacentMatrix(graph);
		int vertexQty = graph.getVertexQty();
		for (int k = 0; k < vertexQty; k++) {
			for (int i = 0; i < vertexQty; i++) {
				for (int j = 0; j < vertexQty; j++) {

					if (matrix[i][k] && matrix[k][j]) {

						matrix[i][j] = true;
					}
				}
			}
		}

		return matrix;
	}
}
