package org.osate.aadl.evaluator.ui.mainWizard;

import java.util.Collection;
import org.osate.aadl.evaluator.evolution.Evolution;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Declaration;

public class AadlComponent
{
    
    private AadlComponent()
    {
        // do nothing
    }
    
    public static String convert( Evolution evolution ) 
    {
        return convert( 
            evolution , 
            evolution == null ? null : evolution.getSystem() 
        );
    }
    
    public static String convert( Evolution evolution , Component system ) 
    {
        StringBuilder builder = new StringBuilder();
        
        if( evolution == null || system == null )
        {
            return builder.toString();
        }
        
        builder.append( toString( system ) );
        
        for( Component component : evolution.getComponents().values() )
        {
            builder.append( "\n" );
            builder.append( toString( component ) );
        }
        
        return builder.toString();
    }
    
    private static String toString( Component component )
    {
        if( component == null )
        {
            return "";
        }
        
        StringBuilder builder = new StringBuilder();
        
        // first line
        builder.append( component.getType() );
        builder.append( component.isImplementation() ? " implementation " : " " );
        builder.append( component.getName() );
        builder.append( component.getExtend() == null || component.getExtend().trim().isEmpty()
            ? "\n" 
            : " extend " + component.getExtend() + "\n" 
        );
        
        toString( builder , "features"      , component.getFeaturesAll().values()      , ":" );
        toString( builder , "subcomponents" , component.getSubcomponentsAll().values() , ":" );
        toString( builder , "connections"   , component.getConnectionsAll().values()   , ":" );
        toString( builder , "properties"    , component.getPropertiesAll()             , "=>" );
        
        // last line
        builder.append( "end " );
        builder.append( component.getName() );
        builder.append( ";\n" );
        
        return builder.toString();
    }
    
    private static void toString( StringBuilder builder , String name , Collection<? extends Declaration> declarations , String connector )
    {
        if( declarations.isEmpty() )
        {
            return ;
        }
        
        builder.append( "\n  " );
        builder.append( name );
        builder.append( "\n" );
                
        for( Declaration d : declarations )
        {
            builder.append( "    " );
            builder.append( d.getName() );
            builder.append( " " );
            builder.append( connector );
            builder.append( " " );
            builder.append( d.getValue() );
            builder.append( ";\n" );
        }
        
        builder.append( "\n" );
    }
    
}
