package org.osate.aadl.evaluator.ui;

import fluent.gui.impl.swing.FluentWizardPanel;
import fluent.gui.impl.swing.wizard.PaginaPanel;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Project;
import org.osate.aadl.evaluator.ui.p3.EvolutionListJPanel;
import org.osate.aadl.evaluator.ui.p4.FactorEvaluateJPanel;
import org.osate.aadl.evaluator.ui.p1.ProjectSelectJPanel;
import org.osate.aadl.evaluator.ui.p2.SystemSelectJPanel;
import org.osate.aadl.evaluator.ui.p5.ResultListJPanel;

public class MainWizardJFrame extends javax.swing.JFrame 
{
    public static final String CONFIG_FILE = "config.xml";
    
    private FluentWizardPanel wizard;
    private Project project;
    
    private ProjectSelectJPanel projectSelectPanel;
    private SystemSelectJPanel systemSelectPanel;
    private EvolutionListJPanel evolutionsPanel;
    private FactorEvaluateJPanel factorsPanel;
    private ResultListJPanel resultListPanel;
    
    private Properties properties;
    
    public MainWizardJFrame() 
    {
        initComponents();
        init();
        
        setTitle( "DevCompatibility" );     // titulo da tela
        setSize( 1000 , 600 );              // tamanho da tela
        setLocationRelativeTo( null );      // tela centralizada
    }
    
    private void init()
    {
        setLayout( new BorderLayout() );
        
        add( wizard = new FluentWizardPanel( "DevCompatibility" ) {
            @Override
            public void fechar() {
                System.exit( 0 );
            }
        } , BorderLayout.CENTER );
        
        wizard.setActionNames( 
            "Cancel" , 
            "Back" , 
            "Next" , 
            "Finish" 
        );
        
        initProperties();
        wizard.addListener( new MainWizardListener( this ) );
        addPages();
    }
    
    private void addPages()
    {
        wizard.getPaginas().add( 
            new PaginaPanel( 
                projectSelectPanel = new ProjectSelectJPanel( 
                    properties.getProperty( "path" , "./" ) 
                )  , 
                "Please, select a AADL Project." 
            )
        );
        
        wizard.getPaginas().add(new PaginaPanel( 
                systemSelectPanel = new SystemSelectJPanel() {
                    @Override
                    public void setSelect(Component system) {
                        wizard.proxima();
                    }
                } , 
                "Please, select a system to evalute it."
            )
        );
        
        wizard.getPaginas().add(new PaginaPanel( 
                evolutionsPanel = new EvolutionListJPanel(){
                    @Override
                    public void proximaPagina() {
                        wizard.proxima();
                    }
                }, 
                "Please, add how many evoluation do you want."
            )
        );
        
        wizard.getPaginas().add(new PaginaPanel( 
                factorsPanel = new FactorEvaluateJPanel() , 
                "Please, set the factor."
            )
        );
        
        wizard.getPaginas().add(new PaginaPanel( 
                resultListPanel = new ResultListJPanel() , 
                "Results based on evolutions."
            )
        );
        
        wizard.setPaginaAtual( -1 );
        wizard.proxima();
    }
    
    private void initProperties()
    {
        properties = new Properties();
        
        try
        {
            File file = new File( CONFIG_FILE );
            if( !file.exists() )
            {
                properties.setProperty( "path" , "./" );
                save();
            }

            try( FileInputStream fis = new FileInputStream( CONFIG_FILE ) )
            {
                properties.loadFromXML( fis );
            }
            catch( Exception err )
            {
                err.printStackTrace();
            }
        }
        catch( Exception err )
        {
            err.printStackTrace();
        }
        
        
    }
    
    private void save()
    {
        try
        {
            File file = new File( CONFIG_FILE );
            if( !file.exists() )
            {
                file.createNewFile();
            }

            try( FileOutputStream fis = new FileOutputStream( CONFIG_FILE ) )
            {
                properties.storeToXML( fis , "config file" );
            }
            catch( Exception err )
            {
                err.printStackTrace();
            }
        }
        catch( Exception err )
        {
            err.printStackTrace();
        }
    }
    
    // -------------------------------------------
    // -------------------------------------------
    // -------------------------------------------

    public void setProject( Project project )
    {
        this.project = project;
        this.systemSelectPanel.setProject( project );
        
        properties.setProperty( 
            "path" , 
            project.getDirectory().getParentFile().getAbsolutePath() 
        );
        
        save();
    }
    
    public Project getProject() 
    {
        return project;
    }

    public ProjectSelectJPanel getProjectSelectPanel() 
    {
        return projectSelectPanel;
    }

    public SystemSelectJPanel getSystemSelectPanel()
    {
        return systemSelectPanel;
    }

    public EvolutionListJPanel getEvolutionsPanel()
    {
        return evolutionsPanel;
    }

    public FactorEvaluateJPanel getFactorsPanel() 
    {
        return factorsPanel;
    }

    public ResultListJPanel getResultListPanel()
    {
        return resultListPanel;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 85, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
