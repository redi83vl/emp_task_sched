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
public class JPanelTaskListSimple extends javax.swing.JPanel
{
	public JPanelTaskListSimple()
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
		this.updateContent();
	}
	
	List<dc.Task> taskListF = new ArrayList();
	private void updateContent()
	{
		this.taskListF = new ArrayList();
		
		//Adding filtered Tasks
		for (Task task : this.taskList)
		{
			
			String str = this.jTextField1.getText().toLowerCase();
			if(!task.getCreator().toString().concat(task.getExecutor().toString()).toLowerCase().concat(task.getTitle().toLowerCase()).contains(str))
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

          buttonGroup1 = new javax.swing.ButtonGroup();
          jScrollPane1 = new javax.swing.JScrollPane();
          jTable = new javax.swing.JTable();
          jTextField1 = new javax.swing.JTextField();

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

          jTextField1.setColumns(25);
          jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder("Krijuesi/Zbatuesi"));

          javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
          this.setLayout(layout);
          layout.setHorizontalGroup(
               layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(layout.createSequentialGroup()
                              .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addGap(0, 0, Short.MAX_VALUE))
                         .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE))
                    .addGap(5, 5, 5))
          );
          layout.setVerticalGroup(
               layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(5, 5, 5))
          );
     }// </editor-fold>//GEN-END:initComponents

     private void jTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTableMouseClicked
     {//GEN-HEADEREND:event_jTableMouseClicked
          
     }//GEN-LAST:event_jTableMouseClicked
	
     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.ButtonGroup buttonGroup1;
     private javax.swing.JScrollPane jScrollPane1;
     public javax.swing.JTable jTable;
     private javax.swing.JTextField jTextField1;
     // End of variables declaration//GEN-END:variables
}
