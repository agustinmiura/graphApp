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
package ar.edu.austral.view.gui;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableDialog
    extends JDialog
{
    private int[][] matrixCalc;
    private int[][] matrixWay;
    private JScrollPane scrollPane0;
    private JScrollPane scrollPane1;
    private JTable jTable0;
    private JTable jTable1;

    public static void main( String[] asdf )
    {
        int[][] sol0 = new int[2][2];
        int[][] sol1 = new int[2][2];

        for ( int i = 0; i < sol0.length; i++ )
        {
            for ( int j = 0; j < sol1.length; j++ )
            {
                sol0[i][j] = 0;
                sol1[i][j] = 1;
            }
        }

        JFrame jFrame = new JFrame(  );
        TableDialog tableDialog = new TableDialog( jFrame, sol0, sol1 );

        jFrame.setVisible( true );
    }

    public TableDialog( JFrame jFrame, int[][] matrixCalc, int[][] matrixWay )
    {
        super( jFrame, "table dialog" );

        this.matrixCalc = matrixCalc;
        this.matrixWay = matrixWay;
        createGui(  );

        JRootPane jRootPane = getRootPane(  );
        jRootPane.setLayout( new BoxLayout( jRootPane, BoxLayout.Y_AXIS ) );
        jRootPane.add( scrollPane0 );
        jRootPane.add( scrollPane1 );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setVisible( true );
        setResizable( true );
        pack(  );
    }

    private void createGui(  )
    {
        int size = matrixCalc.length;
        String[] columnsName = new String[size];

        Integer integer;

        for ( int i = 0; i < size; i++ )
        {
            integer = new Integer( i );

            columnsName[i] = new String( integer.toString(  ) );
        }

        Integer[][] matrix0 = new Integer[size][size];
        Integer[][] matrix1 = new Integer[size][size];

        for ( int i = 0; i < size; i++ )
        {
            for ( int j = 0; j < size; j++ )
            {
                matrix0[i][j] = matrixCalc[i][j];
                matrix1[i][j] = matrixWay[i][j];
            }
        }

        JTable table0 = new JTable( matrix0, columnsName );

        JTable table1 = new JTable( matrix1, columnsName );

        JScrollPane scrollPane0 = new JScrollPane( table0 );
        table0.setFillsViewportHeight( true );

        JScrollPane scrollPane1 = new JScrollPane( table1 );
        table1.setFillsViewportHeight( true );

        scrollPane0.setAutoscrolls( true );
        scrollPane1.setAutoscrolls( true );

        scrollPane0.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
        scrollPane0.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );

        scrollPane1.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
        scrollPane1.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );

        this.scrollPane0 = scrollPane0;
        this.scrollPane1 = scrollPane1;
    }
}
