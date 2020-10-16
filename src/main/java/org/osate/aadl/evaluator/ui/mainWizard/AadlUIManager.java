package org.osate.aadl.evaluator.ui.mainWizard;

import java.util.LinkedList;
import java.util.List;

public class AadlUIManager 
{
    private static AadlUIManager instance;

    private final List<AadlUIAction> options;
    
    private AadlUIManager() 
    {
        options = new LinkedList<>();
    }

    public static AadlUIManager getInstance()
    {
        if( instance == null )
        {
            instance = new AadlUIManager();
        }
        
        return instance;
    }

    public void add( AadlUIAction action )
    {
        options.add( action );
    }
    
    public List<AadlUIAction> getOptions()
    {
        return options;
    }
    
    public AadlUIAction get( int row )
    {
        return options.get( row );
    }
    
}
