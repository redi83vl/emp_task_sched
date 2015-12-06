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

package gui;

import etc.ConnParam;
import etc.Exporter;
import gui.task.JPanelAddTask;
import gui.task.JPanelRejectCompleteTask;
import gui.task.JPanelShowTask;
import gui.task.JPanelTaskList;
import gui.task.JPanelTaskListSimple;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Redjan Shabani
 */
public class JFrameMainWindow extends javax.swing.JFrame
{
	public JFrameMainWindow()
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
		
		this.user = user;
		
		this.refresh();
		
		ActionListener alRefresh = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				refresh();
			}
		};
		Timer notifTimer = new Timer(60000, alRefresh);
		notifTimer.start();		
	}
	
	private List<dc.Task> expiredTaskList = new ArrayList();
	private void checkForExpiredTasks()
	{
		this.expiredTaskList = new ArrayList();
		List<dc.Task> clist = dao.Task.readByCreator(this.dbconn, this.user);
		List<dc.Task> elist = dao.Task.readByExecutor(this.dbconn, this.user);
		for(dc.Task task : clist)
			if(!(task instanceof dc.CompletedTask) && !(task instanceof dc.RejectedTask))
				if(task.getExpirationTime().isBefore(LocalDateTime.now()))
					expiredTaskList.add(task);
		for(dc.Task task : elist)
			if(!(task instanceof dc.CompletedTask) && !(task instanceof dc.RejectedTask))
				if(task.getExpirationTime().isBefore(LocalDateTime.now()))
					if(!this.expiredTaskList.contains(task))
						expiredTaskList.add(task);
		
		this.jButtonExpiredTasks.setText(this.expiredTaskList.size() + "");
		if(!this.expiredTaskList.isEmpty())
			this.jButtonExpiredTasks.setBackground(Color.RED);
		else
			this.jButtonExpiredTasks.setBackground(Color.GREEN);
	}
	
	private void refresh()
	{
		//load task lists
		List<dc.Task> tasksAsCreator = dao.Task.readByCreator(dbconn, user);
		this.jPanelTaskListAsCreator.setTaskList(tasksAsCreator);
		
		List<dc.Task> tasksAsExecutor = dao.Task.readByExecutor(dbconn, user);
		this.jPanelTaskListAsExecutor.setTaskList(tasksAsExecutor);
		
		this.jPanelTaskListStatistics1.setTaskLists(this.user, tasksAsExecutor, tasksAsCreator);
		
		//check for new notifications
		this.checkForNotifications();
		
		//check for expired tasks
		this.checkForExpiredTasks();
		
	}
	
	private boolean soundPlayed = false;
	private int notifCounter = 0;
	private void checkForNotifications()
	{
		List<dc.Notification> notif = dao.Notification.read(dbconn, user);
		if(notif.isEmpty())
		{
			this.jButtonNotifications.setBackground(Color.GREEN);
			this.jButtonNotifications.setText("0");
			this.notifCounter = 0;
			
			return;
		}
		
		this.jButtonNotifications.setBackground(java.awt.Color.RED);
		this.jButtonNotifications.setText(notif.size()+"");
		if(notifCounter>=notif.size())
		{
			this.notifCounter = notif.size();
			return;
		}
		this.notifCounter = notif.size();
		try
		{
			AudioInputStream audioInputStream;
			audioInputStream = AudioSystem.getAudioInputStream(new File("ping.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			this.soundPlayed = true;
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
		{
			Logger.getLogger(JFrameMainWindow.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	private void jTableTaskListAsCreatorClicked(java.awt.event.MouseEvent evt)
	{
		if(evt.getClickCount()!=2)
			return;
		dc.Task task = this.jPanelTaskListAsCreator.getSelectedTask();
		if(task == null)
			return;
		
		JPanelShowTask jpst = new JPanelShowTask();
		jpst.setTask(task);
		String[] buttons;
		buttons = new String[]{"Perfundo","Refuzo","Printo","Mbyll"};
		int choice = JOptionPane.showOptionDialog(
			this, 
			jpst, 
			"Detyre", 
			JOptionPane.YES_NO_CANCEL_OPTION, 
			JOptionPane.PLAIN_MESSAGE, 
			null, 
			buttons, 
			"Mbyll"
		);
		
		if(choice == 0)//task completed
		{
			if(task instanceof dc.CompletedTask)
			{
				JOptionPane.showMessageDialog(this, "Kjo detyre rezulton tashme si e perfunduar!", "Gabim!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(task instanceof dc.RejectedTask)
			{
				JOptionPane.showMessageDialog(this, "Kjo detyre rezulton tashme si e refuzuar!", "Gabim!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			JPanelRejectCompleteTask jprct = new JPanelRejectCompleteTask();
			int val = JOptionPane.showOptionDialog(
				this, 
				jprct, 
				"Perundim i Detyres", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				new String[]{"Ruaj","Mbyll"}, 
				"Mbyll"
			);
			if(val!=0)
				return;
			String annotations = jprct.getAnnotations();
			LocalDateTime completitionTime = jprct.getEndtime();
			dc.CompletedTask ctask = new dc.CompletedTask(
				task.getIdentifier(), 
				task.getCreator(), 
				task.getExecutor(), 
				task.getTitle(), 
				task.getDescription(), 
				task.getCreationTime(), 
				task.getExpirationTime(), 
				completitionTime, 
				annotations
			);
			dao.Task.update(this.dbconn, ctask);
			//Njoftim qe detyra perfundoi
			dc.Notification notif = new dc.Notification(
				LocalDateTime.now(),
				"Detyra " + ctask.getTitle() + " ,ne ngarkim te " + ctask.getExecutor().toString() + " ,perfundoi.",
				ctask.getCreator()
			);
			dao.Notification.create(this.dbconn, notif);
			this.refresh();
		}
		if(choice == 1)//task rejected
		{
			if(task instanceof dc.CompletedTask)
			{
				JOptionPane.showMessageDialog(this, "Kjo detyre rezulton tashme si e perfunduar!", "Gabim!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(task instanceof dc.RejectedTask)
			{
				JOptionPane.showMessageDialog(this, "Kjo detyre rezulton tashme si e refuzuar!", "Gabim!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			JPanelRejectCompleteTask jprct = new JPanelRejectCompleteTask();
			int val = JOptionPane.showOptionDialog(
				this, 
				jprct, 
				"Perundim i Detyres", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				new String[]{"Ruaj","Mbyll"}, 
				"Mbyll"
			);
			if(val!=0)
				return;
			String annotations = jprct.getAnnotations();
			LocalDateTime endTime = jprct.getEndtime();
			dc.RejectedTask rtask = new dc.RejectedTask(
				task.getIdentifier(), 
				task.getCreator(), 
				task.getExecutor(), 
				task.getTitle(), 
				task.getDescription(), 
				task.getCreationTime(), 
				task.getExpirationTime(), 
				endTime, 
				annotations
			);
			dao.Task.update(this.dbconn, rtask);
			//Njoftim qe detyra u refuzua
			dc.Notification notif = new dc.Notification(
				LocalDateTime.now(),
				"Detyra " + rtask.getTitle() + ", ne ngarkim te " + rtask.getExecutor().toString() + ", u refuzua.",
				rtask.getCreator()
			);
			dao.Notification.create(this.dbconn, notif);
			this.refresh();
		}
		if(choice == 2)//export task to PDF
		{
			JFileChooser jfc = new JFileChooser();
			jfc.setApproveButtonText("Ruaj");
			jfc.setDialogTitle("Ruaj ne PDF");
			jfc.setDialogType(JFileChooser.SAVE_DIALOG);
			jfc.setCurrentDirectory(new File("./"));
			jfc.setSelectedFile(new File(task.getIdentifier() + ".pdf"));
			jfc.setFileFilter(new FileNameExtensionFilter("PDF Documents","pdf"));
			
			if(jfc.showSaveDialog(jpst) == JFileChooser.APPROVE_OPTION)
			{
				File file = jfc.getSelectedFile();
				System.out.println(file);
				Exporter.saveAsPDF(file, task);
			}
			
		}
	}
	
	private void jTableTaskListAsExecutorClicked(java.awt.event.MouseEvent evt)
	{
		if(evt.getClickCount()!=2)
			return;
		dc.Task task = this.jPanelTaskListAsExecutor.getSelectedTask();
		if(task == null)
			return;
		
		JPanelShowTask jpst = new JPanelShowTask();
		jpst.setTask(task);
		String[] buttons;
		
		buttons = new String[]{"Perfundo","Refuzo","Printo","Mbyll"};
		int choice = JOptionPane.showOptionDialog(
			this, 
			jpst, 
			"Detyre", 
			JOptionPane.YES_NO_CANCEL_OPTION, 
			JOptionPane.PLAIN_MESSAGE, 
			null, 
			buttons, 
			"Mbyll"
		);
		
		if(choice == 0)//task completed
		{
			if(task instanceof dc.CompletedTask)
			{
				JOptionPane.showMessageDialog(this, "Kjo detyre rezulton tashme si e perfunduar!", "Gabim!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(task instanceof dc.RejectedTask)
			{
				JOptionPane.showMessageDialog(this, "Kjo detyre rezulton tashme si e refuzuar!", "Gabim!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			JPanelRejectCompleteTask jprct = new JPanelRejectCompleteTask();
			int val = JOptionPane.showOptionDialog(
				this, 
				jprct, 
				"Perundim i Detyres", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				new String[]{"Ruaj","Mbyll"}, 
				"Mbyll"
			);
			if(val!=0)
				return;
			String annotations = jprct.getAnnotations();
			LocalDateTime completitionTime = jprct.getEndtime();
			dc.CompletedTask ctask = new dc.CompletedTask(
				task.getIdentifier(), 
				task.getCreator(), 
				task.getExecutor(), 
				task.getTitle(), 
				task.getDescription(), 
				task.getCreationTime(), 
				task.getExpirationTime(), 
				completitionTime, 
				annotations
			);
			dao.Task.update(this.dbconn, ctask);
			
			//Njoftim qe detyra perundoi
			dc.Notification notif = new dc.Notification(
				LocalDateTime.now(),
				"Detyra " + ctask.getTitle() + ", ne ngarkim te " + ctask.getExecutor().toString() + " ,perfundoi.",
				ctask.getCreator()
			);
			dao.Notification.create(this.dbconn, notif);
			
			this.refresh();
		}
		if(choice == 1)//task rejected
		{
			if(task instanceof dc.CompletedTask)
			{
				JOptionPane.showMessageDialog(this, "Kjo detyre rezulton tashme si e perfunduar!", "Gabim!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(task instanceof dc.RejectedTask)
			{
				JOptionPane.showMessageDialog(this, "Kjo detyre rezulton tashme si e refuzuar!", "Gabim!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			JPanelRejectCompleteTask jprct = new JPanelRejectCompleteTask();
			int val = JOptionPane.showOptionDialog(
				this, 
				jprct, 
				"Perundim i Detyres", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				new String[]{"Ruaj","Mbyll"}, 
				"Mbyll"
			);
			if(val!=0)
				return;
			String annotations = jprct.getAnnotations();
			LocalDateTime endTime = jprct.getEndtime();
			dc.RejectedTask rtask = new dc.RejectedTask(
				task.getIdentifier(), 
				task.getCreator(), 
				task.getExecutor(), 
				task.getTitle(), 
				task.getDescription(), 
				task.getCreationTime(), 
				task.getExpirationTime(), 
				endTime, 
				annotations
			);
			dao.Task.update(this.dbconn, rtask);
			
			//Njoftim qe detyra u refuzua
			dc.Notification notif = new dc.Notification(
				LocalDateTime.now(),
				"Detyra " + rtask.getTitle() + ", ne ngarkim te " + rtask.getExecutor().toString() + ", u refuzua.",
				rtask.getCreator()
			);
			dao.Notification.create(this.dbconn, notif);
			
			this.refresh();
		}
		if(choice == 2)//export task to PDF
		{
			JFileChooser jfc = new JFileChooser();
			jfc.setApproveButtonText("Ruaj");
			jfc.setDialogTitle("Ruaj ne PDF");
			jfc.setDialogType(JFileChooser.SAVE_DIALOG);
			jfc.setCurrentDirectory(new File("./"));
			jfc.setSelectedFile(new File(task.getIdentifier() + ".pdf"));
			jfc.setFileFilter(new FileNameExtensionFilter("PDF Documents","pdf"));
			
			if(jfc.showSaveDialog(jpst) == JFileChooser.APPROVE_OPTION)
			{
				File file = jfc.getSelectedFile();
				System.out.println(file);
				Exporter.saveAsPDF(file, task);
			}
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
     private void initComponents()
     {
          java.awt.GridBagConstraints gridBagConstraints;

          jTabbedPane1 = new javax.swing.JTabbedPane();
          jPanel1 = new javax.swing.JPanel();
          jPanelTaskListStatistics1 = new gui.task.JPanelTaskListStatistics();
          jPanel2 = new javax.swing.JPanel();
          jPanelTaskListAsExecutor = new gui.task.JPanelTaskList();
          jPanel3 = new javax.swing.JPanel();
          jPanelTaskListAsCreator = new gui.task.JPanelTaskList();
          jPanel4 = new javax.swing.JPanel();
          jButton1 = new javax.swing.JButton();
          jButton2 = new javax.swing.JButton();
          jPanel5 = new javax.swing.JPanel();
          jButtonNotifications = new javax.swing.JButton();
          jButtonExpiredTasks = new javax.swing.JButton();
          jPanelHeader1 = new gui.JPanelHeader();
          jMenuBar1 = new javax.swing.JMenuBar();
          jMenu1 = new javax.swing.JMenu();
          jMenuItem2 = new javax.swing.JMenuItem();
          jMenuItem6 = new javax.swing.JMenuItem();
          jMenuItem1 = new javax.swing.JMenuItem();
          jMenu2 = new javax.swing.JMenu();
          jMenuItem3 = new javax.swing.JMenuItem();
          jMenuItem5 = new javax.swing.JMenuItem();

          setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
          setExtendedState(JFrame.MAXIMIZED_BOTH);
          addWindowListener(new java.awt.event.WindowAdapter()
          {
               public void windowClosing(java.awt.event.WindowEvent evt)
               {
                    formWindowClosing(evt);
               }
          });

          jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

          jPanel1.setLayout(new java.awt.GridBagLayout());
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
          gridBagConstraints.weightx = 1.0;
          gridBagConstraints.weighty = 1.0;
          jPanel1.add(jPanelTaskListStatistics1, gridBagConstraints);

          java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("gui/res"); // NOI18N
          jTabbedPane1.addTab(bundle.getString("home"), jPanel1); // NOI18N

          jPanel2.setLayout(new java.awt.BorderLayout());
          jPanel2.add(jPanelTaskListAsExecutor, java.awt.BorderLayout.CENTER);
          this.jPanelTaskListAsCreator.jTable.addMouseListener(
               new MouseAdapter()
               {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt)
                    {
                         jTableTaskListAsCreatorClicked(evt);
                    }
               }
          );

          jTabbedPane1.addTab(bundle.getString("task_in_charge"), jPanel2); // NOI18N

          jPanel3.setLayout(new java.awt.BorderLayout());
          jPanel3.add(jPanelTaskListAsCreator, java.awt.BorderLayout.CENTER);

          jTabbedPane1.addTab(bundle.getString("task_charged"), jPanel3); // NOI18N

          getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
          this.jPanelTaskListAsExecutor.jTable.addMouseListener(
               new MouseAdapter()
               {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt)
                    {
                         jTableTaskListAsExecutorClicked(evt);
                    }
               }
          );

          jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
          jPanel4.setLayout(new java.awt.GridBagLayout());

          jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/1439673361_Gnome-View-Refresh-64.png"))); // NOI18N
          jButton1.setToolTipText("Rifresko");
          jButton1.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jButton1ActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 4;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
          gridBagConstraints.weighty = 1.0;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
          jPanel4.add(jButton1, gridBagConstraints);

          jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/1439673336_document-open.png"))); // NOI18N
          jButton2.setToolTipText("Detyre e Re");
          jButton2.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jButton2ActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.gridx = 0;
          gridBagConstraints.gridy = 1;
          gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
          gridBagConstraints.weighty = 1.0;
          gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
          jPanel4.add(jButton2, gridBagConstraints);

          getContentPane().add(jPanel4, java.awt.BorderLayout.LINE_START);

          jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
          jPanel5.setLayout(new java.awt.GridBagLayout());

          jButtonNotifications.setBackground(java.awt.Color.green);
          jButtonNotifications.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jButtonNotifications.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/1439774006_43.Bell.png"))); // NOI18N
          jButtonNotifications.setText("0");
          jButtonNotifications.setToolTipText("Njoftimet e fundit!");
          jButtonNotifications.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jButtonNotificationsActionPerformed(evt);
               }
          });
          gridBagConstraints = new java.awt.GridBagConstraints();
          gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
          gridBagConstraints.weightx = 0.5;
          jPanel5.add(jButtonNotifications, gridBagConstraints);

          jButtonExpiredTasks.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
          jButtonExpiredTasks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/1441419925_35.Alarm-Clock.png"))); // NOI18N
          jButtonExpiredTasks.setText("0");
          jButtonExpiredTasks.setToolTipText("Detyra ende te pakryera brenda afatit.");
          jButtonExpiredTasks.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jButtonExpiredTasksActionPerformed(evt);
               }
          });
          jPanel5.add(jButtonExpiredTasks, new java.awt.GridBagConstraints());

          getContentPane().add(jPanel5, java.awt.BorderLayout.PAGE_START);
          getContentPane().add(jPanelHeader1, java.awt.BorderLayout.SOUTH);

          jMenu1.setText("File");

          jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
          jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/small-document-open.png"))); // NOI18N
          jMenuItem2.setText("Detyre e Re");
          jMenuItem2.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jMenuItem2ActionPerformed(evt);
               }
          });
          jMenu1.add(jMenuItem2);

          jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
          jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/small-view-refresh.png"))); // NOI18N
          jMenuItem6.setText("Rifresko");
          jMenu1.add(jMenuItem6);

          jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, java.awt.event.InputEvent.CTRL_MASK));
          jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/lock.png"))); // NOI18N
          jMenuItem1.setText("Mbyll Programin");
          jMenuItem1.addActionListener(new java.awt.event.ActionListener()
          {
               public void actionPerformed(java.awt.event.ActionEvent evt)
               {
                    jMenuItem1ActionPerformed(evt);
               }
          });
          jMenu1.add(jMenuItem1);

          jMenuBar1.add(jMenu1);

          jMenu2.setText("Info");

          jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
          jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/help.png"))); // NOI18N
          jMenuItem3.setText("Ndihma");
          jMenu2.add(jMenuItem3);

          jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.CTRL_MASK));
          jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/about.png"))); // NOI18N
          jMenuItem5.setText("Rreth Nesh");
          jMenu2.add(jMenuItem5);

          jMenuBar1.add(jMenu2);

          setJMenuBar(jMenuBar1);

          pack();
     }// </editor-fold>//GEN-END:initComponents

	private JPanelAddTask jpat = new JPanelAddTask();
     private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem2ActionPerformed
     {//GEN-HEADEREND:event_jMenuItem2ActionPerformed
          if(this.dbconn==null || this.user == null)
		{
			JOptionPane.showMessageDialog(this, "U verifikua nje gabim ne DB!", "Gabim!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		jpat.setConnParam(this.dbconn,this.user);
          int choice = JOptionPane.showOptionDialog(
               this,
               jpat,
               "Detyre e Re",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.PLAIN_MESSAGE,
               null,
               new String[]{"Ruaj","Anullo"},
               "Anullo"
          );
          if(choice != 0)
			return;

		dc.Task task = jpat.getTask();
          if(task == null)
          {
               JOptionPane.showMessageDialog(this, "Ploteso te gjitha fushat e detyrueshme!", "Gabim!", JOptionPane.ERROR_MESSAGE);
               this.jMenuItem2ActionPerformed(evt);
               return;
          }

          boolean dbsuccess = dao.Task.create(dbconn, task);
		dao.Notification.create(
			this.dbconn, 
			new dc.Notification(
				LocalDateTime.now(), 
				"Detyre e re prej " + task.getCreator() + ": " + task.getTitle(), 
				task.getExecutor()
			)
		);
          if(dbsuccess)
          {
               JOptionPane.showMessageDialog(this, "Detyra u ruajt me sukses!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
          }
          else
          {
               JOptionPane.showMessageDialog(this, "U verifikua nje gabim ne DB!", "Gabim!", JOptionPane.ERROR_MESSAGE);
			this.jMenuItem2ActionPerformed(evt);
			return;
          }
		this.jpat = new JPanelAddTask();
		this.refresh();
     }//GEN-LAST:event_jMenuItem2ActionPerformed

     private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
     {//GEN-HEADEREND:event_jButton1ActionPerformed
          this.refresh();
     }//GEN-LAST:event_jButton1ActionPerformed

     private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
     {//GEN-HEADEREND:event_jButton2ActionPerformed
          this.jMenuItem2ActionPerformed(null);
     }//GEN-LAST:event_jButton2ActionPerformed

     private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
     {//GEN-HEADEREND:event_formWindowClosing
		int choice = JOptionPane.showConfirmDialog(
			this, 
			"Konfirmo mbzlljen e programit!", 
			"Konfirmim!", 
			JOptionPane.OK_CANCEL_OPTION, 
			JOptionPane.WARNING_MESSAGE
		);
		if(choice!=0)
			return;
		
		if(this.dbconn != null)
		{
			try
			{
				System.out.print("Closing DB Connection ... ");
				this.dbconn.close();
				System.out.println("done!");
			}
			catch (SQLException ex)
			{
				Logger.getLogger(JFrameMainWindow.class.getName()).log(Level.SEVERE, null, ex);
			}
			
		}
		System.exit(0);
     }//GEN-LAST:event_formWindowClosing

     private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
     {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
          this.formWindowClosing(null);
     }//GEN-LAST:event_jMenuItem1ActionPerformed

     private void jButtonNotificationsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonNotificationsActionPerformed
     {//GEN-HEADEREND:event_jButtonNotificationsActionPerformed
          JList jln = new JList();
		List<dc.Notification> notifList = dao.Notification.read(this.dbconn, this.user);
		DefaultListModel dlm = new DefaultListModel();
		for(dc.Notification notif : notifList)
			dlm.addElement("<html>" + "<b>" + notif.getContent() + "</b>" + "</html>");
		jln.setModel(dlm);
		JOptionPane.showMessageDialog(this, jln, "Njoftime", JOptionPane.PLAIN_MESSAGE);
		dao.Notification.delete(this.dbconn,this.user);
		this.refresh();
     }//GEN-LAST:event_jButtonNotificationsActionPerformed

     private void jButtonExpiredTasksActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonExpiredTasksActionPerformed
     {//GEN-HEADEREND:event_jButtonExpiredTasksActionPerformed
          JPanelTaskListSimple jptls = new JPanelTaskListSimple();
		jptls.setTaskList(this.expiredTaskList);
		JOptionPane.showMessageDialog(this, jptls, "Detyrat e paperfunduara brenda afatit!", JOptionPane.PLAIN_MESSAGE);
     }//GEN-LAST:event_jButtonExpiredTasksActionPerformed

	public static void main(String args[])
	{
		try
		{
			javax.swing.UIManager.setLookAndFeel(com.jtattoo.plaf.luna.LunaLookAndFeel.class.getName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(JFrameMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				ConnParam cp = new ConnParam("params.xml");
				String dbdrv = cp.dbdrv;
				String dburl = cp.dburl;
				String dbusr = cp.dbusr;
				String dbpwd = cp.dbpwd;
				String user = cp.user;
				
				try
				{
					System.out.print("Opening DB Connection ... ");
					Class.forName(dbdrv);
					Connection dbconn = DriverManager.getConnection(
						dburl,
						dbusr,
						dbpwd
					);
					System.out.println("done!");
					
					JFrameMainWindow jfmw = new JFrameMainWindow();
					jfmw.setConnParam(dbconn, dao.Person.read(dbconn, user ));
					jfmw.setVisible(true);
				}
				catch (ClassNotFoundException | SQLException ex)
				{
					Logger.getLogger(JFrameMainWindow.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
	}

     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.JButton jButton1;
     private javax.swing.JButton jButton2;
     private javax.swing.JButton jButtonExpiredTasks;
     private javax.swing.JButton jButtonNotifications;
     private javax.swing.JMenu jMenu1;
     private javax.swing.JMenu jMenu2;
     private javax.swing.JMenuBar jMenuBar1;
     private javax.swing.JMenuItem jMenuItem1;
     private javax.swing.JMenuItem jMenuItem2;
     private javax.swing.JMenuItem jMenuItem3;
     private javax.swing.JMenuItem jMenuItem5;
     private javax.swing.JMenuItem jMenuItem6;
     private javax.swing.JPanel jPanel1;
     private javax.swing.JPanel jPanel2;
     private javax.swing.JPanel jPanel3;
     private javax.swing.JPanel jPanel4;
     private javax.swing.JPanel jPanel5;
     private gui.JPanelHeader jPanelHeader1;
     private gui.task.JPanelTaskList jPanelTaskListAsCreator;
     private gui.task.JPanelTaskList jPanelTaskListAsExecutor;
     private gui.task.JPanelTaskListStatistics jPanelTaskListStatistics1;
     private javax.swing.JTabbedPane jTabbedPane1;
     // End of variables declaration//GEN-END:variables
}
