package org.osate.aadl.evaluator.ui.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.osate.aadl.evaluator.project.Declaration;
import org.osate.aadl.evaluator.project.Feature;
import org.osate.aadl.evaluator.project.Property;
import org.osate.aadl.evaluator.project.Subcomponent;

public class DeclarationDialogActionListener implements ActionListener
{
    private final ComponentJDialog parent;
    private final boolean added;
    
    public DeclarationDialogActionListener( ComponentJDialog dialog , boolean added )
    {
        this.parent = dialog;
        this.added = added;
    }
    
    @Override
    public void actionPerformed( ActionEvent e )
    {
        Declaration declaration = added 
            ? parent.getDeclarationNew() 
            : parent.getDeclaration();
        
        if( declaration == null )
        {
            return ;
        }
        
        final DeclarationJDialog dialog = new DeclarationJDialog( parent );
        dialog.setTitle( getTitle( declaration ) );
        dialog.setDeclaration( declaration );
        dialog.setVisible( true );
        dialog.dispose();
        
        if( dialog.isSaved() )
        {
            if( added )
            {
                parent.addDeclaration( dialog.getDeclaration() );
            }
            else
            {
                parent.setDeclaration( dialog.getDeclaration() );
            }
        }
    }
    
    private String getTitle( Declaration declaration )
    {
        if( declaration instanceof Feature )
        {
            return "Feature";
        }
        else if( declaration instanceof Subcomponent )
        {
            return "Subcomponent";
        }
        else if( declaration instanceof Property )
        {
            return "Property";
        }
        else
        {
            return "Connection";
        }
    }
    
}