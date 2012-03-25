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
package ar.edu.austral.controller;

import ar.edu.austral.model.algorithms.Dijkstra2;
import ar.edu.austral.model.algorithms.Floyd;
import ar.edu.austral.model.algorithms.MinimumSpanningTreePrim2;
import ar.edu.austral.model.algorithms.NodoA;
import ar.edu.austral.model.implementations.AdjacentListWeightGraph;
import ar.edu.austral.model.implementations.AdjacentMatrixWeightDirected;
import ar.edu.austral.model.interfaces.Directed;
import ar.edu.austral.model.interfaces.IGraph;
import ar.edu.austral.model.interfaces.IWeight;
import ar.edu.austral.model.interfaces.Undirected;
import ar.edu.austral.model.utils.enums.GraphType;
import ar.edu.austral.view.elements.MyListDialog;
import ar.edu.austral.view.elements.MyListDijkstraDialog;
import ar.edu.austral.view.elements.MyPrimDialog;
import ar.edu.austral.view.gui.GraphView2;
import ar.edu.austral.view.gui.PanelState;
import ar.edu.austral.view.gui.TableDialog;
import ar.edu.austral.view.test.AddEdgeDialog;
import ar.edu.austral.view.test.DeleteEdgeDialog;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class GraphShowApp
    extends JFrame
{
    public static final String APP_NAME = "Graph application show";
    private JFrame jFrame;
    private JMenuBar mainBar;
    private IGraph iGraph;
    private Directed directed;
    private Undirected undirected;
    private GraphView2 graphView2;
    private JScrollPane jScrollPaneOfGraphView2;

    public GraphShowApp(  )
    {
        super(  );
        jFrame = this;
        setExtendedState( JFrame.MAXIMIZED_BOTH );
        setLayout( new BorderLayout(  ) );
        assingElements(  );
        createGui(  );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setSize( 640, 480 );
        setVisible( true );
        setResizable( true );
    }

    private void assingElements(  )
    {
        graphView2 = new GraphView2(  );
        graphView2.setPanelState( PanelState.NONE );
        directed = null;
        undirected = null;
        iGraph = null;
    }

    private JMenu createModifyGraphMenu(  )
    {
        JMenu jMenu = new JMenu( "Modify graph" );

        JMenuItem menuItem;

        menuItem = new JMenuItem( "Add vertex " );
        menuItem.addActionListener( new CommonVertexPaint(  ) );
        jMenu.add( menuItem );

        menuItem = new JMenuItem( "Add edge " );
        menuItem.addActionListener( new CommonEdgePaint(  ) );
        jMenu.add( menuItem );

        menuItem = new JMenuItem( "Delete Vertex" );
        menuItem.addActionListener( new DeleteVertexACtion(  ) );
        jMenu.add( menuItem );

        menuItem = new JMenuItem( "Delete Edge" );
        menuItem.addActionListener( new CommonDeleteEdgePaint(  ) );
        jMenu.add( menuItem );

        return jMenu;
    }

    private JMenu createTestMenu(  )
    {
        JMenu jMenu = new JMenu( "Test menu" );

        JMenuItem menuItem;
        menuItem = new JMenuItem( "Set special edge" );
        menuItem.addActionListener( new SpecialEdgePaint(  ) );
        jMenu.add( menuItem );

        menuItem = new JMenuItem( "Restore configuration" );
        menuItem.addActionListener( new RestoreConfig(  ) );
        jMenu.add( menuItem );

        return jMenu;
    }

    private class DeleteVertexACtion
        implements ActionListener
    {
        private boolean isInvalidParameter( Integer vertexToDelete )
        {
            boolean answer;
            boolean check0 = vertexToDelete < 0;
            boolean check1 = ( vertexToDelete > iGraph.getVertexQty(  ) );
            boolean check2 = ( iGraph == null );

            answer = check0 || check1 || check2;

            return ( answer );
        }

        @Override
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                String stringInteger =
                    JOptionPane.showInputDialog( jFrame, GraphAppConstants.DIALOG_MESSAGE_ENTER_VERTEX );

                Integer vertexToDelete = Integer.parseInt( stringInteger );

                boolean check = isInvalidParameter( vertexToDelete );

                if ( check )
                {
                    JOptionPane.showMessageDialog( jFrame, GraphAppConstants.MESSAGE_ERROR_INVALID_PARAMETER );

                    return;
                } else
                {
                    graphView2.vertexDeleted( vertexToDelete );
                }
            } catch ( HeadlessException e1 )
            {
                e1.printStackTrace(  );
            } catch ( NumberFormatException e1 )
            {
                e1.printStackTrace(  );
            } catch ( Exception e1 )
            {
                e1.printStackTrace(  );
            }
        }
    }

    private class CommonDeleteVertexPaint
        implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent arg0 )
        {
        }
    }

    private class CommonDeleteEdgePaint
        implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            DeleteEdgeDialog d = new DeleteEdgeDialog( jFrame, graphView2, (IWeight) iGraph );
            d.setTitle( GraphAppConstants.DELETE_EDGE );
            d.setVisible( true );
        }
    }

    private class CommonVertexPaint
        implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent arg0 )
        {
            try
            {
                iGraph.addVertex( "Sdf" );
            } catch ( NullPointerException e )
            {
                JOptionPane.showMessageDialog( jFrame, GraphAppConstants.MESSAGE_ERROR_GRAPH_UNINITIALIZED );
            }
        }
    }

    private class CommonEdgePaint
        implements ActionListener
    {
        private boolean usingInvalidVertex( int startVertex, int endVertex, int cost )
        {
            boolean check0 = ( ( startVertex < 0 ) || ( startVertex > iGraph.getVertexQty(  ) ) );
            boolean check1 = ( ( endVertex < 0 ) || ( endVertex > iGraph.getVertexQty(  ) ) );
            boolean check2 = ( iGraph == null );

            return ( check0 || check1 || check2 );
        }

        @Override
        public void actionPerformed( ActionEvent e )
        {
            AddEdgeDialog a = new AddEdgeDialog( jFrame, graphView2, (IWeight) iGraph );
            a.setTitle( GraphAppConstants.ADD_EDGE );
            a.setVisible( true );
        }
    }

    private class SpecialEdgePaint
        implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                String stringStartVertex = JOptionPane.showInputDialog( this, "get startVertex" );
                String stringEndVertex = JOptionPane.showInputDialog( this, "get endVertex" );
                String stringCost = JOptionPane.showInputDialog( this, "get cost" );

                Integer startVertex = new Integer( stringStartVertex );
                Integer endVertex = new Integer( stringEndVertex );
                Integer cost = new Integer( stringCost );

                graphView2.highlightGraficalEdge( startVertex,
                                                  endVertex,
                                                  cost,
                                                  graphView2.getSpecialColor(  ) );
            } catch ( NumberFormatException e1 )
            {
                JOptionPane.showMessageDialog( jFrame, GraphAppConstants.MESSAGE_ERROR_INVALID_PARAMETER );
            } catch ( NullPointerException e1 )
            {
                JOptionPane.showMessageDialog( jFrame, GraphAppConstants.MESSAGE_ERROR_GRAPH_UNINITIALIZED );
            } catch ( Exception e1 )
            {
                e1.printStackTrace(  );
            }
        }
    }

    private class RestoreConfig
        implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            graphView2.restoreConfig(  );
        }
    }

    private JMenu createFileMenu(  )
    {
        JMenu jMenu = new JMenu( GraphAppConstants.MENU_NAME_FILE );

        JMenuItem menuItem;

        menuItem = new JMenuItem( GraphAppConstants.MENU_ITEM_EXIT );
        menuItem.addActionListener( new ExitApp(  ) );
        jMenu.add( menuItem );

        return jMenu;
    }

    private JMenu createGraphMenu(  )
    {
        JMenu jMenu = new JMenu( GraphAppConstants.MENU_NAME_GRAPH );

        JMenuItem menuItem;

        menuItem = new JMenuItem( GraphAppConstants.MENU_ITEM_CREATE_G );
        menuItem.addActionListener( new CreateGraph(  ) );
        jMenu.add( menuItem );

        menuItem = new JMenuItem( GraphAppConstants.MENU_ITEM_DELETE_G );
        menuItem.addActionListener( new DeleteGraph(  ) );
        jMenu.add( menuItem );

        menuItem = new JMenuItem( GraphAppConstants.MENU_ITEM_RESET_G );
        menuItem.addActionListener( new ResetGraph(  ) );
        jMenu.add( menuItem );

        return jMenu;
    }

    private JMenu createAlgorithmMenu(  )
    {
        JMenu jMenu = new JMenu( GraphAppConstants.MENU_NAME_ALGORITHM );

        JMenuItem menuItem;

        menuItem = new JMenuItem( GraphAppConstants.MENU_ITEM_DIJSTRA );
        menuItem.addActionListener( new DoDijstra(  ) );
        jMenu.add( menuItem );

        menuItem = new JMenuItem( GraphAppConstants.MENU_ITEM_PRIM );
        menuItem.addActionListener( new DoPrim(  ) );
        jMenu.add( menuItem );

        menuItem = new JMenuItem( GraphAppConstants.MENU_ITEM_FLOYD );
        menuItem.addActionListener( new DoFloyd(  ) );
        jMenu.add( menuItem );

        return jMenu;
    }

    private JMenuBar createMainBar(  )
    {
        JMenuBar jMenuBar = new JMenuBar(  );

        jMenuBar.add( createFileMenu(  ) );
        jMenuBar.add( createGraphMenu(  ) );
        jMenuBar.add( createAlgorithmMenu(  ) );
        jMenuBar.add( createTestMenu(  ) );
        jMenuBar.add( createModifyGraphMenu(  ) );

        return jMenuBar;
    }

    private void createGui(  )
    {
        mainBar = createMainBar(  );
        setJMenuBar( mainBar );

        jScrollPaneOfGraphView2 = new JScrollPane( graphView2 );
        jScrollPaneOfGraphView2.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        jScrollPaneOfGraphView2.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
        jScrollPaneOfGraphView2.setAutoscrolls( true );

        add( jScrollPaneOfGraphView2 );
    }

    private class DoFloyd
        implements ActionListener
    {
        private boolean usingInvalidParameter( int startVertex )
        {
            boolean check0 = iGraph == null;
            boolean check3 = directed == null;

            return ( check0 || check3 );
        }

        @Override
        public void actionPerformed( ActionEvent e )
        {
            boolean check = usingInvalidParameter( 0 );

            if ( check )
            {
                JOptionPane.showMessageDialog( jFrame, "Error" );

                return;
            } else
            {
                try
                {
                    List<int[][]> solveList = Floyd.floyd( directed );

                    TableDialog tableDialog = new TableDialog( jFrame,
                                                               solveList.get( 0 ),
                                                               solveList.get( 1 ) );
                } catch ( Exception e1 )
                {
                    e1.printStackTrace(  );
                }
            }
        }
    }

    private class CreateGraph
        implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            int stop;
            int vertexQty;
            int graphChoosen;
            String stringInteger = null;

            stringInteger = JOptionPane.showInputDialog( GraphAppConstants.DIALOG_MESSAGE_VERTEX_QTY );
            vertexQty = new Integer( stringInteger );

            int n =
                JOptionPane.showOptionDialog( jFrame, GraphAppConstants.DIALOG_MESSAGE_CREATE_G,
                                              GraphAppConstants.DIALOG_NAME_CREATE_G, JOptionPane.YES_NO_CANCEL_OPTION,
                                              JOptionPane.QUESTION_MESSAGE, null, GraphAppConstants.GRAPH_NAME, // the titles of buttons
                                              GraphAppConstants.GRAPH_NAME[0] ); // default button title

            graphChoosen = n;

            switch ( n )
            {
                case 0:
                {
                    try
                    {
                        stop = 9;
                        iGraph = new AdjacentMatrixWeightDirected( vertexQty );
                        directed = (Directed) iGraph;
                        undirected = null;
                        iGraph.addObserver( graphView2 );
                        graphView2.setPanelState( PanelState.PUT_VERTEX );
                        graphView2.setVertexQtyToPut( vertexQty );

                        break;
                    } catch ( Exception e1 )
                    {
                        JOptionPane.showMessageDialog( jFrame, GraphAppConstants.DIALOG_MESSAGE_INVALID_VERTEX_QTY );
                    }
                }

                case 1:
                {
                    try
                    {
                        stop = 89;
                        iGraph = new AdjacentListWeightGraph( vertexQty );
                        iGraph.addObserver( graphView2 );
                        undirected = (Undirected) iGraph;
                        directed = null;
                        graphView2.setPanelState( PanelState.PUT_VERTEX );
                        graphView2.setVertexQtyToPut( vertexQty );

                        break;
                    } catch ( Exception e1 )
                    {
                        JOptionPane.showMessageDialog( jFrame, GraphAppConstants.DIALOG_MESSAGE_INVALID_VERTEX_QTY );
                    }
                }

                case -1:
                    return;

                default:
                    return;
            }
        }
    }

    private class DeleteGraph
        implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            iGraph = null;
            directed = null;
            undirected = null;
            graphView2.clearEdges(  );
            graphView2.clearVertexArray(  );
            graphView2.paint( graphView2.getGraphics(  ) );
            graphView2.setVertexToPut( 0 );
            graphView2.setVertexQtyToPut( 0 );
        }
    }

    private class ResetGraph
        implements ActionListener
    {
        public boolean invalidParameters(  )
        {
            boolean answer = false;
            boolean check0 = iGraph == null;
            boolean check1 = ( ( directed == null ) && ( undirected == null ) );
            answer = ( ( check0 || check1 ) );

            return answer;
        }

        @Override
        public void actionPerformed( ActionEvent e )
        {
            GraphType graphType = iGraph.getGraphType(  );
            boolean invalidParameter = invalidParameters(  );

            if ( invalidParameter )
            {
                JOptionPane.showMessageDialog( jFrame, GraphAppConstants.DIALOG_MESSAGE_ERROR );

                return;
            } else
            {
                graphView2.clearPanel(  );
                graphView2.clearEdges(  );
                graphView2.redrawAllVertex(  );
            }
        }
    }

    private class DoPrim
        implements ActionListener
    {
        private boolean usingInvalidParameters(  )
        {
            boolean check0 = ( ( directed != null ) && ( undirected == null ) );
            boolean check1 = ( iGraph == null );

            return ( check0 || check1 );
        }

        @Override
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                boolean invalidParameter = usingInvalidParamter(  );
                String stringVertex =
                    JOptionPane.showInputDialog( jFrame, GraphAppConstants.DIALOG_MESSAGE_ENTER_VERTEX );
                Integer startVertex = new Integer( stringVertex );

                if ( ( startVertex < 0 ) || ( startVertex > iGraph.getVertexQty(  ) ) )
                {
                    JOptionPane.showMessageDialog( jFrame, GraphAppConstants.MESSAGE_ERROR_INVALID_VERTEX );

                    return;
                }

                if ( invalidParameter )
                {
                    JOptionPane.showMessageDialog( jFrame, GraphAppConstants.DIALOG_MESSAGE_ERROR_PRIM );
                } else
                {
                    int[] solutionArray = MinimumSpanningTreePrim2.subGetFatherArray( (IWeight) iGraph, startVertex );

                    MyPrimDialog myPrimDialog =
                        new MyPrimDialog( jFrame,
                                          MyPrimDialog.getDialogPrimActions(  ), solutionArray, iGraph, graphView2 );
                }
            } catch ( HeadlessException e1 )
            {
                e1.printStackTrace(  );
            } catch ( NumberFormatException e1 )
            {
                JOptionPane.showMessageDialog( jFrame, GraphAppConstants.MESSAGE_ERROR_INVALID_VERTEX );
                e1.printStackTrace(  );
            } catch ( Exception e1 )
            {
                e1.printStackTrace(  );
            }
        }

        private boolean usingInvalidParamter(  )
        {
            return false;
        }
    }

    private class DoDijstra
        implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            Integer beginningVertex = null;

            try
            {
                String message = JOptionPane.showInputDialog( this, "Please write the beginning vertex" );
                beginningVertex = Integer.parseInt( message );

                if ( ( beginningVertex < 0 ) || ( beginningVertex > ( iGraph.getVertexQty(  ) - 1 ) ) )
                {
                    return;
                }
            } catch ( NumberFormatException f )
            {
                new JOptionPane( "Valor invalido, el vertice inicial debe ser el indice del vertice." );
            }

            AdjacentMatrixWeightDirected g = (AdjacentMatrixWeightDirected) iGraph;
            int nodo_origen = beginningVertex;
            Dijkstra2<Integer> d = null;

            try
            {
                d = new Dijkstra2<Integer>( g, nodo_origen );
            } catch ( Exception e1 )
            {
                e1.printStackTrace(  );
            }

            NodoA[] resultado = d.getResult(  );

            List list = new ArrayList<String>(  );

            for ( int i = 0; i < resultado.length; i++ )
            {
                list.add( new String( "Desde v" + ( nodo_origen ) + " hasta v" + ( i ) + ": " + resultado[i].distancia ) );
            }

            MyListDialog listDialog = new MyListDijkstraDialog( jFrame, list, resultado, nodo_origen, g, d );
            listDialog.addObserver( graphView2 );
            listDialog.setVisible( true );

            System.out.println( "do Djistra" );
        }
    }

    private class ExitApp
        implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                System.exit( -10 );
            } catch ( Throwable e1 )
            {
                e1.printStackTrace(  );
            }
        }
    }
}
