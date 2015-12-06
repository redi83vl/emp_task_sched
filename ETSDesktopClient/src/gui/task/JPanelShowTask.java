/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.task;

import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author user
 */
public class JPanelShowTask extends javax.swing.JPanel
{
	public JPanelShowTask()
	{
		initComponents();
	}

	private dc.Task task = null;
	public void setTask(dc.Task task)
	{
		this.task = task;
		if(this.task == null)
			return;
		this.jTextFieldIdentifier.setText(this.task.getIdentifier());
		this.jTextFieldTitle.setText(this.task.getTitle());
		this.jTextAreaDescription.setText(this.task.getDescription());
		this.jTextFieldCreator.setText(this.task.getCreator().toString());
		this.jTextFieldExecutor.setText(this.task.getExecutor().toString());
		this.jTextFieldExipration.setText(this.task.getExpirationTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		
		if(this.task instanceof dc.CompletedTask)
		{
			this.jTextFieldConlusion.setText(((dc.CompletedTask)this.task).getCompletitionTime().format(DateTimeFormatter.ofPattern("hh:mm dd.MM.yyyy")));
			this.jButton1.setEnabled(true);
		}
		else if(task instanceof dc.RejectedTask)
		{
			this.jTextFieldConlusion.setText(((dc.RejectedTask)this.task).getRejectionTime().format(DateTimeFormatter.ofPattern("hh:mm dd.MM.yyyy")));
			this.jButton1.setEnabled(true);
		}
		else
			this.jButton1.setEnabled(false);
		
		this.jTextAreaDescription.setCaretPosition(0);
	}
	@SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
     private void initComponents()
     {
          java.awt.GridBagConstraints gridBagConstraints;

          jScrollPane1 = new javax.swing.JScrollPane();
          jTextAreaDescription = new javax.swing.JTextArea();
          jSeparator1 = new javax.swing.JSeparator();
          jTextFieldTitle = new javax.swing.JTextField();
          jTextFieldIdentifier = new javax.swing.JTextField();
          jTextFieldCreator = new javax.swing.JTextField();
          jTextFieldExecutor = new javax.swing.JTextField();
          jTextFieldConlusion = new javax.swing.JTextField();
          jTextFieldExipration = new javax.swing.JTextField();
          jButton1 = new javax.swing.JButton();

          setLayout(new java.awt.GridBagLayout());

          jTextAreaDescription.setEditable(false);
          jTextAreaDescription.setColumns(50);
          jTextAreaDescription.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jTextAreaDescription.setLineWrap(true);
          jTextAreaDescription.setRows(25);
          jTextAreaDescription.setTabSize(5);
          jTextAreaDescription.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pershkrimi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
          jScrollPane1.setViewportView(jTextAreaDescription);

          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 3;
          gridBagConstraints.gridwidth = 3;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
          add(jScrollPane1, gridBagConstraints);
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 6;
          gridBagConstraints.gridwidth = 5;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          add(jSeparator1, gridBagConstraints);

          jTextFieldTitle.setEditable(false);
          jTextFieldTitle.setColumns(35);
          jTextFieldTitle.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jTextFieldTitle.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Titulli", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 2;
          gridBagConstraints.gridwidth = 3;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
          add(jTextFieldTitle, gridBagConstraints);

          jTextFieldIdentifier.setEditable(false);
          jTextFieldIdentifier.setColumns(15);
          jTextFieldIdentifier.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jTextFieldIdentifier.setForeground(java.awt.Color.red);
          jTextFieldIdentifier.setHorizontalAlignment(javax.swing.JTextField.CENTER);
          jTextFieldIdentifier.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Id", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 0;
          gridBagConstraints.gridwidth = 3;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
          add(jTextFieldIdentifier, gridBagConstraints);

          jTextFieldCreator.setEditable(false);
          jTextFieldCreator.setColumns(15);
          jTextFieldCreator.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jTextFieldCreator.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Urdheruesi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.gridwidth = 2;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
          gridBagConstraints.weightx = 1.0;
          gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
          add(jTextFieldCreator, gridBagConstraints);

          jTextFieldExecutor.setEditable(false);
          jTextFieldExecutor.setColumns(15);
          jTextFieldExecutor.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jTextFieldExecutor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Zbatuesi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.weightx = 1.0;
          gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
          add(jTextFieldExecutor, gridBagConstraints);

          jTextFieldConlusion.setEditable(false);
          jTextFieldConlusion.setColumns(15);
          jTextFieldConlusion.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jTextFieldConlusion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Perfundimi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 5;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
          add(jTextFieldConlusion, gridBagConstraints);

          jTextFieldExipration.setEditable(false);
          jTextFieldExipration.setColumns(15);
          jTextFieldExipration.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jTextFieldExipration.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Skadenca", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 4;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
          add(jTextFieldExipration, gridBagConstraints);

          jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/task/1439694602_eye.png"))); // NOI18N
          jButton1.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jButton1ActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 5;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(5, 0, 7, 5);
          add(jButton1, gridBagConstraints);
     }// </editor-fold>//GEN-END:initComponents

     private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
     {//GEN-HEADEREND:event_jButton1ActionPerformed
          String str = "";
		if(this.task instanceof dc.CompletedTask)
			str = ((dc.CompletedTask)this.task).getAnnotations();
		else if(task instanceof dc.RejectedTask)
			str = ((dc.RejectedTask)this.task).getAnnotations();
		
		JTextArea jta = new JTextArea();
		jta.setColumns(50);
          jta.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
          jta.setLineWrap(true);
          jta.setRows(25);
          jta.setTabSize(5);
		jta.setText(str);
		JScrollPane jsp = new JScrollPane(jta);
		jta.setCaretPosition(0);
		JOptionPane.showMessageDialog(this,new JScrollPane(jta), "Shenime!", JOptionPane.PLAIN_MESSAGE);
     }//GEN-LAST:event_jButton1ActionPerformed


     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.JButton jButton1;
     private javax.swing.JScrollPane jScrollPane1;
     private javax.swing.JSeparator jSeparator1;
     private javax.swing.JTextArea jTextAreaDescription;
     private javax.swing.JTextField jTextFieldConlusion;
     private javax.swing.JTextField jTextFieldCreator;
     private javax.swing.JTextField jTextFieldExecutor;
     private javax.swing.JTextField jTextFieldExipration;
     private javax.swing.JTextField jTextFieldIdentifier;
     private javax.swing.JTextField jTextFieldTitle;
     // End of variables declaration//GEN-END:variables
}
