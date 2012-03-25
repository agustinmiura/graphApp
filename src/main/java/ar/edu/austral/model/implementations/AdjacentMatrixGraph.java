/**
 * 
 */
package ar.edu.austral.model.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.edu.austral.model.interfaces.Undirected;
import ar.edu.austral.model.utils.enums.GraphType;
import ar.edu.austral.view.gui.GraphObserver;
public class AdjacentMatrixGraph<T> implements Undirected<T> {

	private boolean[][] matrix;

	private int vertexQty;

	private int edgeQty;

	private List<T> vertexObjectList;

	private boolean isInvalidVertex(int vertex) {

		return (vertex < 0 || vertex >= vertexQty);
	}

	private void subConstructor(int qty) {

		vertexQty = qty;
		edgeQty = 0;
		matrix = new boolean[qty][qty];

		vertexObjectList = new ArrayList<T>(qty);

		for (int i = 0; i < qty; i++) {
			vertexObjectList.add((T) new Object());
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public AdjacentMatrixGraph() throws Exception {

		this(AdjacentMatrixGraphC.DEFAULT_QTY);
	}

	/***
	 * 
	 * @param vertexQty
	 * @throws Exception
	 */
	public AdjacentMatrixGraph(int vertexQty) throws Exception {

		if (vertexQty < 0) {

			throw new Exception("Bad vertex qty used");
		}

		subConstructor(vertexQty);
	}

	public List<Integer> getAdjacentList(int vertex) throws Exception {
		if (isInvalidVertex(vertex)) {

			throw new Exception("Using invalid vertex here");

		}

		int vertexFil = vertex;
		List<Integer> answer = new ArrayList<Integer>(vertexQty);

		for (int col = 0; col < vertexQty; col++) {
			if (matrix[vertexFil][col]) {

				answer.add(col);
			}

		}

		Collections.sort(answer);

		return answer;
	}

	public int getVertexQty() {
		// TODO Auto-generated method stub
		return this.vertexQty;
	}

	/**
	 * 
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (this.edgeQty == 0);
	}

	public void empty() {
		// TODO Auto-generated method stub
		int qty = this.vertexQty;
		subConstructor(qty);
	}

	public int getEdgeQty() {
		// TODO Auto-generated method stub
		return edgeQty;
	}

	/**
	 * must overwrite the fil and the col vertexToDelete in the matrix matrix
	 */
	public void deleteVertex(int vertexToDelete) throws Exception {

		if (isInvalidVertex(vertexToDelete)) {

			throw new Exception("Using invalid vertex here");
		} else {
			subDeleteVertex2(vertexToDelete);
			// subDeleteVertex(vertexToDelete);
			subDeleteVertex3(vertexToDelete);
			vertexQty--;
		}
	}

	private void subDeleteVertex3(int vertexToDelete) {

		int filToReplace;

		for (int i = (vertexToDelete + 1); i < vertexQty && ((i - 1) >= 0); i++) {

			filToReplace = (i - 1);
			for (int col = 0; col < vertexQty; col++) {

				matrix[filToReplace][col] = matrix[i][col];
			}

		}

		int colToReplace;
		for (int j = (vertexToDelete + 1); j < vertexQty && ((j - 1) >= 0); j++) {
			colToReplace = (j - 1);
			for (int fil = 0; fil < vertexQty; fil++) {
				matrix[fil][colToReplace] = matrix[fil][j];

			}

		}
	}

	/**
	 * delete the object in the vertex now
	 * 
	 * @param vertexToDelete
	 */
	private void subDeleteVertex2(int vertexToDelete) {
		int oldVertexQty = vertexQty;

		T tempT;
		for (int i = vertexToDelete + 1; i < oldVertexQty; i++) {
			tempT = vertexObjectList.get(i);
			vertexObjectList.set((i - 1), tempT);

		}
		// vertexObjectList.set((oldVertexQty-1),null);
	}

	private void subDeleteVertex(int vertexToDelete) {
		int v1 = vertexToDelete;
		vertexQty--;
		boolean matrixTemp[][] = new boolean[vertexQty][vertexQty];

		int i = 0;

		while (i < vertexQty) {

			int j = 0;
			while (j < vertexQty) {
				if (i < v1 && j < v1) {
					matrixTemp[i][j] = matrix[i][j]; 
				} else if (i < v1 && j >= v1) {
					matrixTemp[i][j] = matrix[i][j + 1];
				} else if (i >= v1 && j < v1) {
					matrixTemp[i][j] = matrix[i + 1][j];
				} else if (i >= v1 && j >= v1) {
					matrixTemp[i][j] = matrix[i + 1][j + 1];
				}
				j++;
			}
			i++;

		}
	}

	private void subAddVertex(T t) {
		int oldVertexQty = vertexQty;
		boolean[][] oldMatrix = matrix;
		List<T> oldList = vertexObjectList;

		int newVertexQty = (oldVertexQty + 1);
		boolean[][] newMatrix = new boolean[newVertexQty][newVertexQty];
		List<T> newList = new ArrayList<T>(newVertexQty);

		newList.addAll(oldList);

		if (oldVertexQty > newList.size()) {

			newList.add(t);
		} else {
			newList.add(t);
		}

		int size = oldVertexQty;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				newMatrix[i][j] = oldMatrix[i][j];

			}
		}

		vertexObjectList = newList;
		vertexQty = newVertexQty;
		matrix = newMatrix;

	}

	public void addVertex(T t) {
		// TODO Auto-generated method stub
		subAddVertex(t);
	}

	public void addEdge(int startVertex, int endVertex) throws Exception {
		// TODO Auto-generated method stub
		if (isInvalidVertex(startVertex) || isInvalidVertex(endVertex)) {

			throw new Exception("Bad vertex use");
		} else {
			edgeQty++;
			matrix[startVertex][endVertex] = matrix[endVertex][startVertex] = true;
		}
	}

	public void deleteEdge(int startVertex, int endVertex) throws Exception {
		// TODO Auto-generated method stub
		if (isInvalidVertex(startVertex) || isInvalidVertex(endVertex)) {

			throw new Exception("Bad vertex use");
		} else {
			edgeQty--;
			matrix[startVertex][endVertex] = matrix[endVertex][startVertex] = false;
		}
	}

	public boolean existEdge(int startVertex, int endVertex) throws Exception {
		// TODO Auto-generated method stub
		if (isInvalidVertex(startVertex) || isInvalidVertex(endVertex)) {

			throw new Exception("Bad vertex use");
		} else {

			return (matrix[startVertex][endVertex] || matrix[endVertex][startVertex]);
		}
	}

	public T getVertexObject(int vertex) throws Exception {
		// TODO Auto-generated method stub
		if (isInvalidVertex(vertex)) {

			throw new Exception("Invalid vertex");
		} else {

			return (vertexObjectList.get(vertex));
		}
	}

	public void setVertexObject(T t, int Vertex) throws Exception {
		// TODO Auto-generated method stub
		if (isInvalidVertex(Vertex)) {

			throw new Exception("Invalid vertex");
		} else {

			vertexObjectList.set(Vertex, t);
		}
	}

	@Override
	public void addObserver(GraphObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public GraphType getGraphType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGraphType(GraphType type) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public void clearObservers() {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public void deleteObserver(GraphObserver graphObserver) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

}
