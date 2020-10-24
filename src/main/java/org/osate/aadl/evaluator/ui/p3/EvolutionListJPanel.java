package org.osate.aadl.evaluator.ui.p3;

import fluent.gui.impl.swing.FluentTable;
import fluent.gui.table.CustomTableColumn;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.osate.aadl.aadlevaluator.report.EvolutionReport;
import org.osate.aadl.aadlevaluator.report.ProjectReport;
import org.osate.aadl.aadlevaluator.report.util.ProjectReportUtils;
import org.osate.aadl.aadlevaluator.report.util.ResumeUtils;
import org.osate.aadl.evaluator.evolution.Evolution;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.ui.mainWizard.AadlDetailsRunnable;

public abstract class EvolutionListJPanel extends javax.swing.JPanel 
{
    private static final String EVOLUTION_NAME = "Change {0}";
    
    private final Executor executor = Executors.newSingleThreadExecutor();
    
    private ProjectReport projectReport;
    
    private Component systemOriginal;
    private FluentTable<EvolutionReport> table;
    private int counter;
    
    public EvolutionListJPanel() 
    {
        initComponents();
        init();
    }
    
    private void init()
    {
        jScrollPane1.setViewportView(
            table = new FluentTable<>( "changes" )
        );
        
        table.addColumn( new CustomTableColumn<EvolutionReport,String>( "Change #" , 50 ){
            @Override
            public String getValue( int index , EvolutionReport report ) {
                return "Change " + (index + 1);
            }
        });
        
        table.setUp();
        
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                table.setTabelaVaziaMensagem( "No change." );
                table.setTabelaVazia();
            }
        } );
        
        table.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
            @Override
            public void valueChanged( ListSelectionEvent e ) {
                detailsJTextArea.setText( "" );
                
                EvolutionReport report = table.getSelectedObject();
                
                if( report == null )
                {
                    return ;
                }
                
                executor.execute( new AadlDetailsRunnable( detailsJTextArea  , report.getEvolution() ) );
            }
        });
        
        // --- //
        
        addJButton.addActionListener( new EvolutionAddActionListener( this ) );
        deleteJButton.addActionListener( new EvolutionDeleteActionListener( table ) );
        
        /*
        factorJButton.addActionListener( new FactorActionListener( this ) );
        
        reportJButton.addActionListener( new ReportActionListener( table ){
            @Override
            public ProjectReport getProjectReport() {
                return projectReport;
            }
        });
        */
    }

    public void setSystemOriginal( Component system )
    {
        this.systemOriginal = system;
        
        this.projectReport = new ProjectReport( system.getName() );
        this.projectReport.add( ProjectReportUtils.create( "original" , systemOriginal ) );
    }

    public Component getSystemOriginal()
    {
        return systemOriginal;
    }
    
    public void add( Evolution evolution )
    {
        executor.execute( new Runnable() {
            @Override
            public void run() {
                try
                {
                    EvolutionReport report = ProjectReportUtils.create( createEvolutionName() , evolution );
                    System.out.println( report.toString() );
                    
                    projectReport.add( report );
                    ResumeUtils.getResume( projectReport , projectReport.getResume() );
                    
                    table.addData( report );
                }
                catch( Throwable err )
                {
                    err.printStackTrace();
                }
            }
        });
    }
    
    public void add( Collection<? extends Evolution> evolutions )
    {
        for( Evolution evolution : evolutions )
        {
            executor.execute( new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        EvolutionReport report = ProjectReportUtils.create( createEvolutionName() , evolution );
                        System.out.println( report.toString() );

                        projectReport.add( report );
                        table.addData( report );
                    }
                    catch( Throwable err )
                    {
                        err.printStackTrace();
                    }
                }
            });
        }
        
        executor.execute( new Runnable() {
            @Override
            public void run() {
                ResumeUtils.getResume( projectReport , projectReport.getResume() );
            }
        });
        
        if( evolutions != null && !evolutions.isEmpty() )
        {
            executor.execute( new Runnable() {
                @Override
                public void run() {
                    proximaPagina();
                }
            });
        }
    }
    
    public List<EvolutionReport> getEvolutions()
    {
        return table.getTabelModel().getData();
    }

    public ProjectReport getProjectReport() 
    {
        return projectReport;
    }
    
    public FluentTable<EvolutionReport> getTable()
    {
        return table;
    }
    
    private String createEvolutionName()
    {
        return MessageFormat.format( EVOLUTION_NAME , counter++ );
    }
    
    public abstract void proximaPagina();
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        addJButton = new javax.swing.JButton();
        editJButton = new javax.swing.JButton();
        deleteJButton = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        detailsJTextArea = new javax.swing.JTextArea();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);

        jLabel1.setText("Changes");
        jToolBar1.add(jLabel1);
        jToolBar1.add(filler1);

        addJButton.setText("Add");
        addJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addJButton.setFocusable(false);
        addJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(addJButton);

        editJButton.setText("Edit");
        editJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editJButton.setFocusable(false);
        editJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(editJButton);

        deleteJButton.setText("Delete");
        deleteJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteJButton.setFocusable(false);
        deleteJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(deleteJButton);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setDividerLocation(230);
        jSplitPane1.setLeftComponent(jScrollPane1);

        detailsJTextArea.setEditable(false);
        detailsJTextArea.setColumns(20);
        detailsJTextArea.setFont(new java.awt.Font("Courier 10 Pitch", 0, 14)); // NOI18N
        detailsJTextArea.setRows(5);
        jScrollPane4.setViewportView(detailsJTextArea);

        jTabbedPane1.addTab("Change Details", jScrollPane4);

        jSplitPane1.setRightComponent(jTabbedPane1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addJButton;
    private javax.swing.JButton deleteJButton;
    private javax.swing.JTextArea detailsJTextArea;
    private javax.swing.JButton editJButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
