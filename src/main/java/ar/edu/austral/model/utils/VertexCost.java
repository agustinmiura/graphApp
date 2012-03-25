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
package ar.edu.austral.model.utils;

public class VertexCost implements Pair<Integer, Integer> {

	private Integer vertex;
	private Integer cost;

	/**
	 * 
	 * @param vertex
	 * @param cost
	 * @throws Exception
	 */
	public VertexCost(Integer vertex, Integer cost) throws Exception {
		this.vertex = vertex;
		this.cost = cost;

	}

	public void setFirst(Integer e) throws Exception {
		// TODO Auto-generated method stub
		vertex = e;
	}

	public void setSecond(Integer t) throws Exception {
		// TODO Auto-generated method stub
		cost = t;
	}

	public Integer getFirst() {
		// TODO Auto-generated method stub
		return vertex;
	}

	public Integer getSecond() {
		// TODO Auto-generated method stub
		return cost;
	}

	public boolean equalsTo(Pair<Integer, Integer> pair) {
		// TODO Auto-generated method stub
		Integer firstOp = vertex;
		Integer secondOp = pair.getFirst();
		Integer result = firstOp - secondOp;
		return (result == 0);

	}

	public boolean equals(Object object) {
		boolean answer = false;

		Pair<Integer, Integer> pair = (Pair) object;
		boolean equalVertex = (vertex - pair.getFirst()) == 0;
		boolean equalCost = (cost - pair.getSecond()) == 0;

		return (equalVertex && equalCost);
	}

	/**
	 * 
	 */
	public String toString() {
		String answer = null;
		answer = Utils.getString("(vertex , cost )=(", "(", vertex.toString(),
				",", cost.toString(), ")");
		return answer;

	}

}
