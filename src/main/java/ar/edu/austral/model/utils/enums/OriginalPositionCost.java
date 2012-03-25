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

import java.util.Comparator;

import ar.edu.austral.model.utils.Pair;

public class OriginalPositionCost implements Pair<Integer, Integer> {

	private Integer position;
	private Integer cost;

	private static final Integer ZERO = new Integer(0);

	public OriginalPositionCost() {

		this(OriginalPositionCost.ZERO, OriginalPositionCost.ZERO);
	}

	public OriginalPositionCost(int position, int cost) {
		this.position = position;
		this.cost = cost;
	}

	public void setFirst(Integer e) throws Exception {
		// TODO Auto-generated method stub
		position = e;
	}

	public void setSecond(Integer t) throws Exception {
		// TODO Auto-generated method stub
		cost = t;
	}

	public Integer getFirst() {
		// TODO Auto-generated method stub
		return position;
	}

	public Integer getSecond() {
		// TODO Auto-generated method stub
		return cost;
	}

	public boolean equalsTo(Pair<Integer, Integer> pair) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Method not overwritten");
	}

	private class DefaultComparator implements
			Comparator<Pair<Integer, Integer>> {

		public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
			Integer cost0 = o1.getSecond();
			Integer cost1 = o2.getSecond();
			return (cost0 - cost1);
		}

	}
}
