package org.osate.aadl.evaluator.ui.mainWizard;

import org.osate.aadl.evaluator.ui.p3.EvolutionListJPanel;
import java.util.List;
import org.osate.aadl.evaluator.evolution.Evolution;

public abstract class AadlUIAction 
{
    private final String description;

    public AadlUIAction( String description ) 
    {
        this.description = description;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public abstract List<? extends Evolution> execute( final EvolutionListJPanel panel ) throws Exception;
}
