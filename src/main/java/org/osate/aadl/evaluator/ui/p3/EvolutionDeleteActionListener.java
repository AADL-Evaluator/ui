package org.osate.aadl.evaluator.ui.p3;

import fluent.gui.impl.swing.FluentTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class EvolutionDeleteActionListener implements ActionListener
{
    private final FluentTable table;

    public EvolutionDeleteActionListener( final FluentTable table )
    {
        this.table = table;
    }
    
    @Override
    public void actionPerformed( ActionEvent e )
    {
        int r = JOptionPane.showConfirmDialog( 
            table , 
            "Do you want to delete all evolutions selected?" , 
            "Delete" , 
            JOptionPane.YES_NO_OPTION , 
            JOptionPane.WARNING_MESSAGE
        );
        
        if( r == JOptionPane.YES_OPTION )
        {
            table.removeSelectedObjects();
        }
    }
    
}