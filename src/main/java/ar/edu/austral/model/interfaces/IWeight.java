/*
 * Copyright (C) 2009         Almada Emiliano
 *                                                 Miura Agustín
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

import java.util.List;

public interface IWeight
{
    int MAX_VALUE = 100;

    public List<Integer> getWeight( int startVertex, int endVertex )
                            throws Exception;

    public void setWeight( int startVertex, int endVertex, int weight, int newWeight )
                   throws Exception;

    public void addWeight( int startVertex, int endVertex, int weight )
                   throws Exception;

    public boolean foundWeight( int startVertex, int endVertex, int weight )
                        throws Exception;

    public void deleteWeight( int startVertex, int endVertex, int weight )
                      throws Exception;
}
