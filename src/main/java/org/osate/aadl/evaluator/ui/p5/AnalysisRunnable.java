package org.osate.aadl.evaluator.ui.p5;

import fluent.gui.impl.swing.FluentTable;
import org.osate.aadl.aadlevaluator.analysis.Analysis;
import org.osate.aadl.aadlevaluator.analysis.EvolutionAnalysis;
import org.osate.aadl.aadlevaluator.report.EvolutionReport;

/**
 * Em vez de utilizar essa classe, deve-se usar o ComparationReportRunnable.
 * 
 * 
 * @author avld
 * @deprecated
 */
@Deprecated
public class AnalysisRunnable implements Runnable
{
    private final FluentTable<Analysis> table;
    private final EvolutionReport original;
    private final EvolutionReport report;

    public AnalysisRunnable( FluentTable<Analysis> table , EvolutionReport o , EvolutionReport r )
    {
        this.table = table;
        this.original = o;
        this.report = r;
    }
    
    @Override
    public void run()
    {
        this.table.removeAll();
        
        if( original == null || report == null )
        {
            return ;
        }
        
        try
        {
            EvolutionAnalysis analysis = new EvolutionAnalysis();

            for( Analysis result : analysis.analysis( original , report ) )
            {
                table.addData( result );
            }
        }
        catch( Exception err )
        {
            err.printStackTrace();
        }
    }
    
}
