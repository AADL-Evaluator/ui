package org.osate.aadl.evaluator.ui.mainWizard;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.jdesktop.swingx.JXTree;
import org.osate.aadl.evaluator.project.ComponentPackage;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.Declaration;
import org.osate.aadl.evaluator.project.Project;
import org.osate.aadl.evaluator.project.Subcomponent;
import org.osate.aadl.evaluator.ui.ComponentCodeJDialog;

public class SystemJPanel extends javax.swing.JPanel 
{
    private Project project;
    private JXTree tree;
    private DefaultMutableTreeNode root;
    private DefaultListModel listModel;
    
    private final List<List<Declaration>> declarations;
    
    public SystemJPanel()
    {
        declarations = new LinkedList<>();
        
        initComponents();
        init();
    }
    
    private void init()
    {
        jScrollPane1.setViewportView(
            tree = new JXTree(
                root = new DefaultMutableTreeNode( "Project" , true )
            )
        );
        
        tree.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if( e.getClickCount() >= 3 ){
                    selectJButton.doClick();
                }
            }
        });
        
        tree.getSelectionModel().setSelectionMode( 
            TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION 
        );
        
        selectedJList.setModel( listModel = new DefaultListModel() );
    }
    
    public void setProject( Project project )
    {
        this.project = project;
        rebuild();
    }
    
    private void rebuild()
    {
        root.removeAllChildren();
        
        for( ComponentPackage aadl : project.getPackages().values() )
        {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode( aadl , true );
            
            for( Component component : aadl.getComponents().values() )
            {
                if( !Component.TYPE_SYSTEM.equalsIgnoreCase( component.getType() )
                    || !component.isImplementation() )
                {
                    continue ;
                }
                
                node.add( createNode( component ) );
            }
            
            if( node.getChildCount() > 0 )
            {
                root.add( node );
            }
        }
        
        ((DefaultTreeModel) tree.getModel()).setRoot( root );
    }
    
    private DefaultMutableTreeNode createNode( Component component )
    {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode( component , true );
        
        if( !component.getFeatures().isEmpty() )
        {
            node.add( createNode( "Features" , component.getFeatures().values() ) );
        }
        
        if( !component.getSubcomponents().isEmpty() )
        {
            node.add( createNode( "Subcomponents" , component.getSubcomponents().values() ) );
        }
        
        if( !component.getConnections().isEmpty() )
        {
            node.add( createNode( "Connections" , component.getConnections().values() ) );
        }
        
        if( !component.getProperties().isEmpty() )
        {
            node.add( createNode( "Properties" , component.getProperties() ) );
        }
        
        return node;
    }
    
    private DefaultMutableTreeNode createNode( String name , Collection<? extends Declaration> declarations )
    {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode( name , true );
        
        for( Declaration declaration : declarations )
        {
            node.add( new DefaultMutableTreeNode( declaration ) );
        }

        return node;
    }
    
    public List<List<Declaration>> getDeclarationsSelected()
    {
        return declarations;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        selectJButton = new javax.swing.JButton();
        viewJButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        deleteJButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        selectedJList = new javax.swing.JList<>();

        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jSplitPane1.setDividerLocation(300);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);

        jLabel1.setText("System");
        jToolBar1.add(jLabel1);
        jToolBar1.add(filler1);

        selectJButton.setText("Select");
        selectJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectJButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(selectJButton);

        viewJButton.setText("View");
        viewJButton.setFocusable(false);
        viewJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        viewJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        viewJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewJButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(viewJButton);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.PAGE_START);
        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jToolBar2.setFloatable(false);

        jLabel2.setText("Component Selected");
        jToolBar2.add(jLabel2);
        jToolBar2.add(filler2);

        deleteJButton.setText("Delete");
        deleteJButton.setFocusable(false);
        deleteJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(deleteJButton);

        jPanel2.add(jToolBar2, java.awt.BorderLayout.PAGE_START);

        selectedJList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                selectedJListKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(selectedJList);

        jPanel2.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectJButtonActionPerformed
        if( tree.getSelectionPath() == null
            || tree.getSelectionPath().getPathCount() < 4 )
        {
            JOptionPane.showMessageDialog( 
                this , 
                "Please, select a subcomponent inside a system implementation." ,
                "Error" ,
                JOptionPane.ERROR_MESSAGE
            );
            
            return ;
        }
        
        // ---- //
        
        final List<Declaration> l = new LinkedList<>();
        String names = "";
        
        for( TreePath path : tree.getSelectionPaths() )
        {
            if( path.getPathCount() < 5 )
            {
                JOptionPane.showMessageDialog( 
                    this , 
                    "Please, select a subcomponent inside a system implementation." ,
                    "Error" ,
                    JOptionPane.ERROR_MESSAGE
                );
                
                return ;
            }
            
            // 0 - first, 1 - second, 
            // when count is 5, last element is 4
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getPathComponent( 4 );
            
            if( !l.isEmpty() 
                && !l.get( 0 ).getComponent().getType().equalsIgnoreCase( ((Declaration) node.getUserObject()).getComponent().getType() ) )
            {
                JOptionPane.showMessageDialog( 
                    this , 
                    "Please, select the same type of component (eg., all selected should be devices)." ,
                    "Error" ,
                    JOptionPane.ERROR_MESSAGE
                );
                
                return ;
            }
            
            l.add( (Declaration) node.getUserObject() );
            
            names += names.isEmpty() ? "" : ", ";
            names += ((Declaration) node.getUserObject()).getName();
        }
        
        // ----- verify it was added, same type and have the same features
        if( listModel.contains( names ) )
        {
            JOptionPane.showMessageDialog( 
                this , 
                l.size() == 1 ? "This declaration was added!" : "These declarations were added!" ,
                "Error" ,
                JOptionPane.ERROR_MESSAGE
            );

            return ;
        }
        
        declarations.add( l );
        listModel.addElement( names );
    }//GEN-LAST:event_selectJButtonActionPerformed

    private void deleteJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJButtonActionPerformed
        int index = selectedJList.getSelectedIndex();
        if( index == -1 )
        {
            return ;
        }
        
        declarations.remove( index );
        listModel.remove( index );
    }//GEN-LAST:event_deleteJButtonActionPerformed

    private void selectedJListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_selectedJListKeyPressed
        if( evt.getKeyCode() == KeyEvent.VK_DELETE )
        {
            deleteJButton.doClick();
        }
    }//GEN-LAST:event_selectedJListKeyPressed

    private void viewJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewJButtonActionPerformed
        if( tree.getSelectionPath() == null
            || tree.getSelectionPath().getPathCount() < 3 )
        {
            return ;
        }
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getPathComponent( 4 );
        Component component;
        
        if( node.getUserObject() instanceof Subcomponent )
        {
            component = ((Subcomponent) node.getUserObject()).getComponent();
        }
        else
        {
            node = (DefaultMutableTreeNode) tree.getSelectionPath().getPathComponent( 2 );
            component = (Component) node.getUserObject();
        }
        
        final ComponentCodeJDialog dialog = new ComponentCodeJDialog(
            SwingUtilities.getWindowAncestor( this )
        );
        dialog.setComponent( component );
        dialog.setVisible( true );
        dialog.dispose();
    }//GEN-LAST:event_viewJButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteJButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JButton selectJButton;
    private javax.swing.JList<String> selectedJList;
    private javax.swing.JButton viewJButton;
    // End of variables declaration//GEN-END:variables
}
