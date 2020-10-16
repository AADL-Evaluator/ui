package org.osate.aadl.evaluator.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Connection;
import org.osate.aadl.evaluator.project.Declaration;
import org.osate.aadl.evaluator.project.Subcomponent;

public class ProjectUtils 
{
    
    private ProjectUtils()
    {
        // do nothing
    }
    
    /**
     * return a list of bus connected to a processor.
     * 
     * @param system            System
     * @return                  String: processor       List: buses
     */
    public static Map<String,List<Subcomponent>> getBusesByProcessors( Component system )
    {
        Map<String,List<Subcomponent>> results = new HashMap<>();
        
        for( Connection connection : system.getConnectionsAll().values() )
        {
            // ------- get component
            Subcomponent compA = system.getSubcomponent( connection.getSubcomponentNameA() );
            Subcomponent compB = system.getSubcomponent( connection.getSubcomponentNameB() );
            
            if( compA == null || compB == null )
            {
                continue ;
            }
            
            // ------ select a bus and cpu
            Subcomponent bus = null;
            String cpu = null;
            
            if( compA.isBus() )
            {
                bus = compA;
            }
            else if( compB.isBus() )
            {
                bus = compB;
            }
            
            if( compA.isProcessor() )
            {
                cpu = connection.getSubcomponentNameA() 
                    + "." 
                    + connection.getFeatureNameA();
            }
            else if( compB.isProcessor() )
            {
                cpu = connection.getSubcomponentNameB() 
                    + "." 
                    + connection.getFeatureNameB();
            }
            
            // ------ ignore if not exist cpu and bus
            if( cpu == null || bus == null )
            {
                continue ;
            }
            
            if( !results.containsKey( cpu ) )
            {
                results.put( cpu , new LinkedList<Subcomponent>() );
            }
            
            results.get( cpu ).add( bus );
        }
        
        return results;
    }
    
    /**
     * return a list of connection between the declaration and a process.
     * 
     * @param declaration       the declaration will be check
     * @return                  the list of connection to a process
     */
    public static Set<Connection> getProcesses( Declaration declaration )
    {
        Set<Connection> results = new LinkedHashSet<>();
        
        if( !(declaration instanceof Subcomponent) )
        {
            return results;
        }
        
        final Subcomponent subcomponent = (Subcomponent) declaration;
        
        for( Connection connection : declaration.getParent().getConnections().values() )
        {
            Subcomponent compA = connection.getSubcomponentA();
            Subcomponent compB = connection.getSubcomponentB();
            
            if( compA == null 
                || compB == null 
                || ( compA != subcomponent && compB != subcomponent ) )
            {
                continue ;
            }
            
            if( compB.isProcess() 
                || compA.isProcess() )
            {
                results.add( connection );
            }
        }
        
        return results;
    }
    
    /**
     * Return a suggest name to avoid a conflit.
     * 
     * @param registred         all name registred
     * @param name              the original name
     * @return                  the original name + "_copy" + number
     */
    public static String getSuggestName( final Collection<String> registred , final String name )
    {
        int count = 0;
        
        String suggest = name;
        
        while( registred.contains( suggest ) )
        {
            suggest = name + "_copy" + count++;
        }
        
        return suggest;
    }
    
}