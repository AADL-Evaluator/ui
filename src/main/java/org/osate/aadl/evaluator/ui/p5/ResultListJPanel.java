package org.osate.aadl.evaluator.ui.p5;

import fluent.gui.impl.swing.FluentTable;
import fluent.gui.table.CustomTableColumn;
import fluent.gui.table.FieldTableColumn;
import java.math.BigDecimal;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.osate.aadl.aadlevaluator.analysis.Analysis;
import org.osate.aadl.aadlevaluator.report.EvolutionReport;
import org.osate.aadl.aadlevaluator.report.ProjectReport;
import org.osate.aadl.evaluator.ui.mainWizard.AadlComponentRunnable;
import org.osate.aadl.evaluator.ui.mainWizard.AadlDetailsRunnable;
import org.osate.aadl.evaluator.ui.mainWizard.AnalysisRunnable;

public class ResultListJPanel extends javax.swing.JPanel 
{
    private final Executor executor = Executors.newSingleThreadExecutor();
    
    private ProjectReport projectReport;
    
    private FluentTable<EvolutionReport> reportJable;
    private FluentTable<Analysis> analysisTable;
    
    public ResultListJPanel() 
    {
        initComponents();
        init();
    }
    
    private void init()
    {
        jScrollPane1.setViewportView(reportJable = new FluentTable<>( "changes" )
        );
        
        analysisJScrollPane.setViewportView( 
            analysisTable = new FluentTable<>( "analysis" )
        );
        
        analysisTable.addColumn( new CustomTableColumn<Analysis,String>( "Characteristic" ){
            @Override
            public String getValue( Analysis analysis ) {
                return analysis.getCharacteristic();
            }
        });
        
        analysisTable.addColumn( new CustomTableColumn<Analysis,String>( "Sub" ){
            @Override
            public String getValue( Analysis analysis ) {
                return analysis.getSubcharacteristic();
            }
        } );
        
        analysisTable.addColumn( new CustomTableColumn<Analysis,String>( "Result" ){
            @Override
            public String getValue( Analysis analysis ) {
                return analysis.toString();
            }
        });
        
        analysisTable.setUp();
        
        // --------------------- //
        
        reportJable.addColumn( new CustomTableColumn<EvolutionReport,String>( "Change #" , 50 ){
            @Override
            public String getValue( int index , EvolutionReport report ) {
                return report.getName();
            }
        });
        
        reportJable.addColumn( new CustomTableColumn<EvolutionReport,BigDecimal>( "Factor" , 50 ){
            @Override
            public BigDecimal getValue( int index , EvolutionReport report ) {
                return report.getFactor();
            }
        });
        
        reportJable.setUp();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                reportJable.setTabelaVaziaMensagem( "No change." );
                reportJable.setTabelaVazia();
            }
        } );
        
        reportJable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged( ListSelectionEvent e ) {
                detailsJTextArea.setText( "" );
                changeJTextArea .setText( "" );
                analysisTable.setData( null );
                
                EvolutionReport report = reportJable.getSelectedObject();
                
                if( report == null )
                {
                    return ;
                }
                
                EvolutionReport original = getProjectReport().getReports().get( "original" );
                
                executor.execute( new AadlDetailsRunnable( detailsJTextArea  , report.getEvolution() ) );
                executor.execute( new AadlComponentRunnable( changeJTextArea , report.getEvolution() ) );
                executor.execute( new AnalysisRunnable( analysisTable , original , report ) );
            }
        });
        
        reportJButton.addActionListener(new ReportActionListener( reportJable ){
            @Override
            public ProjectReport getProjectReport() {
                return projectReport;
            }
        });
    }

    public void setProjectReport( ProjectReport projectReport )
    {
        this.projectReport = projectReport;
        reportJable.setData( projectReport.getReports().values() );
    }
    
    public ProjectReport getProjectReport() 
    {
        return projectReport;
    }
    
    public FluentTable<EvolutionReport> getTable()
    {
        return reportJable;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        reportJButton = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        detailsJTextArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        changeJTextArea = new javax.swing.JTextArea();
        analysisJScrollPane = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);

        jLabel1.setText("Results");
        jToolBar1.add(jLabel1);
        jToolBar1.add(filler1);

        reportJButton.setText("Report");
        reportJButton.setFocusable(false);
        reportJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reportJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(reportJButton);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setDividerLocation(230);
        jSplitPane1.setLeftComponent(jScrollPane1);

        detailsJTextArea.setEditable(false);
        detailsJTextArea.setColumns(20);
        detailsJTextArea.setFont(new java.awt.Font("Courier 10 Pitch", 0, 14)); // NOI18N
        detailsJTextArea.setRows(5);
        jScrollPane4.setViewportView(detailsJTextArea);

        jTabbedPane1.addTab("Change Details", jScrollPane4);

        changeJTextArea.setEditable(false);
        changeJTextArea.setColumns(20);
        changeJTextArea.setFont(new java.awt.Font("Courier 10 Pitch", 0, 14)); // NOI18N
        changeJTextArea.setRows(5);
        jScrollPane3.setViewportView(changeJTextArea);

        jTabbedPane1.addTab("Change View", jScrollPane3);
        jTabbedPane1.addTab("Analysis", analysisJScrollPane);

        jSplitPane1.setRightComponent(jTabbedPane1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane analysisJScrollPane;
    private javax.swing.JTextArea changeJTextArea;
    private javax.swing.JTextArea detailsJTextArea;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton reportJButton;
    // End of variables declaration//GEN-END:variables
}
