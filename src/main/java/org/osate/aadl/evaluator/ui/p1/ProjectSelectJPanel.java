package org.osate.aadl.evaluator.ui.p1;

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import org.osate.aadl.evaluator.project.Project;
import org.osate.aadl.evaluator.file.ProjectFile;

public class ProjectSelectJPanel extends JPanel
{
    private final String path;
    private JFileChooser chooser;

    public ProjectSelectJPanel( String path )
    {
        this.path = path;
        init();
    }
    
    private void init()
    {
        setLayout( new BorderLayout() );
        
        add( 
            chooser = new JFileChooser() , 
            BorderLayout.CENTER 
        );
        
        chooser.setControlButtonsAreShown( false );
        chooser.setMultiSelectionEnabled ( false );
        chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        chooser.setCurrentDirectory( new File( path ) );
    }
    
    public Project getProjectSelected() throws Exception
    {
        return ProjectFile.open( 
            chooser.getSelectedFile() 
        );
    }
    
}