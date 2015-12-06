/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.task;

import dc.Person;
import java.util.List;

/**
 *
 * @author user
 */
public class JPanelTaskListStatistics extends javax.swing.JPanel
{
	public JPanelTaskListStatistics()
	{
		initComponents();
	}
	
	public void setTaskLists(dc.Person user, List<dc.Task> listExecutor, List<dc.Task> listCreator)
	{
		int ect = 0, ert = 0, eot = 0;
		for(dc.Task task : listExecutor)
		{
			if(task instanceof dc.CompletedTask)
				ect++;
			else if(task instanceof dc.RejectedTask)
				ert++;
			else
				eot++;
		}
		
		int cct = 0, crt = 0, cot = 0;
		for(dc.Task task : listCreator)
		{
			if(task instanceof dc.CompletedTask)
				cct++;
			else if(task instanceof dc.RejectedTask)
				crt++;
			else
				cot++;
		}
		
		if(user!=null)
			this.jLabelUserName.setText(user.toString());
		
		this.jLabelETO.setText(eot+"");
		this.jLabelETC.setText(ect+"");
		this.jLabelETR.setText(ert+"");
		this.jLabelCTO.setText(cot+"");
		this.jLabelCTC.setText(cct+"");
		this.jLabelCTR.setText(crt+"");
	}
	
	@SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
     private void initComponents()
     {
          java.awt.GridBagConstraints gridBagConstraints;

          jLabel15 = new javax.swing.JLabel();
          jLabelUserName = new javax.swing.JLabel();
          jPanel1 = new javax.swing.JPanel();
          jLabel13 = new javax.swing.JLabel();
          jLabel14 = new javax.swing.JLabel();
          jLabel1 = new javax.swing.JLabel();
          jLabelETO = new javax.swing.JLabel();
          jLabel10 = new javax.swing.JLabel();
          jLabelCTO = new javax.swing.JLabel();
          jLabel2 = new javax.swing.JLabel();
          jLabelETC = new javax.swing.JLabel();
          jLabel11 = new javax.swing.JLabel();
          jLabelCTC = new javax.swing.JLabel();
          jLabel3 = new javax.swing.JLabel();
          jLabelETR = new javax.swing.JLabel();
          jLabel12 = new javax.swing.JLabel();
          jLabelCTR = new javax.swing.JLabel();
          jSeparator1 = new javax.swing.JSeparator();
          jSeparator2 = new javax.swing.JSeparator();

          setLayout(new java.awt.GridBagLayout());

          jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
          jLabel15.setText("Axhenda e Detyrave");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
          add(jLabel15, gridBagConstraints);

          jLabelUserName.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
          jLabelUserName.setText("Emri Mbiemri [PNUMBER]");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 0;
          add(jLabelUserName, gridBagConstraints);

          jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Statistika", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
          jPanel1.setLayout(new java.awt.GridBagLayout());

          jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
          jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
          jLabel13.setText("Ne Ngarkim");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 2;
          gridBagConstraints.gridwidth = 2;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
          jPanel1.add(jLabel13, gridBagConstraints);

          jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
          jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
          jLabel14.setText("Te Krijuara");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 3;
          gridBagConstraints.gridy = 2;
          gridBagConstraints.gridwidth = 2;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
          jPanel1.add(jLabel14, gridBagConstraints);

          jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
          jLabel1.setForeground(java.awt.Color.blue);
          jLabel1.setText("Ne Pritje");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 4;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
          jPanel1.add(jLabel1, gridBagConstraints);

          jLabelETO.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
          jLabelETO.setForeground(java.awt.Color.blue);
          jLabelETO.setText("0");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 4;
          jPanel1.add(jLabelETO, gridBagConstraints);

          jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
          jLabel10.setForeground(java.awt.Color.blue);
          jLabel10.setText("Ne Pritje");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 3;
          gridBagConstraints.gridy = 4;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
          jPanel1.add(jLabel10, gridBagConstraints);

          jLabelCTO.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
          jLabelCTO.setForeground(java.awt.Color.blue);
          jLabelCTO.setText("0");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 4;
          gridBagConstraints.gridy = 4;
          gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
          jPanel1.add(jLabelCTO, gridBagConstraints);

          jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
          jLabel2.setForeground(java.awt.Color.green);
          jLabel2.setText("Perfunduar");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 5;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
          jPanel1.add(jLabel2, gridBagConstraints);

          jLabelETC.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
          jLabelETC.setForeground(java.awt.Color.green);
          jLabelETC.setText("0");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 5;
          jPanel1.add(jLabelETC, gridBagConstraints);

          jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
          jLabel11.setForeground(java.awt.Color.green);
          jLabel11.setText("Perfunduar");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 3;
          gridBagConstraints.gridy = 5;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
          jPanel1.add(jLabel11, gridBagConstraints);

          jLabelCTC.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
          jLabelCTC.setForeground(java.awt.Color.green);
          jLabelCTC.setText("0");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 4;
          gridBagConstraints.gridy = 5;
          gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
          jPanel1.add(jLabelCTC, gridBagConstraints);

          jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
          jLabel3.setForeground(java.awt.Color.red);
          jLabel3.setText("Refuzuar");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 6;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
          jPanel1.add(jLabel3, gridBagConstraints);

          jLabelETR.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
          jLabelETR.setForeground(java.awt.Color.red);
          jLabelETR.setText("0");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 1;
          gridBagConstraints.gridy = 6;
          gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
          jPanel1.add(jLabelETR, gridBagConstraints);

          jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
          jLabel12.setForeground(java.awt.Color.red);
          jLabel12.setText("Refuzuar");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 3;
          gridBagConstraints.gridy = 6;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
          gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
          jPanel1.add(jLabel12, gridBagConstraints);

          jLabelCTR.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
          jLabelCTR.setForeground(java.awt.Color.red);
          jLabelCTR.setText("0");
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 4;
          gridBagConstraints.gridy = 6;
          gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
          jPanel1.add(jLabelCTR, gridBagConstraints);

          jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 2;
          gridBagConstraints.gridy = 2;
          gridBagConstraints.gridheight = 5;
          gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
          gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
          jPanel1.add(jSeparator1, gridBagConstraints);
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 3;
          gridBagConstraints.gridwidth = 5;
          gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
          gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
          jPanel1.add(jSeparator2, gridBagConstraints);

          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 3;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
          gridBagConstraints.weighty = 1.0;
          add(jPanel1, gridBagConstraints);
     }// </editor-fold>//GEN-END:initComponents


     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.JLabel jLabel1;
     private javax.swing.JLabel jLabel10;
     private javax.swing.JLabel jLabel11;
     private javax.swing.JLabel jLabel12;
     private javax.swing.JLabel jLabel13;
     private javax.swing.JLabel jLabel14;
     private javax.swing.JLabel jLabel15;
     private javax.swing.JLabel jLabel2;
     private javax.swing.JLabel jLabel3;
     private javax.swing.JLabel jLabelCTC;
     private javax.swing.JLabel jLabelCTO;
     private javax.swing.JLabel jLabelCTR;
     private javax.swing.JLabel jLabelETC;
     private javax.swing.JLabel jLabelETO;
     private javax.swing.JLabel jLabelETR;
     private javax.swing.JLabel jLabelUserName;
     private javax.swing.JPanel jPanel1;
     private javax.swing.JSeparator jSeparator1;
     private javax.swing.JSeparator jSeparator2;
     // End of variables declaration//GEN-END:variables
}
