package org.osate.aadl.evaluator.ui.mainWizard;

import javax.swing.JTextArea;
import org.osate.aadl.evaluator.evolution.Evolution;
import org.osate.aadl.evaluator.project.Component;

public class AadlComponentRunnable implements Runnable
{
    private final JTextArea area;
    private final Evolution evolution;
    private final Component system;

    public AadlComponentRunnable( JTextArea area , Evolution evolution , Component system )
    {
        this.area = area;
        this.evolution = evolution;
        this.system = system;
    }

    public AadlComponentRunnable( JTextArea area , Evolution evolution )
    {
        this.area = area;
        this.evolution = evolution;
        this.system = evolution == null ? null : evolution.getSystem();
    }
    
    @Override
    public void run() 
    {
        area.setText( AadlComponent.convert( evolution, system ) );
    }
    
}
