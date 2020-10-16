package org.osate.aadl.evaluator.ui.edit;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jdesktop.swingx.JXHeader;
import org.jdesktop.swingx.JXTree;
import org.osate.aadl.evaluator.project.Component;
import org.osate.aadl.evaluator.project.ComponentPackage;
import org.osate.aadl.evaluator.project.Connection;
import org.osate.aadl.evaluator.project.Declaration;
import org.osate.aadl.evaluator.project.Feature;
import org.osate.aadl.evaluator.project.Property;
import org.osate.aadl.evaluator.project.Subcomponent;
import org.osate.aadl.evaluator.ui.JTreeUtils;

public class ComponentJDialog extends javax.swing.JDialog 
{
    private Component component;
    
    private JXTree tree;
    private DefaultTreeModel treeModel;
    
    private DefaultMutableTreeNode rootNode;
    private DefaultMutableTreeNode featuresNode;
    private DefaultMutableTreeNode subcomponentsNode;
    private DefaultMutableTreeNode connectionsNode;
    private DefaultMutableTreeNode propertiesNode;
    
    private boolean saved;
    
    public ComponentJDialog( java.awt.Window parent )
    {
        super( parent );
        
        initComponents();
        init();
        addListeners();
        
        setSize( 700 , 600 );
        setTitle( "Component Edit" );
        setModal( true );
        setLocationRelativeTo( parent );
    }
    
    private void init()
    {
        add( new JXHeader( 
            "Component Edit" , 
            "Please, you should edit this component." 
        ) , BorderLayout.NORTH );
        
        declarationJScrollPane.setViewportView(
            tree = new JXTree(
                rootNode = new DefaultMutableTreeNode( "Component" )
            )
        );
        
        treeModel = (DefaultTreeModel) tree.getModel();
        
        typeJComboBox.addItem( Component.TYPE_ABSTRACT );
        typeJComboBox.addItem( Component.TYPE_BUS );
        typeJComboBox.addItem( Component.TYPE_DATA );
        typeJComboBox.addItem( Component.TYPE_DEVICE );
        typeJComboBox.addItem( Component.TYPE_MEMORY );
        typeJComboBox.addItem( Component.TYPE_PROCESS );
        typeJComboBox.addItem( Component.TYPE_PROCESSOR );
        typeJComboBox.addItem( Component.TYPE_SYSTEM );
        typeJComboBox.addItem( Component.TYPE_THREAD );
        
        tree.setRootVisible( false );
        
        rootNode.add( featuresNode = new DefaultMutableTreeNode( "Features" ) );
        rootNode.add( subcomponentsNode = new DefaultMutableTreeNode( "Subcomponents" ) );
        rootNode.add( connectionsNode = new DefaultMutableTreeNode( "Connections" ) );
        rootNode.add( propertiesNode = new DefaultMutableTreeNode( "Properties" ) );
        treeModel.setRoot( rootNode );
    }
    
