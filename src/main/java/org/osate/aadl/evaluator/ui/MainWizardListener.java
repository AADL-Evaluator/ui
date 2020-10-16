package org.osate.aadl.evaluator.ui;

import fluent.gui.impl.swing.wizard.WizardAdapter;
import javax.swing.JOptionPane;

public class MainWizardListener extends WizardAdapter
{
    private final MainWizardJFrame frame;

    public MainWizardListener( MainWizardJFrame frame )
    {
        this.frame = frame;
    }

    @Override
    public boolean canChangePage( int current , int next )
    {
        try
        {
            return change( current , next );
        }
        catch( Exception err )
        {
            err.printStackTrace();

            JOptionPane.showMessageDialog( 
                frame , 
                err.getMessage() ,
                "Error" ,
                JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }
    
    private boolean change( int current , int next ) throws Exception
    {
        if( current == 0 )
        {
            // recuperando o projeto selecionado
            frame.setProject( 
                frame.getProjectSelectPanel().getProjectSelected()
            );
        }
        else if( current == 1 && next == 2 )
        {
            frame.getEvolutionsPanel().setSystemOriginal(           // 3º View
                frame.getSystemSelectPanel().getSystemSelected()    // 2º View
            );
        }
        else if( current == 2 && next == 3 )
        {
            frame.getFactorsPanel().setProjectReport(               // 4º View
                frame.getEvolutionsPanel().getProjectReport()       // 3º View
            );
        }
        else if( current == 3 && next == 4 )
        {
            frame.getResultListPanel().setProjectReport(           // 5º View
                frame.getFactorsPanel().getProjectReport()         // 4º View
            );
        }
        
        return true;
    }
    
}