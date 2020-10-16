package org.osate.aadl.evaluator.ui.p5;

import fluent.gui.impl.swing.FluentTable;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import org.osate.aadl.aadlevaluator.analysis.Analysis;
import org.osate.aadl.aadlevaluator.analysis.EvolutionAnalysis;
import org.osate.aadl.aadlevaluator.report.EvolutionReport;
import org.osate.aadl.aadlevaluator.report.ProjectReport;
import org.osate.aadl.aadlevaluator.report.util.ProjectReportUtils;
import org.osate.aadl.evaluator.evolution.EvolutionUtils;
import org.osate.aadl.evaluator.ui.mainWizard.AadlComponent;

public abstract class ReportActionListener implements ActionListener
{
    private final FluentTable<EvolutionReport> table;

    public ReportActionListener( final FluentTable<EvolutionReport> table )
    {
        this.table = table;
    }
    
    public abstract ProjectReport getProjectReport();
    
    @Override
    public void actionPerformed( ActionEvent e ) 
    {
        if( table.getRowCount() <= 0 )
        {
            return ;
        }
        
        try
        {
            File dir = ProjectReportUtils.generator( getProjectReport() );
            
            createAnalysis( dir , table.getTabelModel().getData() );
            createAADL( dir , table.getTabelModel().getData() );
            
            Desktop.getDesktop().open( dir );
        }
        catch( Exception err )
        {
            err.printStackTrace();
        }
    }
    
    private void createAnalysis( File mainDir , List<EvolutionReport> reports ) throws Exception
    {
        File dir = new File( mainDir , "analysis" );
        dir.mkdirs();
        
        EvolutionReport original = getProjectReport().getReports().get( "original" );
        
        for( EvolutionReport report : reports )
        {
            if( report.getEvolution() == null )
            {
                continue ;
            }
            
            File file = new File( dir , report.getName() + ".txt" );
            
            try( FileWriter writer = new FileWriter( file ) )
            {
                for( String text : EvolutionUtils.diff( report.getEvolution().getSystem() , report.getEvolution() ) )
                {
                    writer.append( text );
                    writer.append( "\n\n" );
                }
                
                for( Analysis analysis : new EvolutionAnalysis()
                        .analysis( original , report ) )
                {
                    writer.write( analysis.toString() );
                    writer.write( System.lineSeparator() );
                }
            }
        }
    }
    
    private void createAADL( File mainDir , List<EvolutionReport> reports ) throws Exception
    {
        File dir = new File( mainDir , "AADL" );
        dir.mkdirs();
        
        for( EvolutionReport report : reports )
        {
            if( report.getEvolution() == null )
            {
                continue ;
            }
            
            File file = new File( dir , report.getName() + ".aadl" );
            
            try( FileWriter writer = new FileWriter( file ) )
            {
                writer.append( 
                    AadlComponent.convert( report.getEvolution()  ) 
                );
            }
        }
    }
    
}
