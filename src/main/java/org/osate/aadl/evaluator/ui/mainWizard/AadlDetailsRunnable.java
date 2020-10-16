package org.osate.aadl.evaluator.ui.mainWizard;

import javax.swing.JTextArea;
import org.osate.aadl.evaluator.automatic.AutomaticEvolution;
import org.osate.aadl.evaluator.evolution.Candidate;
import org.osate.aadl.evaluator.evolution.Evolution;
import org.osate.aadl.evaluator.evolution.EvolutionUtils;

public class AadlDetailsRunnable implements Runnable
{
    private final JTextArea detailJTextArea;
    private final Evolution evolution;

    public AadlDetailsRunnable( JTextArea area , Evolution evolution )
    {
        this.detailJTextArea = area;
        this.evolution = evolution;
    }

    @Override
    public void run()
    {
        detailJTextArea.setText( "" );
                
        try
        {
            for( String text : EvolutionUtils.diff( evolution.getSystem() , evolution ) )
            {
                detailJTextArea.append( text );
                detailJTextArea.append( "\n\n" );
            }
        }
        catch( Exception err )
        {
            detailJTextArea.append( err.getMessage() );
            detailJTextArea.append( "\n\n" );
        }
        
        if( evolution != null )
        {
            detailJTextArea.append( "Functional Suitability:\n" );
            for( Candidate candidate : evolution.getCandidates() )
            {
                detailJTextArea.append( "  " + candidate.getComponent().getName() + " : " + candidate.getFuncionalitiesToString() );
                detailJTextArea.append( "\n" );
            }
        }
        
        detailJTextArea.append( "\n\n" );
        
        if( evolution instanceof AutomaticEvolution )
        {
            if( !((AutomaticEvolution) evolution).getMessages().isEmpty() )
            {
                detailJTextArea.append( "Messages:\n" );

                for( String text : ((AutomaticEvolution) evolution).getMessages() )
                {
                    detailJTextArea.append( text );
                    detailJTextArea.append( "\n" );
                }

                detailJTextArea.append( "\n\n" );
            }
        }
        
        detailJTextArea.setCaretPosition( 0 );
    }
    
}