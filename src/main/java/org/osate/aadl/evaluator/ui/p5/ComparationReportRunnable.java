package org.osate.aadl.evaluator.ui.p5;

import fluent.gui.impl.swing.FluentTable;
import org.osate.aadl.aadlevaluator.report.EvolutionReport;
import org.osate.aadl.aadlevaluator.report.comparation.ComparationReport;
import org.osate.aadl.aadlevaluator.report.comparation.ComparationReportUtils;

public class ComparationReportRunnable implements Runnable
{
    private final FluentTable<ComparationReport> table;
    private final EvolutionReport original;
    private final EvolutionReport report;
    
    public ComparationReportRunnable( FluentTable<ComparationReport> table , EvolutionReport o , EvolutionReport r )
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
            table.addData( 
                ComparationReportUtils.compare( report , original ) 
            );
        }
        catch( Exception err )
        {
            err.printStackTrace();
        }
    }
    
}
