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

import java.util.List;

import ar.edu.austral.model.interfaces.IGraph;
import ar.edu.austral.model.interfaces.IWeight;

public class GraphUtils {

	public static <T> Integer getMinWeight(IGraph<T> grafo, int origen,
			int destino) throws Exception {
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
