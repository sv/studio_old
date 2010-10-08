/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConfigDialog.java
 *
 * Created on Jan 22, 2010, 12:22:15 PM
 */

package studio.ui;

import java.awt.Font;
import java.text.DecimalFormat;
import org.jfree.ui.FontChooserDialog;
import studio.kdb.Config;

/**
 *
 * @author vidyser
 */
public class ConfigDialog extends javax.swing.JDialog {
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

    /** Creates new form ConfigDialog */
    public ConfigDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lfLabel = new javax.swing.JLabel();
        lookAndFeel = new javax.swing.JTextField();
        nfLabel = new javax.swing.JLabel();
        numberFormat = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        fontChooserButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        subsciption = new javax.swing.JCheckBox();
        dictAsTable = new javax.swing.JCheckBox();

        setTitle("Studio for kdb+ settings");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("Save");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        titleLabel.setText("Studio for kdb+ settings");

        lfLabel.setText("Look and feel:");

        lookAndFeel.setText(Config.getInstance().getLookAndFeel());

        nfLabel.setText("Number format:");

        numberFormat.setText(((DecimalFormat)Config.getInstance().getNumberFormat()).toPattern());

        jLabel1.setText("Font:");

        fontChooserButton.setText(Config.getInstance().getFont().getName()+","+Config.getInstance().getFont().getSize());
        fontChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontChooserButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("* Restart app to changes get effect");

        subsciption.setText("Subscription support");
        subsciption.setSelected(Config.getInstance().isSubsciptionEnabled());

        dictAsTable.setText("Dict as table");
        dictAsTable.setSelected(Config.getInstance().isDictAsTable());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addComponent(titleLabel)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lfLabel)
                        .addGap(18, 18, 18)
                        .addComponent(lookAndFeel, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nfLabel)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fontChooserButton)
                            .addComponent(numberFormat, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(subsciption)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dictAsTable)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lfLabel)
                    .addComponent(lookAndFeel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nfLabel)
                    .addComponent(numberFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fontChooserButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subsciption)
                    .addComponent(dictAsTable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (newFont != null) {
            Config.getInstance().setFont(newFont);
        }
        if (!lookAndFeel.getText().equals(Config.getInstance().getLookAndFeel())) {
            Config.getInstance().setLookAndFeel(lookAndFeel.getText());
        }
        if (numberFormat.getText().equals(((DecimalFormat) Config.getInstance().getNumberFormat()).toPattern())) {
            Config.getInstance().setNumberFormat(numberFormat.getText());
        }
        if(dictAsTable.isSelected() != Config.getInstance().isDictAsTable())
            Config.getInstance().setDictAsTable(dictAsTable.isSelected());

        if(subsciption.isSelected() != Config.getInstance().isSubsciptionEnabled())
            Config.getInstance().setSubscriptionEnabled(subsciption.isSelected());
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private Font newFont=null;

    private void fontChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontChooserButtonActionPerformed
        FontChooserDialog fd = new FontChooserDialog(this, "Choose font", true, Config.getInstance().getFont());
        fd.pack();
        fd.setVisible(true);
        if (!fd.isCancelled()) {
            newFont = fd.getSelectedFont();
            fontChooserButton.setText(newFont.getName()+","+newFont.getSize());
        }
    }//GEN-LAST:event_fontChooserButtonActionPerformed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfigDialog dialog = new ConfigDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox dictAsTable;
    private javax.swing.JButton fontChooserButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lfLabel;
    private javax.swing.JTextField lookAndFeel;
    private javax.swing.JLabel nfLabel;
    private javax.swing.JTextField numberFormat;
    private javax.swing.JButton okButton;
    private javax.swing.JCheckBox subsciption;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}
