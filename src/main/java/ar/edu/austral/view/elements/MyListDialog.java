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
package ar.edu.austral.view.elements;

import ar.edu.austral.view.gui.GraphObserver;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class MyListDialog
    extends JDialog
{
    public static final String ACCEPT_STRING = "Show solution";
    protected List list;
    protected JButton acceptButton;
    protected JList jList;
    protected DefaultListModel defaultListModel;
    protected GraphObserver go;

    public static void main( String[] arfds )
    {
        JFrame jFrame = new JFrame( "jajajaj" );
        List<String> list = new ArrayList<String>( 10 );

        for ( int i = 0; i < 11; i++ )
        {
            list.add( i, "dsfdsf  gffdgf" + i );
        }

        MyListDialog myListDialog = new MyListDialog( jFrame, list );
    }

    public MyListDialog( Frame frame, List list )
    {
        super( frame, "List dialog" );

        acceptButton.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    acceptButtonActionPerformed( evt );
                }
            } );
        this.list = list;
        addList(  );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setVisible( true );
        setResizable( true );
        setJMenuBar( new JMenuBar(  ) );
        pack(  );
    }

    public void addObserver( GraphObserver go )
    {
        this.go = go;
    }

    protected void acceptButtonActionPerformed( ActionEvent actionEvent )
    {
        super.dispose(  );
    }

    protected void createGui(  )
    {
        acceptButton = new JButton( MyListDialog.ACCEPT_STRING );
    }

    protected void addList(  )
    {
        String stringToInsert;
        Object object;

        for ( int i = 0; i < list.size(  ); i++ )
        {
            object = list.get( i );
            stringToInsert = object.toString(  );

            defaultListModel.add( i, stringToInsert );
        }
    }

    protected JRootPane createRootPane(  )
    {
        createGui(  );

        JRootPane jRootPane = new JRootPane(  );

        jRootPane.setLayout( new BorderLayout(  ) );
        defaultListModel = new DefaultListModel(  );
        jList = new JList( defaultListModel );
        jList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        jList.setSelectedIndex( 0 );
        jList.setVisibleRowCount( 20 );

        JScrollPane listScrollPane = new JScrollPane( jList );

        jRootPane.add( listScrollPane, BorderLayout.CENTER );
        jRootPane.add( acceptButton, BorderLayout.PAGE_END );

        return jRootPane;
    }
}
