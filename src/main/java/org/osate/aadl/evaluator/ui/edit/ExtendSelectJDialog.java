package org.osate.aadl.evaluator.ui.edit;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import org.jdesktop.swingx.JXHeader;
import org.jdesktop.swingx.JXTree;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.ComponentPackage;
import org.osate.aadl.evaluator.project.Project;
import org.osate.aadl.evaluator.ui.JTreeUtils;

public class ExtendSelectJDialog extends javax.swing.JDialog 
{
    private Project project;
    private String type;
    
    private JXTree tree;
    private DefaultMutableTreeNode root;
    
    private boolean saved;
    
    public ExtendSelectJDialog( java.awt.Window parent )
    {
        super( parent );
        
        initComponents();
        init();
        
        setTitle( "Extend Select" );
        setSize( 400 , 600 );
        setModal( true );
        setLocationRelativeTo( parent );
    }
    
    private void init()
    {
        add( new JXHeader( 
            "Extend Select" , 
            "Please, select a component to extend it." 
        ) , BorderLayout.NORTH );
        
        jScrollPane1.setViewportView( 
            tree = new JXTree( 
                root = new DefaultMutableTreeNode( "Project" ) 
            ) 
        );
        
        tree.getSelectionModel().setSelectionMode( 
            TreeSelectionModel.SINGLE_TREE_SELECTION 
        );
        
        tree.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                if( e.getClickCount() >= 2 ){
                    saveJButton.doClick();
                }
            }
        });
        
        tree.setRootVisible( false );
    }

    public boolean isSaved()
    {
        return saved;
    }
    
    public Component getSelected()
    {
        return (Component)((DefaultMutableTreeNode) tree.getSelectionPath()
            .getPathComponent( 2 ))
            .getUserObject();
    }
    
    public void setProjectAndType( Project project , String type )
    {
        this.project = project;
        this.type = type;
        
        rebuild();
    }
    
    private void rebuild()
    {
        root.removeAllChildren();
        
        for( ComponentPackage pack : project.getPackages().values() )
        {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode( pack );
            
            for( Component component : pack.getComponents().values() )
            {
                if( !component.getType().equalsIgnoreCase( type ) )
                {
                    continue ;
                }
                
                node.add( new DefaultMutableTreeNode( component ) );
            }
            
            if( !node.isLeaf() )
            {
                root.add( node );
            }
        }
        
        ((DefaultTreeModel) tree.getModel()).setRoot( root );
        JTreeUtils.setTreeExpandedState( tree , true );
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        saveJButton = new javax.swing.JButton();
        cancelJButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jToolBar1.setFloatable(false);
        jToolBar1.add(filler1);

        saveJButton.setText("Save");
        saveJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveJButton.setFocusable(false);
        saveJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveJButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(saveJButton);

        cancelJButton.setText("Cancel");
        cancelJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelJButton.setFocusable(false);
        cancelJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelJButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(cancelJButton);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJButtonActionPerformed
        if( tree.getSelectionPath() == null
            || tree.getSelectionPath().getPathCount() <= 2 )
        {
            JOptionPane.showMessageDialog( 
                this , 
                "Please, select a component." ,
                "Error" ,
                JOptionPane.ERROR_MESSAGE
            );
            
            return ;
        }
        
        saved = true;
        setVisible( false );
    }//GEN-LAST:event_saveJButtonActionPerformed

    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
        saved = false;
        setVisible( false );
    }//GEN-LAST:event_cancelJButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelJButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton saveJButton;
    // End of variables declaration//GEN-END:variables
}
