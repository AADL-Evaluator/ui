package org.osate.aadl.evaluator.ui.edit;

import java.awt.BorderLayout;
import org.jdesktop.swingx.JXHeader;
import org.osate.aadl.evaluator.project.Declaration;

public class DeclarationJDialog extends javax.swing.JDialog 
{
    private boolean saved;
    private JXHeader header;
    private Declaration declaration;
    
    public DeclarationJDialog( java.awt.Window parent )
    {
        super( parent );
        
        initComponents();
        init();
        
        setSize( 500 , 300 );
        setTitle( "Declaration" );
        setModal( true );
        setLocationRelativeTo( parent );
    }

    private void init()
    {
        add( header = new JXHeader( 
            "Declaration" , 
            "Please, set a name and value." 
        ) , BorderLayout.NORTH );
    }

    @Override
    public void setTitle( String title )
    {
        super.setTitle( title );
        
        if( header != null )
        {
            header.setTitle( title );
        }
    }
    
    public void setDeclaration( Declaration declaration )
    {
        this.declaration = declaration;
        
        nameJTextField.setText( declaration.getName()  );
        valueJTextArea.setText( declaration.getValue() );
    }
    
    public Declaration getDeclaration()
    {
        declaration.setName( nameJTextField.getText().trim() );
        declaration.setValue( valueJTextArea.getText().trim() );
        
        return declaration;
    }

    public boolean isSaved()
    {
        return saved;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        saveJButton = new javax.swing.JButton();
        cancelJButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameJTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        valueJTextArea = new javax.swing.JTextArea();

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

        jLabel1.setText("Name:");

        jLabel2.setText("Value:");

        valueJTextArea.setColumns(20);
        valueJTextArea.setLineWrap(true);
        valueJTextArea.setRows(5);
        valueJTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(valueJTextArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameJTextField)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJButtonActionPerformed
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField nameJTextField;
    private javax.swing.JButton saveJButton;
    private javax.swing.JTextArea valueJTextArea;
    // End of variables declaration//GEN-END:variables
}
