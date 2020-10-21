package org.osate.aadl.evaluator.ui.p4;

import fluent.gui.impl.swing.FluentTable;
import fluent.gui.table.CustomTableColumn;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.osate.aadl.aadlevaluator.report.EvolutionReport;
import org.osate.aadl.aadlevaluator.report.ProjectReport;
import org.osate.aadl.aadlevaluator.report.ReportFactor;
import org.osate.aadl.aadlevaluator.report.gui.FactorListJPanel;
import org.osate.aadl.aadlevaluator.report.util.ResumeUtils;

public class FactorEvaluateJPanel extends javax.swing.JPanel 
{
    private final Executor executor = Executors.newSingleThreadExecutor();
    
    private ProjectReport projectReport;
    
    private FactorListJPanel factorListJPanel;
    private FluentTable<EvolutionReport> table;
    
    public FactorEvaluateJPanel() 
    {
        initComponents();
        init();
    }
    
    private void init()
    {
        jScrollPane1.setViewportView(
            table = new FluentTable<>( "changes" )
        );
        
        jSplitPane1.setRightComponent( 
            factorListJPanel = new FactorListJPanel(){
                @Override
                public void save( final EvolutionReport resume , final Collection<ReportFactor> factors ) {
                    projectReport.setResume( resume );
                    ResumeUtils.caculate( projectReport );
                    
                    table.getTabelModel().fireTableDataChanged();
                }
            }
        );
        
        table.addColumn( new CustomTableColumn<EvolutionReport,String>( "Change #" , 50 ){
            @Override
            public String getValue( int index , EvolutionReport report ) {
                return "Change " + (index + 1);
            }
        });
        
        table.addColumn( new CustomTableColumn<EvolutionReport,Double>( "Rank" , 50 ){
            @Override
            public Double getValue( int index , EvolutionReport report ) {
                return report.getFactor().doubleValue();
            }
        });
        
        table.setUp();
        
        // ------- //
        
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                TableRowSorter<TableModel> sorter = new TableRowSorter<>( table.getModel() );
                sorter.setComparator( 0 , new Comparator<String>() {
                    @Override
                    public int compare( String name1 , String name2 ) {
                        System.out.println( "Ordenando..." );
                        return getNumber( name1 ) - getNumber( name2 );
                    }

                    private int getNumber( String name ){
                        if( name == null && !name.contains( " " ) ) return 0;

                        String value = name.substring( name.lastIndexOf( " " ) + 1 );
                        if( value == null || name.trim().isEmpty() ) return 0;

                        return Integer.parseInt( name );
                    }
                });
            }
        });
        
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                table.setTabelaVaziaMensagem( "No change." );
                table.setTabelaVazia();
            }
        } );
    }
    
    public List<EvolutionReport> getEvolutions()
    {
        return table.getTabelModel().getData();
    }

    public void setProjectReport( ProjectReport projectReport )
    {
        this.projectReport = projectReport;
        table.setData( projectReport.getReports().values() );
        
        factorListJPanel.setFactors( projectReport.getResume() );
        
        executor.execute( new Runnable() {
            @Override
            public void run() {
                ResumeUtils.getResume( projectReport , projectReport.getResume() );
            }
        });
    }
    
    public ProjectReport getProjectReport() 
    {
        return projectReport;
    }
    
    public FluentTable<EvolutionReport> getTable()
    {
        return table;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(250);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);

        jLabel1.setText("Changes");
        jToolBar1.add(jLabel1);
        jToolBar1.add(filler1);

        jLabel3.setText("          ");
        jToolBar1.add(jLabel3);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.PAGE_START);
        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
