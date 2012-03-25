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

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class ProgressPane
    extends JPanel
{
    private JProgressBar progressBar;
    private Task task;

    public static void main( String[] sdf )
    {
        ProgressPane progressPane = new ProgressPane(  );

        JFrame jFrame = new JFrame( "show this" );
        jFrame.add( progressPane );
        jFrame.setVisible( true );

        progressPane.startShouting(  );
    }

    class Task
        extends SwingWorker<Void, Void>
    {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground(  )
        {
            Random random = new Random(  );
            int progress = 0;
            // Initialize progress property.
            setProgress( 10 );

            while ( progress > -1 )
            {
                // Sleep for up to one second.
                try
                {
                    Thread.sleep( random.nextInt( 1000 ) );
                } catch ( InterruptedException ignore )
                {
                }

                // Make random progress.
                progress = ( ( progress + 10 ) % 99 );
                setProgress( progress );
                System.out.println( progress );
            }

            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done(  )
        {
            Toolkit.getDefaultToolkit(  ).beep(  );
            setCursor( null ); // turn off the wait cursor
        }
    }

    public ProgressPane(  )
    {
        super( new BorderLayout(  ) );

        progressBar = new JProgressBar( 0, 100 );
        progressBar.setValue( 0 );
        progressBar.setStringPainted( true );

        JPanel panel = new JPanel(  );
        panel.add( progressBar );

        add( panel, BorderLayout.PAGE_START );
        setBorder( BorderFactory.createEmptyBorder( 20, 20, 20, 20 ) );
    }

    public void startShouting(  )
    {
        task = new Task(  );
        task.addPropertyChangeListener( new MyPropertyChangeListener(  ) );
        task.execute(  );
    }

    public void stop(  )
    {
        progressBar.setValue( -1 );

        // task=null;
    }

    class MyActionListener
        implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent e )
        {
        }
    }

    class MyPropertyChangeListener
        implements PropertyChangeListener
    {
        @Override
        public void propertyChange( PropertyChangeEvent evt )
        {
            if ( "progress" == evt.getPropertyName(  ) )
            {
                int progress = (Integer) evt.getNewValue(  );
                progressBar.setValue( progress );
                System.out.println( "doint this" );
            }
        }
    }
}
