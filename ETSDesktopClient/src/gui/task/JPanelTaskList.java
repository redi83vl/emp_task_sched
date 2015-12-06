/*
 * Copyright (C) 2015 Redjan Shabani
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

import dc.Task;
import java.awt.Color;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Redjan Shabani
 */
public class JPanelTaskList extends javax.swing.JPanel
{
	public JPanelTaskList()
	{
		initComponents();
		
		this.jTextField1.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent de){updateContent();}
			@Override
			public void removeUpdate(DocumentEvent de){updateContent();}
			@Override
			public void changedUpdate(DocumentEvent de){updateContent();}
		});
	}
	
	private Connection dbconn;
	private dc.Person user;
	public void setConnParam(Connection dbconn, dc.Person user)
	{
		this.dbconn = dbconn;
		this.user = user;
	}
	
	private List<dc.Task> taskList = new ArrayList();
	public void setTaskList(List<dc.Task> taskList)
	{
		this.taskList = taskList;
		this.jTextField1.setText("");
		this.jXDatePickerMinDate.setDate(null);
		this.jXDatePickerMaxDate.setDate(null);
		this.updateContent();
	}
	
	List<dc.Task> taskListF = new ArrayList();
	private void updateContent()
	{
		this.taskListF = new ArrayList();
		
		//Adding filtered Tasks
		for (Task task : this.taskList)
		{
			if(this.jRadioButtonOpenedTask.isSelected() && (task instanceof dc.CompletedTask || task instanceof dc.RejectedTask))
				continue;
			if(this.jRadioButtonCompletedTask.isSelected() && !(task instanceof dc.CompletedTask))
				continue;
			if(this.jRadioButtonRejectedTask.isSelected() && !(task instanceof dc.RejectedTask))
				continue;
			
			String str = this.jTextField1.getText().toLowerCase();
			if(!task.getCreator().toString().concat(task.getExecutor().toString()).toLowerCase().concat(task.getTitle().toLowerCase()).contains(str))
				continue;
			
			LocalDateTime minDateTime = this.jXDatePickerMinDate.getDate() == null ? LocalDateTime.MIN : LocalDateTime.ofInstant(this.jXDatePickerMinDate.getDate().toInstant(), ZoneId.systemDefault()).withHour(0).withMinute(0).withSecond(0).withNano(0);
			LocalDateTime maxDateTime = this.jXDatePickerMaxDate.getDate() == null ? LocalDateTime.MAX : LocalDateTime.ofInstant(this.jXDatePickerMaxDate.getDate().toInstant(), ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
			
			if(task.getCreationTime().isBefore(minDateTime) || task.getCreationTime().isAfter(maxDateTime))
				continue;
			
			this.taskListF.add(task);
		}
		

		//sorting tasks according to creation time descending
		this.taskListF.sort(new Comparator(){

			@Override
			public int compare(Object o1, Object o2)
			{
				dc.Task t1 = (dc.Task) o1, t2 = (dc.Task) o2;
				return -t1.getCreationTime().compareTo(t2.getCreationTime());
			}
		});
		
		
		
		String[] header = {"ID", "Krijuesi", "Zbatuesi", "Titulli", "Krijuar", "Skadenca"};
		Object[][] data = new Object[taskListF.size()][header.length];
		for(int r = 0; r < this.taskListF.size(); r++)
		{
			String htmlRed = "<html><p color=\'red\'>" ;
			String htmlGreen = "<html><p color=\'green\'>" ;
			String htmlBlue = "<html><p color=\'blue\'>" ;
			String html;
			if(this.taskListF.get(r) instanceof dc.CompletedTask)
				html = htmlGreen;
			else if(this.taskListF.get(r) instanceof dc.RejectedTask)
				html = htmlRed;
			else
				html = htmlBlue;
			
			data[r][0] = html + this.taskListF.get(r).getIdentifier();
			data[r][1] = html + this.taskListF.get(r).getCreator();
			data[r][2] = html + this.taskListF.get(r).getExecutor();
			data[r][3] = html + "<b>" +this.taskListF.get(r).getTitle();
			data[r][4] = html + this.taskListF.get(r).getCreationTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			data[r][5] = html + this.taskListF.get(r).getExpirationTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		}
		
		DefaultTableModel dtm = new DefaultTableModel(data, header)
		{
			@Override
			public boolean isCellEditable(int r, int c)
			{
				return false;
			}
		};
		
		this.jTable.setModel(dtm);
		this.jTableSetFormatting();
	}
	
	private void jTableSetFormatting()
	{
		this.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		this.jTable.getColumn("ID").setPreferredWidth(100);
		this.jTable.getColumn("Krijuesi").setPreferredWidth(200);
		this.jTable.getColumn("Zbatuesi").setPreferredWidth(200);
		this.jTable.getColumn("Titulli").setPreferredWidth(700);
		this.jTable.getColumn("Krijuar").setPreferredWidth(100);
		this.jTable.getColumn("Skadenca").setPreferredWidth(100);
		
		
		DefaultTableCellRenderer rend = new DefaultTableCellRenderer();
		rend.setHorizontalAlignment(SwingConstants.CENTER);
		this.jTable.getColumn("ID").setCellRenderer(rend);
		this.jTable.getColumn("Krijuar").setCellRenderer(rend);
		this.jTable.getColumn("Skadenca").setCellRenderer(rend);
		
		this.jTable.setGridColor(Color.MAGENTA);
	}
	
	public dc.Task getSelectedTask()
	{
		int row = this.jTable.getSelectedRow();
		if(row<0)
			return null;
		
		return this.taskListF.get(row);
	}
	
	@SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
     private void initComponents()
     {
          java.awt.GridBagConstraints gridBagConstraints;

          buttonGroup1 = new javax.swing.ButtonGroup();
          jPanel1 = new javax.swing.JPanel();
          jRadioButtonOpenedTask = new javax.swing.JRadioButton();
          jRadioButtonCompletedTask = new javax.swing.JRadioButton();
          jRadioButtonRejectedTask = new javax.swing.JRadioButton();
          jRadioButtonAllTask = new javax.swing.JRadioButton();
          jXDatePickerMinDate = new org.jdesktop.swingx.JXDatePicker();
          jXDatePickerMaxDate = new org.jdesktop.swingx.JXDatePicker();
          jLabel1 = new javax.swing.JLabel();
          jLabel2 = new javax.swing.JLabel();
          jLabel3 = new javax.swing.JLabel();
          jSeparator1 = new javax.swing.JSeparator();
          jLabel4 = new javax.swing.JLabel();
          jSeparator4 = new javax.swing.JSeparator();
          jLabel5 = new javax.swing.JLabel();
          jTextField1 = new javax.swing.JTextField();
          jScrollPane1 = new javax.swing.JScrollPane();
          jTable = new javax.swing.JTable();
          jPanel2 = new javax.swing.JPanel();

          setLayout(new java.awt.BorderLayout());

          jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtro Listen", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
          jPanel1.setLayout(new java.awt.GridBagLayout());

          buttonGroup1.add(jRadioButtonOpenedTask);
          jRadioButtonOpenedTask.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jRadioButtonOpenedTask.setForeground(java.awt.Color.blue);
          jRadioButtonOpenedTask.setText("Ne Pritje");
          jRadioButtonOpenedTask.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jRadioButtonOpenedTaskActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
          jPanel1.add(jRadioButtonOpenedTask, gridBagConstraints);

          buttonGroup1.add(jRadioButtonCompletedTask);
          jRadioButtonCompletedTask.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jRadioButtonCompletedTask.setForeground(java.awt.Color.green);
          jRadioButtonCompletedTask.setText("Perfunduar");
          jRadioButtonCompletedTask.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jRadioButtonCompletedTaskActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 3;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
          jPanel1.add(jRadioButtonCompletedTask, gridBagConstraints);

          buttonGroup1.add(jRadioButtonRejectedTask);
          jRadioButtonRejectedTask.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jRadioButtonRejectedTask.setForeground(java.awt.Color.red);
          jRadioButtonRejectedTask.setText("Refuzuar");
          jRadioButtonRejectedTask.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jRadioButtonRejectedTaskActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 4;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
          jPanel1.add(jRadioButtonRejectedTask, gridBagConstraints);

          buttonGroup1.add(jRadioButtonAllTask);
          jRadioButtonAllTask.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jRadioButtonAllTask.setSelected(true);
          jRadioButtonAllTask.setText("Te Gjitha");
          jRadioButtonAllTask.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jRadioButtonAllTaskActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 5;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
          jPanel1.add(jRadioButtonAllTask, gridBagConstraints);

          jXDatePickerMinDate.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jXDatePickerMinDate.addPropertyChangeListener(new java.beans.PropertyChangeListener()
          {
               public void propertyChange(java.beans.PropertyChangeEvent evt)
               {
                    jXDatePickerMinDatePropertyChange(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 8;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
          jPanel1.add(jXDatePickerMinDate, gridBagConstraints);
          this.jXDatePickerMinDate.setFormats("dd.MM.yyyy");

          jXDatePickerMaxDate.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jXDatePickerMaxDate.addPropertyChangeListener(new java.beans.PropertyChangeListener()
          {
               public void propertyChange(java.beans.PropertyChangeEvent evt)
               {
                    jXDatePickerMaxDatePropertyChange(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 10;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
          jPanel1.add(jXDatePickerMaxDate, gridBagConstraints);
          this.jXDatePickerMaxDate.setFormats("dd.MM.yyyy");

          jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jLabel1.setText("Nga");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 7;
          gridBagConstraints.gridy = 1;
          jPanel1.add(jLabel1, gridBagConstraints);

          jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jLabel2.setText("Deri");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 9;
          gridBagConstraints.gridy = 1;
          jPanel1.add(jLabel2, gridBagConstraints);

          jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jLabel3.setText("Statusi");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 0;
          gridBagConstraints.gridwidth = 4;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
          jPanel1.add(jLabel3, gridBagConstraints);

          jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 0;
          gridBagConstraints.gridheight = 2;
          gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
          jPanel1.add(jSeparator1, gridBagConstraints);

          jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jLabel4.setText("Data e Krijimit");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 7;
          gridBagConstraints.gridy = 0;
          gridBagConstraints.gridwidth = 4;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
          jPanel1.add(jLabel4, gridBagConstraints);

          jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 6;
          gridBagConstraints.gridy = 0;
          gridBagConstraints.gridheight = 2;
          gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
          gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
          jPanel1.add(jSeparator4, gridBagConstraints);

          jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
          jLabel5.setText("Krijuesi/Zbatuesi");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 0;
          jPanel1.add(jLabel5, gridBagConstraints);

          jTextField1.setColumns(25);
          jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
          jPanel1.add(jTextField1, gridBagConstraints);

          add(jPanel1, java.awt.BorderLayout.NORTH);

          jTable.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
          jTable.setModel(new javax.swing.table.DefaultTableModel(
               new Object [][]
               {

               },
               new String []
               {
                    "ID", "Krijuesi", "Zbatuesi", "Titulli", "Dt. e Krijimit", "Dt. e Skadences"
               }
          )
          {
               Class[] types = new Class []
               {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
               };
               boolean[] canEdit = new boolean []
               {
                    false, false, false, false, false, false
               };

               public Class getColumnClass(int columnIndex)
               {
                    return types [columnIndex];
               }

               public boolean isCellEditable(int rowIndex, int columnIndex)
               {
                    return canEdit [columnIndex];
               }
          });
          jTable.setRowHeight(30);
          jTable.addMouseListener(new java.awt.event.MouseAdapter()
          {
               public void mouseClicked(java.awt.event.MouseEvent evt)
               {
                    jTableMouseClicked(evt);
               }
          });
          jScrollPane1.setViewportView(jTable);

          add(jScrollPane1, java.awt.BorderLayout.CENTER);

          jPanel2.setLayout(new java.awt.GridBagLayout());
          add(jPanel2, java.awt.BorderLayout.EAST);
     }// </editor-fold>//GEN-END:initComponents

     private void jRadioButtonOpenedTaskActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonOpenedTaskActionPerformed
     {//GEN-HEADEREND:event_jRadioButtonOpenedTaskActionPerformed
          this.updateContent();
     }//GEN-LAST:event_jRadioButtonOpenedTaskActionPerformed

     private void jRadioButtonCompletedTaskActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonCompletedTaskActionPerformed
     {//GEN-HEADEREND:event_jRadioButtonCompletedTaskActionPerformed
          this.updateContent();
     }//GEN-LAST:event_jRadioButtonCompletedTaskActionPerformed

     private void jRadioButtonRejectedTaskActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonRejectedTaskActionPerformed
     {//GEN-HEADEREND:event_jRadioButtonRejectedTaskActionPerformed
          this.updateContent();
     }//GEN-LAST:event_jRadioButtonRejectedTaskActionPerformed

     private void jRadioButtonAllTaskActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonAllTaskActionPerformed
     {//GEN-HEADEREND:event_jRadioButtonAllTaskActionPerformed
          this.updateContent();
     }//GEN-LAST:event_jRadioButtonAllTaskActionPerformed

     private void jXDatePickerMinDatePropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_jXDatePickerMinDatePropertyChange
     {//GEN-HEADEREND:event_jXDatePickerMinDatePropertyChange
		this.updateContent();
     }//GEN-LAST:event_jXDatePickerMinDatePropertyChange

     private void jXDatePickerMaxDatePropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_jXDatePickerMaxDatePropertyChange
     {//GEN-HEADEREND:event_jXDatePickerMaxDatePropertyChange
          this.updateContent();
     }//GEN-LAST:event_jXDatePickerMaxDatePropertyChange

     private void jTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTableMouseClicked
     {//GEN-HEADEREND:event_jTableMouseClicked
          
     }//GEN-LAST:event_jTableMouseClicked
	
     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.ButtonGroup buttonGroup1;
     private javax.swing.JLabel jLabel1;
     private javax.swing.JLabel jLabel2;
     private javax.swing.JLabel jLabel3;
     private javax.swing.JLabel jLabel4;
     private javax.swing.JLabel jLabel5;
     private javax.swing.JPanel jPanel1;
     private javax.swing.JPanel jPanel2;
     private javax.swing.JRadioButton jRadioButtonAllTask;
     private javax.swing.JRadioButton jRadioButtonCompletedTask;
     private javax.swing.JRadioButton jRadioButtonOpenedTask;
     private javax.swing.JRadioButton jRadioButtonRejectedTask;
     private javax.swing.JScrollPane jScrollPane1;
     private javax.swing.JSeparator jSeparator1;
     private javax.swing.JSeparator jSeparator4;
     public javax.swing.JTable jTable;
     private javax.swing.JTextField jTextField1;
     private org.jdesktop.swingx.JXDatePicker jXDatePickerMaxDate;
     private org.jdesktop.swingx.JXDatePicker jXDatePickerMinDate;
     // End of variables declaration//GEN-END:variables
}
