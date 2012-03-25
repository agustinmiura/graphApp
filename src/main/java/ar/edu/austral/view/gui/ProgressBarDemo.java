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
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class ProgressBarDemo
    extends JPanel
    implements ActionListener,
               PropertyChangeListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JProgressBar progressBar;
    private JButton startButton;
    private JTextArea taskOutput;
    private Task task;

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
            setProgress( 0 );

            while ( progress < 100 )
            {
                // Sleep for up to one second.
                try
                {
                    Thread.sleep( random.nextInt( 1000 ) );
                } catch ( InterruptedException ignore )
                {
                }

                // Make random progress.
                progress += random.nextInt( 10 );
                setProgress( Math.min( progress, 100 ) );
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
            startButton.setEnabled( true );
            setCursor( null ); // turn off the wait cursor
            taskOutput.append( "Done!\n" );
        }
    }

    public ProgressBarDemo(  )
    {
        super( new BorderLayout(  ) );

        // Create the demo's UI.
        startButton = new JButton( "Start" );
        startButton.setActionCommand( "start" );
        startButton.addActionListener( this );

        progressBar = new JProgressBar( 0, 100 );
        progressBar.setValue( 0 );
        progressBar.setStringPainted( true );

        taskOutput = new JTextArea( 5, 20 );
        taskOutput.setMargin( new Insets( 5, 5, 5, 5 ) );
        taskOutput.setEditable( false );

        JPanel panel = new JPanel(  );
        panel.add( startButton );
        panel.add( progressBar );

        add( panel, BorderLayout.PAGE_START );
        add( new JScrollPane( taskOutput ),
             BorderLayout.CENTER );
        setBorder( BorderFactory.createEmptyBorder( 20, 20, 20, 20 ) );
    }

    /**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed( ActionEvent evt )
    {
        startButton.setEnabled( false );
        setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
        // Instances of javax.swing.SwingWorker are not reusuable, so
        // we create new instances as needed.
        task = new Task(  );
        task.addPropertyChangeListener( (PropertyChangeListener) this );
        task.execute(  );
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange( PropertyChangeEvent evt )
    {
        if ( "progress" == evt.getPropertyName(  ) )
        {
            int progress = (Integer) evt.getNewValue(  );
            progressBar.setValue( progress );
            taskOutput.append( String.format( "Completed %d%% of task.\n",
                                              task.getProgress(  ) ) );
        }
    }

    /**
     * Create the GUI and show it. As with all GUI code, this must run on the
     * event-dispatching thread.
     */
    private static void createAndShowGUI(  )
    {
        // Create and set up the window.
        JFrame frame = new JFrame( "ProgressBarDemo" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        // Create and set up the content pane.
        JComponent newContentPane = new ProgressBarDemo(  );
        newContentPane.setOpaque( true ); // content panes must be opaque
        frame.setContentPane( newContentPane );

        // Display the window.
        frame.pack(  );
        frame.setVisible( true );
    }

    public static void main( String[] args )
    {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater( new Runnable(  )
            {
                public void run(  )
                {
                    createAndShowGUI(  );
                }
            } );
    }
}
