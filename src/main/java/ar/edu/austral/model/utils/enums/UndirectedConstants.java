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
package ar.edu.austral.model.utils.enums;

import ar.edu.austral.model.utils.Pair;
import ar.edu.austral.model.utils.VertexCost;

public class UndirectedConstants {

	public static Pair<Integer, Integer> getPairZero() {
		Pair<Integer, Integer> pair = null;
		try {
			pair = new VertexCost(new Integer(0), new Integer(0));

			return pair;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pair;

	}

}
