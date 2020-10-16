package org.osate.aadl.evaluator.ui.p3;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import org.jdesktop.swingx.JXHeader;
import org.osate.aadl.evaluator.ui.mainWizard.AadlUIAction;
import org.osate.aadl.evaluator.ui.mainWizard.AadlUIManager;

public class EvolutionChooseJDialog extends javax.swing.JDialog 
{
    public static final int TYPE_MANUAL = 0;
    public static final int TYPE_MANUAL_GUIDE = 1;
    public static final int TYPE_AUTOMATIC = 2;
    
    private boolean saved;
    
    public EvolutionChooseJDialog( Window parent )
    {
        super( parent );
        
        initComponents();
        init();
        
        setTitle( "Choose" );
        setSize( 400 , 350 );
        setModal( true );
        setLocationRelativeTo( parent );
    }
    
    private void init()
    {
        add( 
            new JXHeader( 
                "Choose" , 
                "what type of evolution would you like to do? There are two "
                + "option: 1º) modifing as you want the system (e.g., add"
                + ", change or delete one or more components); or 2º) changing"
                + "one component to other equivalent (e.g., change a component "
                + "A to component B)."
            )
        , BorderLayout.NORTH );
        
        // ---- //
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        for( AadlUIAction action : AadlUIManager.getInstance().getOptions() )
        {
            listModel.addElement( action.getDescription() );
        }
        
        /*
        listModel.addElement( "Modify completely the system"  );
        listModel.addElement( "Changing a component to other" );
        listModel.addElement( "Automatic changes creator" );
        */
        
        evolutionJList.setModel( listModel );
        evolutionJList.setSelectedIndex( 0 );
        evolutionJList.requestFocus();
        
        evolutionJList.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                    continueJButton.doClick();
                }
            }
        });
        
        evolutionJList.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if( e.getClickCount() >= 2 ){
                    continueJButton.doClick();
                }
            }
        });
    }
    
    public AadlUIAction getEvolutionType()
    {
        int row = evolutionJList.getSelectedIndex();
        
        return row == -1
            ? null
            : AadlUIManager.getInstance().get( row );
    }

    public boolean isSaved()
    {
        return saved;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        continueJButton = new javax.swing.JButton();
        cancelJButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        evolutionJList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jToolBar1.setFloatable(false);
        jToolBar1.add(filler1);

        continueJButton.setText("Continue");
        continueJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        continueJButton.setFocusable(false);
        continueJButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        continueJButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        continueJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continueJButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(continueJButton);

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

        jLabel1.setText("What type of evolution?");

        evolutionJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(evolutionJList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void continueJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continueJButtonActionPerformed
        saved = true;
        setVisible( false );
    }//GEN-LAST:event_continueJButtonActionPerformed

    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
        saved = false;
        setVisible( false );
    }//GEN-LAST:event_cancelJButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelJButton;
    private javax.swing.JButton continueJButton;
    private javax.swing.JList<String> evolutionJList;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
