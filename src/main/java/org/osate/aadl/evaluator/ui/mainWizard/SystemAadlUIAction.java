package org.osate.aadl.evaluator.ui.mainWizard;

import org.osate.aadl.evaluator.ui.p3.EvolutionListJPanel;
import java.util.Arrays;
import java.util.List;
import javax.swing.SwingUtilities;
import org.osate.aadl.evaluator.evolution.Evolution;
import org.osate.aadl.evaluator.ui.edit.ComponentJDialog;

public class SystemAadlUIAction extends AadlUIAction
{

    public SystemAadlUIAction()
    {
        super( "Modify completely the system" );
    }
    
    @Override
    public List<Evolution> execute( final EvolutionListJPanel panel ) throws Exception
    {
        Evolution evolution = new Evolution( panel.getSystemOriginal() );
        
        final ComponentJDialog dialog = new ComponentJDialog(
            SwingUtilities.getWindowAncestor( panel )
        );
        
        dialog.setComponent( evolution.getSystem() );
        dialog.setVisible( true );
        dialog.dispose();
        
        if( !dialog.isSaved() )
        {
            throw new Exception( "User canceled the operation." );
        }
        
        evolution.setSystem( dialog.getComponent() );
        
        return Arrays.asList( evolution );
    }
    
}
