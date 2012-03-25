package ar.edu.austral.model.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ar.edu.austral.model.interfaces.Directed;
import ar.edu.austral.model.interfaces.IGraph;
import ar.edu.austral.model.interfaces.IWeight;
import ar.edu.austral.model.utils.enums.GraphType;
import ar.edu.austral.view.gui.GraphObserver;

public class AdjacentMatrixWeightDirected<T> implements Directed<T>, IWeight {

	private List<Integer>[][] matrix;

	private int vertexQty;

	private int edgeQty;

	private List<T> vertexObjectList;

	private List<GraphObserver> observers;

	private static final Object OBJECT = new Object();

	private static final int DEFAULT_QTY = 10;

	private void subConstructor(int vertexQty) {
		this.vertexQty = vertexQty;
		edgeQty = 0;

		vertexObjectList = new ArrayList(vertexQty);

		for (int i = 0; i < vertexQty; i++) {

			vertexObjectList.add((T) OBJECT);
		}

		matrix = new List[vertexQty][vertexQty];

		for (int i = 0; i < vertexQty; i++) {
			for (int j = 0; j < vertexQty; j++) {
				matrix[i][j] = new LinkedList<Integer>();

			}

		}

		observers = new ArrayList(12);

	}

	public AdjacentMatrixWeightDirected() throws Exception {

		this(AdjacentMatrixWeightDirected.DEFAULT_QTY);
	}

	public AdjacentMatrixWeightDirected(int vertexQty) throws Exception {
		if (vertexQty <= 0) {

			throw new Exception("Using bad vertex qty in graph");

		} else {

			subConstructor(vertexQty);
		}
	}

	@Override
	public List<Integer> getPredeccesorList(int vertex) throws Exception {
		// TODO Auto-generated method stub
		if (isInvalidVertex(vertex)) {

			throw new Exception("Bad vertex qty");
		}

		List<Integer> answerList = new ArrayList<Integer>(vertexQty);
		int colToCheck = vertex;
		boolean isEmptyList;
		boolean foundVertex;

		int max = vertex - 1;
		// Iterator<Integer> iterator;
		for (int i = 0; i <= max; i++) {
			isEmptyList = matrix[i][colToCheck].isEmpty();
			if (!isEmptyList) {
				answerList.add(i);
			}
		}

		int min = (vertex + 1);
		for (int i = min; i < vertexQty; i++) {
			isEmptyList = matrix[i][colToCheck].isEmpty();
			if (!isEmptyList) {
				answerList.add(i);
			}
		}

		return answerList;
	}

	@Override
	public List<Integer> getSuccesorList(int vertex) throws Exception {
		// TODO Auto-generated method stub
		if (isInvalidVertex(vertex)) {

			throw new Exception("Bad vertex qty");
		}

		List<Integer> answerList = new ArrayList<Integer>(vertexQty);
		int filToCheck = vertex;
		boolean isEmptyList;

		for (int i = 0; i < vertexQty; i++) {
			isEmptyList = matrix[filToCheck][i].isEmpty();

			if (!isEmptyList) {
				answerList.add(i);
			}

		}

		return answerList;
	}

	@Override
	public void addEdge(int startVertex, int endVertex) throws Exception {
		// TODO Auto-generated method stub
		boolean invalidVertex0 = isInvalidVertex(startVertex);
		boolean invalidVertex1 = isInvalidVertex(endVertex);

		if (invalidVertex0 || invalidVertex1) {

			throw new Exception("Invalid vertex used");
		}

		throw new Exception("Method not implemented cant use this");
	}

