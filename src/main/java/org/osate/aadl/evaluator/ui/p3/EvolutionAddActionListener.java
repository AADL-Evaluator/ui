package org.osate.aadl.evaluator.ui.p3;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.SwingUtilities;
import org.osate.aadl.evaluator.evolution.Evolution;
import org.osate.aadl.evaluator.ui.mainWizard.AadlUIAction;

public class EvolutionAddActionListener implements ActionListener
{
    private final EvolutionListJPanel panel;

    public EvolutionAddActionListener( final EvolutionListJPanel panel )
    {
        this.panel = panel;
    }
    
    @Override
    public void actionPerformed( ActionEvent e )
    {
        try
        {
            Window parent = SwingUtilities.getWindowAncestor( panel );
            AadlUIAction action = chooseEvolutionType( parent );
            
            if( action != null )
            {
                panel.add( action.execute( panel ) );
            }
        }
        catch( Exception err )
        {
            // do nothing
        }
    }
    
    private AadlUIAction chooseEvolutionType( final Window parent ) throws Exception
    {
        final EvolutionChooseJDialog d = new EvolutionChooseJDialog( parent );
        d.setVisible( true );
        d.dispose();
        
        if( !d.isSaved() )
        {
            throw new Exception( "User canceled the operation." );
        }
        
        return d.getEvolutionType();
    }
    
}