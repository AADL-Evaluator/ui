package org.osate.aadl.evaluator.ui.p4;

import org.osate.aadl.evaluator.ui.p3.EvolutionListJPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import org.osate.aadl.aadlevaluator.report.EvolutionReport;
import org.osate.aadl.aadlevaluator.report.gui.FactorListJDialog;
import org.osate.aadl.aadlevaluator.report.util.ResumeUtils;

public class FactorActionListener implements ActionListener
{
    private final EvolutionListJPanel panel;

    public FactorActionListener( EvolutionListJPanel panel )
    {
        this.panel = panel;
    }
    
    @Override
    public void actionPerformed( ActionEvent e ) 
    {
        if( panel.getTable().getRowCount() <= 0 )
        {
            return ;
        }
        
        EvolutionReport cloned = panel.getProjectReport().getResume().clone();
        
        FactorListJDialog dialog = new FactorListJDialog( 
            SwingUtilities.getWindowAncestor( panel ) 
        );
        
        dialog.setFactors( cloned );
        dialog.setVisible( true );
        dialog.dispose();
        
        if( dialog.isSaved() )
        {
            panel.getProjectReport().setResume( cloned );
            ResumeUtils.caculate( panel.getProjectReport() );
            
            panel.getTable().getTabelModel().fireTableDataChanged();
        }
    }
    
}