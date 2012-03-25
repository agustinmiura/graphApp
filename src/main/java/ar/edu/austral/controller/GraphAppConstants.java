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
package ar.edu.austral.controller;

public interface GraphAppConstants {

	String GRAPH_NAME_DIRECTED = "Directed";
	String GRAPH_NAME_UNDIRECTED = "Undirected";

	Object[] GRAPH_NAME = new Object[] { GRAPH_NAME_DIRECTED,
			GRAPH_NAME_UNDIRECTED };

	String DIALOG_MESSAGE_ERROR = "Invalid message";

	String DIALOG_MESSAGE_INVALID_VERTEX_QTY = "Using invalid vertex qty";
	String DIALOG_MESSAGE_VERTEX_QTY = "Choose vertex qty";

	String DIALOG_MESSAGE_ERROR_PRIM = "Error using parameters with prim";

	String DIALOG_MESSAGE_CREATE_G = "  Choose graph type  ";
	String DIALOG_NAME_CREATE_G = "Create graph";

	String DIALOG_MESSAGE_ENTER_VERTEX_DEL = "Enter vertex to delete";

	String DIALOG_MESSAGE_ENTER_VERTEX = "Enter vertex to do prim algorithm";

	String MESSAGE_ERROR_INVALID_VERTEX = "Using invalid vertex";

	String MESSAGE_ERROR_INVALID_PARAMETER = "Using invalid parameter";

	String MESSAGE_ERROR_GRAPH_UNINITIALIZED = "Please create a graph before this operation";
	String MESSAGE_ERROR_EDGE_UNINITIALIZED = "You are trying to delete an invalid edge";

	String MENU_NAME_FILE = "File";
	String MENU_NAME_GRAPH = "Graph";
	String MENU_NAME_ALGORITHM = "Algorithm";

	String MENU_ITEM_CREATE_G = "Create graph";
	String MENU_ITEM_RESET_G = "Reset graph";
	String MENU_ITEM_DELETE_G = "Delete graph";

	String MENU_ITEM_PRIM = "Prim algorithm";
	String MENU_ITEM_DIJSTRA = "Dijstra algorithm";
	String MENU_ITEM_FLOYD = "Floyd algorithm";

	String MENU_ITEM_EXIT = "Exit app";

	String MESSAGE_GRAPH_CHOOSE = "Use directed graph or undirected graph";

	String ADD_EDGE = "Add edge";
	String DELETE_EDGE = "Delete edge";
	String MESSAGE_ERROR_ALREADY_EXISTS = "That edge already exists in the graph";

}