    private void addListeners()
    {
        typeJComboBox.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if( e.getStateChange() != ItemEvent.SELECTED ){
                    return ;
                }
                
                createComponentText();
            }
        });
        
        implementationJCheckBox.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if( e.getStateChange() != ItemEvent.SELECTED
                    && e.getStateChange() != ItemEvent.DESELECTED ){
                    return ;
                }
                
                createComponentText();
            }
        });
        
        nameJTextField.getDocument().addDocumentListener( new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                createComponentText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                createComponentText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                createComponentText();
            }
        });
        
        extendJTextField.getDocument().addDocumentListener( new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                createComponentText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                createComponentText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                createComponentText();
            }
        });
        
        tree.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if( tree.getSelectionPath() == null
                    || tree.getSelectionPath().getPathCount() < 2 )
                {
                    return ;
                }
                
                boolean isAdd = tree.getSelectionPath().getPathCount() == 2;
                
                if( isAdd )
                {
                    if( e != null && e.getClickCount() >= 3 )
                    {
                        addJButton.doClick();
                    }
                }
                else
                {
                    if( e != null && e.getClickCount() >= 2 )
                    {
                        editJButton.doClick();
                    }
                }
            }
        });
        
        tree.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch( e.getKeyCode() ){
                    case KeyEvent.VK_DELETE: 
                        deleteJButton.doClick(); 
                        break;
                    case KeyEvent.VK_F5: 
                        refreshJTree();
                        break;
                    case KeyEvent.VK_ENTER: 
                        tree.getMouseListeners()[0].mouseClicked( null );
                        break;
                }
            }
        } );
        
        addJButton .addActionListener( new DeclarationDialogActionListener( this , true  ) );
        editJButton.addActionListener( new DeclarationDialogActionListener( this , false ) );
    }

    public void setComponent( Component component )
    {
        this.component = component;
        
        // ---- //
        
        typeJComboBox.setSelectedItem( component.getType() );
        nameJTextField.setText( component.getName() );
        extendJTextField.setText( component.getExtend() );
        implementationJCheckBox.setSelected( component.isImplementation() );
        
        featuresNode.removeAllChildren();
        subcomponentsNode.removeAllChildren();
        connectionsNode.removeAllChildren();
        propertiesNode.removeAllChildren();
        
        for( Feature feature : component.getFeatures().values() )
        {
            featuresNode.add( new DefaultMutableTreeNode( feature ) );
        }
        
        for( Subcomponent sub : component.getSubcomponents().values() )
        {
            subcomponentsNode.add( new DefaultMutableTreeNode( sub ) );
        }
        
        for( Connection connection : component.getConnections().values() )
        {
            connectionsNode.add( new DefaultMutableTreeNode( connection ) );
        }
        
        for( Property property : component.getProperties() )
        {
            propertiesNode.add( new DefaultMutableTreeNode( property ) );
        }
        
        JTreeUtils.setTreeExpandedState( tree , true );
        
        createComponentText();
    }

    public boolean isSaved()
    {
        return saved;
    }
    
    public Component getComponent()
    {
        if( component == null )
        {
            component = new Component();
        }
        
        component.setType( typeJComboBox.getSelectedItem().toString() );
        component.setImplementation( implementationJCheckBox.isSelected() );
        component.setName( nameJTextField.getText().trim() );
        component.setExtend( extendJTextField.getText() );
        
        component.getFeatures().clear();
        component.getSubcomponents().clear();
        component.getConnections().clear();
        component.getProperties().clear();
        
        for( int i = 0 ; i < featuresNode.getChildCount() ; i++ )
        {
            Feature feature = (Feature) 
                ((DefaultMutableTreeNode) featuresNode.getChildAt( i ))
                .getUserObject();
            
            component.getFeatures().put( feature.getName() , feature );
        }
        
        for( int i = 0 ; i < subcomponentsNode.getChildCount() ; i++ )
        {
            Subcomponent subcomponent = (Subcomponent) 
                ((DefaultMutableTreeNode) subcomponentsNode.getChildAt( i ))
                .getUserObject();
            
            component.getSubcomponents().put( subcomponent.getName() , subcomponent );
        }
        
        for( int i = 0 ; i < connectionsNode.getChildCount() ; i++ )
        {
            Connection connection = (Connection) 
                ((DefaultMutableTreeNode) connectionsNode.getChildAt( i ))
                .getUserObject();
            
            component.getConnections().put( connection.getName() , connection );
        }
        
        for( int i = 0 ; i < propertiesNode.getChildCount() ; i++ )
        {
            Property property = (Property) 
                ((DefaultMutableTreeNode) propertiesNode.getChildAt( i ))
                .getUserObject();
            
            component.getProperties().add( property );
        }
        
        return component;
    }
    
    private void createComponentText()
    {
        StringBuilder builder = new StringBuilder();
        
        // first line
        builder.append( typeJComboBox.getSelectedItem() );
        builder.append( implementationJCheckBox.isSelected() ? " implementation " : " " );
        builder.append( nameJTextField.getText().trim() );
        builder.append( extendJTextField.getText().isEmpty() 
            ? "\n" 
            : " extend " + extendJTextField.getText() + "\n" 
        );
        
        createDeclarationText( builder , "features"      , featuresNode      , ":" );
        createDeclarationText( builder , "subcomponents" , subcomponentsNode , ":" );
        createDeclarationText( builder , "connections"   , connectionsNode   , ":" );
        createDeclarationText( builder , "properties"    , propertiesNode    , "=>" );
        
        // last line
        builder.append( "end " );
        builder.append( nameJTextField.getText().trim() );
        builder.append( ";" );
        
        componentJTextArea.setText( builder.toString() );
    }
    
    private void createDeclarationText( StringBuilder builder , String name , DefaultMutableTreeNode node , String connector )
    {
        if( node.getChildCount() == 0 )
        {
            return ;
        }
        
        builder.append( "\n  " );
        builder.append( name );
        builder.append( "\n" );
                
        for( int i = 0 ; i < node.getChildCount() ; i++ )
        {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) node.getChildAt( i );
            Declaration d = (Declaration) n.getUserObject();
            
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
    
    public Declaration getDeclarationNew()
    {
        if( tree.getSelectionPath() == null
            || tree.getSelectionPath().getPathCount() < 2 )
        {
            return null;
        }
        
        DefaultMutableTreeNode obj = (DefaultMutableTreeNode) tree.getSelectionPath().getPathComponent( 1 );
        
        if( "features".equalsIgnoreCase( obj.getUserObject().toString() ) )
        {
            return new Feature();
        }
        else if( "subcomponents".equalsIgnoreCase( obj.getUserObject().toString() ) )
        {
            return new Subcomponent();
        }
        else if( "connections".equalsIgnoreCase( obj.getUserObject().toString() ) )
        {
            return new Connection();
        }
        else
        {
            return new Property();
        }
    }
    
    public Declaration getDeclaration()
    {
        if( tree.getSelectionPath() == null
            || tree.getSelectionPath().getPathCount() < 3 )
        {
            return null;
        }
        
        DefaultMutableTreeNode obj = (DefaultMutableTreeNode) tree.getSelectionPath().getPathComponent( 2 );
        return (Declaration) obj.getUserObject();
    }
    
    public void addDeclaration( Declaration declaration )
    {
        if( declaration instanceof Feature )
        {
            featuresNode.add( new DefaultMutableTreeNode( declaration ) );
            treeModel.reload( featuresNode );
        }
        else if( declaration instanceof Subcomponent )
        {
            subcomponentsNode.add( new DefaultMutableTreeNode( declaration ) );
            treeModel.reload( subcomponentsNode );
        }
        else if( declaration instanceof Connection )
        {
            connectionsNode.add( new DefaultMutableTreeNode( declaration ) );
            treeModel.reload( connectionsNode );
        }
        else
        {
            propertiesNode.add( new DefaultMutableTreeNode( declaration ) );
            treeModel.reload( propertiesNode );
        }
        
        createComponentText();
    }
    
    public void setDeclaration( Declaration declaration )
    {
        DefaultMutableTreeNode obj = (DefaultMutableTreeNode) tree.getSelectionPath().getPathComponent( 2 );
        obj.setUserObject( declaration );
        refreshJTree();
        
        createComponentText();
    }
    
    private void refreshJTree()
    {
        treeModel.reload( rootNode );
        JTreeUtils.setTreeExpandedState( tree , true );
        
        createComponentText();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        saveJButton = new javax.swing.JButton();
        cancelJButton = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        typeJComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        nameJTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        extendJButton = new javax.swing.JButton();
        extendJTextField = new javax.swing.JTextField();
        declarationJPanel = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel4 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        addJButton = new javax.swing.JButton();
        openJButton = new javax.swing.JButton();
        editJButton = new javax.swing.JButton();
        deleteJButton = new javax.swing.JButton();
        declarationJScrollPane = new javax.swing.JScrollPane();
        implementationJCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        componentJTextArea = new javax.swing.JTextArea();

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

        jSplitPane1.setDividerLocation(300);

        jLabel1.setText("Type:");

        jLabel3.setText("Name:");

        jLabel2.setText("Extend:");

        jPanel2.setLayout(new java.awt.BorderLayout());

        extendJButton.setText("Select");
        extendJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        extendJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extendJButtonActionPerformed(evt);
            }
        });
        jPanel2.add(extendJButton, java.awt.BorderLayout.LINE_END);
        jPanel2.add(extendJTextField, java.awt.BorderLayout.CENTER);

        declarationJPanel.setLayout(new java.awt.BorderLayout());

        jToolBar2.setFloatable(false);

        jLabel4.setText("Declarations");
        jToolBar2.add(jLabel4);
        jToolBar2.add(filler2);

        addJButton.setText("+");
        addJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addJButton.setFocusable(false);
        addJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(addJButton);

        openJButton.setText("O");
        openJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        openJButton.setFocusable(false);
        openJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openJButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(openJButton);

        editJButton.setText("E");
        editJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editJButton.setFocusable(false);
        editJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(editJButton);

        deleteJButton.setText("D");
        deleteJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteJButton.setFocusable(false);
        deleteJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(deleteJButton);

        declarationJPanel.add(jToolBar2, java.awt.BorderLayout.PAGE_START);
        declarationJPanel.add(declarationJScrollPane, java.awt.BorderLayout.CENTER);

        implementationJCheckBox.setText("implementation");
        implementationJCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typeJComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameJTextField)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(declarationJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(implementationJCheckBox)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(implementationJCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(declarationJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        componentJTextArea.setEditable(false);
        componentJTextArea.setColumns(20);
        componentJTextArea.setRows(5);
        jScrollPane1.setViewportView(componentJTextArea);

        jSplitPane1.setRightComponent(jScrollPane1);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void extendJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extendJButtonActionPerformed
        final ExtendSelectJDialog dialog = new ExtendSelectJDialog( this );
        
        dialog.setProjectAndType( 
            component.getParent().getParent() , 
            typeJComboBox.getSelectedItem().toString() 
        );
        
        dialog.setVisible( true );
        dialog.dispose();
        
        if( dialog.isSaved() )
        {
            Component extend = dialog.getSelected();
            
            if( extend == null )
            {
                extendJTextField.setText( "" );
            }
            else if( extend.getParent() == component.getParent() )
            {
                extendJTextField.setText( 
                    extend.getName() 
                );
            }
            else
            {
                extendJTextField.setText( 
                    extend.getParent().getName() +
                    ComponentPackage.PACKAGE_SEPARATOR +
                    extend.getName() 
                );
            }
        }
    }//GEN-LAST:event_extendJButtonActionPerformed

    private void saveJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJButtonActionPerformed
        saved = true;
        setVisible( false );
    }//GEN-LAST:event_saveJButtonActionPerformed

    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
        saved = false;
        setVisible( false );
    }//GEN-LAST:event_cancelJButtonActionPerformed

    private void deleteJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJButtonActionPerformed
        if( tree.getSelectionPath() == null
            || tree.getSelectionPath().getPathCount() < 3 )
        {
            return ;
        }
        
        int r = JOptionPane.showConfirmDialog( this , "Do you want delete this node?" );
        if( r == JOptionPane.YES_OPTION )
        {
            DefaultMutableTreeNode obj = (DefaultMutableTreeNode) tree.getSelectionPath().getPathComponent( 2 );
            treeModel.removeNodeFromParent( obj );
            
            createComponentText();
            
            obj = null;
        }
    }//GEN-LAST:event_deleteJButtonActionPerformed

    private void openJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openJButtonActionPerformed
        try
        {
            Declaration declaration = getDeclaration();
            if( declaration == null )
            {
                throw new Exception( "Please, select a declaration." );
            }
            else if( declaration.getComponent() == null )
            {
                throw new Exception( "The component "+ declaration.getComponentReferenceName() +" was not found." );
            }
            
            final ComponentJDialog dialog = new ComponentJDialog( this );
            dialog.setComponent( declaration.getComponent() );
            dialog.setVisible( true );
            dialog.dispose();
        }
        catch( Exception err )
        {
            JOptionPane.showMessageDialog( 
                this , 
                err.getMessage() ,
                "Error" ,
                JOptionPane.ERROR_MESSAGE 
            );
        }
    }//GEN-LAST:event_openJButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addJButton;
    private javax.swing.JButton cancelJButton;
    private javax.swing.JTextArea componentJTextArea;
    private javax.swing.JPanel declarationJPanel;
    private javax.swing.JScrollPane declarationJScrollPane;
    private javax.swing.JButton deleteJButton;
    private javax.swing.JButton editJButton;
    private javax.swing.JButton extendJButton;
    private javax.swing.JTextField extendJTextField;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JCheckBox implementationJCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTextField nameJTextField;
    private javax.swing.JButton openJButton;
    private javax.swing.JButton saveJButton;
    private javax.swing.JComboBox<String> typeJComboBox;
    // End of variables declaration//GEN-END:variables
}
