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
package ar.edu.austral.model.interfaces;

import ar.edu.austral.model.utils.enums.GraphType;
import ar.edu.austral.view.gui.GraphObserver;

public interface IGraph<T> {

	public int getVertexQty();

	public boolean isEmpty();

	public void empty();

	public int getEdgeQty();

	public void deleteVertex(int vertexToDelete) throws Exception;

	public void addVertex(T t);

	public void addEdge(int startVertex, int endVertex) throws Exception;

	public void deleteEdge(int startVertex, int endVertex) throws Exception;

	public boolean existEdge(int startVertex, int endVertex) throws Exception;

	public T getVertexObject(int vertex) throws Exception;

	public void setVertexObject(T t, int Vertex) throws Exception;

	public void addObserver(GraphObserver observer);

	// delete an observer
	public void deleteObserver(GraphObserver graphObserver);

	// clear the observers of the list
	public void clearObservers();

	public void setGraphType(GraphType type);

	public GraphType getGraphType();
}