	private void subAddVertex(T t) {
		int oldVertexQty = matrix.length;
		List<Integer>[][] oldMatrix = matrix;
		List<T> oldList = vertexObjectList;

		int newVertexQty = (oldVertexQty + 1);
		List<Integer>[][] newMatrix = new List[newVertexQty][newVertexQty];
		List<T> newList = new ArrayList<T>(newVertexQty);

		newList.addAll(oldList);

		//
		newList.add(t);

		int size = oldVertexQty;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				newMatrix[i][j] = oldMatrix[i][j];

			}
		}

		vertexObjectList = newList;
		vertexQty = newVertexQty;
		matrix = newMatrix;

		// matrix[vertexQty-1][vertexQty-1]=new LinkedList<Integer>();
		for (int i = 0; i < vertexQty; i++) {

			matrix[vertexQty - 1][i] = new LinkedList<Integer>();
		}

		for (int i = 0; i < vertexQty; i++) {
			matrix[i][vertexQty - 1] = new LinkedList<Integer>();

		}

	}

	@Override
	public void addVertex(T t) {
		// TODO Auto-generated method stub
		subAddVertex(t);
		Iterator<GraphObserver> iterator = observers.iterator();
		int count = -1;
		while (iterator.hasNext()) {
			count++;
			System.out.println(count);
			iterator.next().vertexAdded();
		}

	}

	@Override
	public void deleteEdge(int startVertex, int endVertex) throws Exception {
		// TODO Auto-generated method stub
		boolean invalidVertex0 = isInvalidVertex(startVertex);
		boolean invalidVertex1 = isInvalidVertex(endVertex);

		if (invalidVertex0 || invalidVertex1) {

			throw new Exception("Bad vertex used");
		}

		// do that , this
		int qty = matrix[startVertex][endVertex].size();

		edgeQty = edgeQty - qty;

		matrix[startVertex][endVertex].clear();

	}

	private void subDeleteVertexObject(int vertexToDelete) {
		int oldVertexQty = vertexQty;

		T tempT;
		for (int i = vertexToDelete + 1; i < oldVertexQty; i++) {
			tempT = vertexObjectList.get(i);
			vertexObjectList.set((i - 1), tempT);

		}

	}

	private void subDeleteVertex(int vertexToDelete) {
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

	@Override
	public void deleteVertex(int vertexToDelete) throws Exception {
		// TODO Auto-generated method stub
		if (isInvalidVertex(vertexToDelete)) {

			throw new Exception("Cant delete that vertex");
		} else {
			subDeleteVertexObject(vertexToDelete);
			subDeleteVertex(vertexToDelete);
			vertexQty--;

			Iterator<GraphObserver> iterator = observers.iterator();

			System.out
					.println("in model direct graph with weight notifying the obsevers");

			int count = -1;
			while (iterator.hasNext()) {
				count++;
				System.out.println("size" + count);

				iterator.next().vertexDeleted(vertexToDelete);
			}

		}
	}

	@Override
	public void empty() {
		// TODO Auto-generated method stub
		int oldVertexQty = vertexQty;

		subConstructor(oldVertexQty);

		Iterator<GraphObserver> iterator = observers.iterator();
		while (iterator.hasNext()) {

			iterator.next().deletedAllEdges();
		}

	}

	@Override
	public boolean existEdge(int startVertex, int endVertex) throws Exception {
		// TODO Auto-generated method stub
		boolean answer = false;

		if (isInvalidVertex(startVertex) || isInvalidVertex(endVertex)) {

			throw new Exception("Bad vertex used");
		}
		answer = matrix[startVertex][endVertex].isEmpty();
		return (!answer);
	}

	@Override
	public int getEdgeQty() {
		// TODO Auto-generated method stub
		return edgeQty;
	}

	private boolean isInvalidVertex(int vertex) {
		// the vertex qty;
		if (vertex <= -1 || vertex >= matrix.length) {

			return true;
		} else {

			return false;
		}

	}

	private boolean isInvalidParameterException(int vertexQty) throws Exception {

		boolean answer = false;

		if (vertexQty <= -1) {

			throw new Exception("Bad vertex used");
		} else {

			return answer;
		}

	}

	@Override
	public T getVertexObject(int vertex) throws Exception {
		// TODO Auto-generated method stub
		boolean badParameter = isInvalidParameterException(vertex);
		T t = vertexObjectList.get(vertex);
		Object objectTemp = t;
		boolean isNull = objectTemp.equals(AdjacentMatrixWeightDirected.OBJECT);
		if (isNull) {

			return null;
		} else {
			return t;

		}
	}

	@Override
	public int getVertexQty() {
		// TODO Auto-generated method stub
		return matrix.length;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (edgeQty == 0);
	}

	@Override
	public void setVertexObject(T t, int vertex) throws Exception {
		// TODO Auto-generated method stub
		if (isInvalidVertex(vertex)) {
			throw new Exception("Bad parameter used vertex, or t to add");
		} else if (t == null) {

			vertexObjectList.set(vertex,
					(T) AdjacentMatrixWeightDirected.OBJECT);
		} else {

			vertexObjectList.set(vertex, t);
		}
	}

	private boolean subContainsWeight(int startVertex, int endVertex, int weight) {

		List<Integer> list = matrix[startVertex][endVertex];
		return (list.contains(weight));

	}

	@Override
	public void addWeight(int startVertex, int endVertex, int weight)
			throws Exception {

		if (isInvalidVertex(startVertex) || isInvalidVertex(endVertex)) {
			throw new Exception(
					"Bad parameter used un start vertex or endVertex or weight");
		}

		boolean containsWeight = subContainsWeight(startVertex, endVertex,
				weight);

		if (containsWeight) {

			throw new Exception("Weight already added");
		} else {

			matrix[startVertex][endVertex].add(weight);
			edgeQty++;

			Iterator<GraphObserver> iterator = observers.iterator();

			while (iterator.hasNext()) {

				iterator.next().weightAdded(startVertex, endVertex, weight);

			}

		}
	}

	@Override
	public void deleteWeight(int startVertex, int endVertex, int weight)
			throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (isInvalidVertex(startVertex) || isInvalidVertex(endVertex)
				|| (weight <= 0)) {
			throw new Exception(
					"Bad parameter used un start vertex or endVertex or weight");
		}

		boolean containsWeight = subContainsWeight(startVertex, endVertex,
				weight);

		int state = -1;
		int index = matrix[startVertex][endVertex].indexOf(weight);

		if (containsWeight) {
			matrix[startVertex][endVertex].remove(index);
			edgeQty--;

			Iterator<GraphObserver> iterator = observers.iterator();

			while (iterator.hasNext()) {

				iterator.next().weightDeleted(startVertex, endVertex, weight);

			}
		} else {
			throw new Exception("Weight cannot be deleted");
		}

	}

	@Override
	public boolean foundWeight(int startVertex, int endVertex, int weight)
			throws Exception {
		if (isInvalidVertex(startVertex) || isInvalidVertex(endVertex)
				|| (weight <= 0)) {
			throw new Exception(
					"Bad parameter used un start vertex or endVertex or weight");
		}

		boolean answer = subContainsWeight(startVertex, endVertex, weight);
		return answer;
	}

	@Override
	public List<Integer> getWeight(int startVertex, int endVertex)
			throws Exception {

		List<Integer> answer = new LinkedList<Integer>();

		if (isInvalidVertex(startVertex) || isInvalidVertex(endVertex)) {
			throw new Exception(
					"Bad parameter used un start vertex or endVertex ");
		}

		List<Integer> listWeight = matrix[startVertex][endVertex];
		Iterator<Integer> iterator = listWeight.iterator();
		while (iterator.hasNext()) {

			answer.add(iterator.next());
		}
		return answer;
	}

	@Override
	public void setWeight(int startVertex, int endVertex, int weight,
			int newWeight) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method not implemented");
	}

	@Override
	public void addObserver(GraphObserver observer) {
		observers.add(observer);

	}

	@Override
	public GraphType getGraphType() {
		// TODO Auto-generated method stub
		return GraphType.DIRECTED;
	}

	@Override
	public void setGraphType(GraphType type) {
		// TODO Auto-generated method stub
		throw new RuntimeException();
	}

	@Override
	public void clearObservers() {
		observers.clear();
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteObserver(GraphObserver graphObserver) {
		boolean contains = observers.contains(graphObserver);
		if (contains) {
			observers.remove(Collections.singleton(graphObserver));
		}

	}

}
