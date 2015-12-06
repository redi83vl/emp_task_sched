/*
 * Copyright (C) 2015 Redjan Shabani
 * Contact: +355 69 84 93 238, redjan.shabani@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gui.task;

import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Redjan Shabani
 */
public class JPanelAddTask extends javax.swing.JPanel
{
	public JPanelAddTask()
	{
		initComponents();
		
	}
	
	private Connection dbconn = null;
	private dc.Person user = null;
	public void setConnParam(Connection dbconn, dc.Person user)
	{
		this.dbconn = dbconn;
		
		if(this.dbconn == null)
			return;
		
		this.setPersonList();
		
		this.user = user;
		
		this.jComboBoxAuthor.setSelectedItem(this.user);
	}
	
	private List<dc.Person> personList;
	private void setPersonList()
	{
		this.personList = dao.Person.read(dbconn);
		for(dc.Person person : this.personList)
		{
			this.jComboBoxAuthor.addItem(person);
			this.jComboBoxExecutor.addItem(person);
		}
		
	}
	
	public dc.Task getTask()
	{
		if(this.jComboBoxAuthor.getSelectedIndex() == 0 || this.jComboBoxExecutor.getSelectedIndex() == 0)
			return null;
		if(this.jTextFieldTitle.getText().isEmpty())
			return null;
		if(this.jXDatePickerExpiration.getDate()==null)
			return null;
		
		String identifier = this.jTextFieldIdentifier.getText();
		dc.Person author = (dc.Person) this.jComboBoxAuthor.getSelectedItem();
		dc.Person executor = (dc.Person) this.jComboBoxExecutor.getSelectedItem();
		String title = this.jTextFieldTitle.getText();
		String description = this.jTextAreaDescription.getText();
		LocalDateTime creationTime = LocalDateTime.now();
		LocalDateTime expirationTime = this.jXDatePickerExpiration.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(23).withMinute(59).withSecond(59);
		
		dc.Task task = new dc.Task(identifier, author, executor, title, description, creationTime, expirationTime);
		
		return task;
	}
	
	private void computeIdentifier()
	{
		if(this.jComboBoxAuthor.getSelectedIndex() == 0 || this.jComboBoxExecutor.getSelectedIndex()==0)
		{
			this.jTextFieldIdentifier.setText("");
			return;
		}
		int identifier = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hhmmssddMM")));
		identifier += Arrays.hashCode(this.jComboBoxAuthor.getSelectedObjects());
		identifier += Arrays.hashCode(this.jComboBoxExecutor.getSelectedObjects());
		this.jTextFieldIdentifier.setText("" + Math.abs(identifier));
	}
	
	@SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
     private void initComponents()
     {
          java.awt.GridBagConstraints gridBagConstraints;

          jLabel2 = new javax.swing.JLabel();
          jComboBoxAuthor = new javax.swing.JComboBox();
          jLabel3 = new javax.swing.JLabel();
          jComboBoxExecutor = new javax.swing.JComboBox();
          jLabel4 = new javax.swing.JLabel();
          jTextFieldTitle = new javax.swing.JTextField();
          jLabel5 = new javax.swing.JLabel();
          jScrollPane1 = new javax.swing.JScrollPane();
          jTextAreaDescription = new javax.swing.JTextArea();
          jLabel1 = new javax.swing.JLabel();
          jXDatePickerExpiration = new org.jdesktop.swingx.JXDatePicker();
          jTextFieldIdentifier = new javax.swing.JTextField();
          jSeparator1 = new javax.swing.JSeparator();
          jSeparator2 = new javax.swing.JSeparator();
          jSeparator3 = new javax.swing.JSeparator();

          setLayout(new java.awt.GridBagLayout());

          jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jLabel2.setText("Urdheruesi");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 2;
          gridBagConstraints.weightx = 1.0;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 7);
          add(jLabel2, gridBagConstraints);

          jComboBoxAuthor.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jComboBoxAuthor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Zgjidh Individ --" }));
          jComboBoxAuthor.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jComboBoxAuthorActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 3;
          gridBagConstraints.weightx = 1.0;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
          add(jComboBoxAuthor, gridBagConstraints);

          jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jLabel3.setText("Zbatuesi");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 2;
          gridBagConstraints.weightx = 1.0;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 2);
          add(jLabel3, gridBagConstraints);

          jComboBoxExecutor.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jComboBoxExecutor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Zgjidh Individ --" }));
          jComboBoxExecutor.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jComboBoxExecutorActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 3;
          gridBagConstraints.weightx = 1.0;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
          add(jComboBoxExecutor, gridBagConstraints);

          jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jLabel4.setText("Titulli");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 5;
          gridBagConstraints.gridwidth = 4;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
          add(jLabel4, gridBagConstraints);

          jTextFieldTitle.setColumns(35);
          jTextFieldTitle.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 6;
          gridBagConstraints.gridwidth = 4;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
          add(jTextFieldTitle, gridBagConstraints);

          jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jLabel5.setText("Pershkrimi");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 7;
          gridBagConstraints.gridwidth = 4;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
          add(jLabel5, gridBagConstraints);

          jTextAreaDescription.setColumns(50);
          jTextAreaDescription.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jTextAreaDescription.setLineWrap(true);
          jTextAreaDescription.setRows(25);
          jTextAreaDescription.setTabSize(5);
          jScrollPane1.setViewportView(jTextAreaDescription);

          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 8;
          gridBagConstraints.gridwidth = 4;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
          add(jScrollPane1, gridBagConstraints);

          jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jLabel1.setText("Skadenca");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 9;
          gridBagConstraints.gridwidth = 2;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
          add(jLabel1, gridBagConstraints);

          jXDatePickerExpiration.setDate(java.util.Date.from(Instant.now())
          );
          jXDatePickerExpiration.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
          jXDatePickerExpiration.setFormats("dd.MM.yyyy");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 9;
          gridBagConstraints.gridwidth = 2;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
          add(jXDatePickerExpiration, gridBagConstraints);

          jTextFieldIdentifier.setEditable(false);
          jTextFieldIdentifier.setColumns(15);
          jTextFieldIdentifier.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jTextFieldIdentifier.setHorizontalAlignment(javax.swing.JTextField.CENTER);
          jTextFieldIdentifier.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Id", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 0;
          gridBagConstraints.gridwidth = 4;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
          add(jTextFieldIdentifier, gridBagConstraints);
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 10;
          gridBagConstraints.gridwidth = 2;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
          add(jSeparator1, gridBagConstraints);
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 4;
          gridBagConstraints.gridwidth = 2;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
          add(jSeparator2, gridBagConstraints);
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.gridwidth = 2;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
          add(jSeparator3, gridBagConstraints);
     }// </editor-fold>//GEN-END:initComponents

     private void jComboBoxAuthorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBoxAuthorActionPerformed
     {//GEN-HEADEREND:event_jComboBoxAuthorActionPerformed
          this.computeIdentifier();
     }//GEN-LAST:event_jComboBoxAuthorActionPerformed

     private void jComboBoxExecutorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBoxExecutorActionPerformed
     {//GEN-HEADEREND:event_jComboBoxExecutorActionPerformed
          this.computeIdentifier();
     }//GEN-LAST:event_jComboBoxExecutorActionPerformed


     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.JComboBox jComboBoxAuthor;
     private javax.swing.JComboBox jComboBoxExecutor;
     private javax.swing.JLabel jLabel1;
     private javax.swing.JLabel jLabel2;
     private javax.swing.JLabel jLabel3;
     private javax.swing.JLabel jLabel4;
     private javax.swing.JLabel jLabel5;
     private javax.swing.JScrollPane jScrollPane1;
     private javax.swing.JSeparator jSeparator1;
     private javax.swing.JSeparator jSeparator2;
     private javax.swing.JSeparator jSeparator3;
     private javax.swing.JTextArea jTextAreaDescription;
     private javax.swing.JTextField jTextFieldIdentifier;
     private javax.swing.JTextField jTextFieldTitle;
     private org.jdesktop.swingx.JXDatePicker jXDatePickerExpiration;
     // End of variables declaration//GEN-END:variables
}
