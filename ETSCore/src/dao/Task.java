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
package dao;

import dc.CompletedTask;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Redjan Shabani
 */
public class Task
{
	
	//<editor-fold desc="CREATE">
	private static final String CREATE = "INSERT INTO Task (identifier, creator, executor, title, description, creationTime, expirationTime) VALUES (?,?,?,?,?,?,?)";
	public static boolean create(Connection conn, dc.Task task)
	{
		boolean success;
		try
		{
			PreparedStatement ps = conn.prepareStatement(CREATE);
			ps.setString(1, task.getIdentifier());
			ps.setInt(2,dao.Person.readId(conn, task.getCreator()));
			ps.setInt(3, dao.Person.readId(conn, task.getExecutor()));
			ps.setString(4, task.getTitle());
			ps.setString(5, task.getDescription());
			ps.setTimestamp(6, Timestamp.valueOf(task.getCreationTime()));
			ps.setTimestamp(7, Timestamp.valueOf(task.getExpirationTime()));
			ps.execute();
			success = true;
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
			success = false;
		}
		return success;
	}
	
	public static List<dc.Task> readByCreator(Connection conn, dc.Person person)
	{
		List<dc.Task> taskList = new ArrayList();
		System.out.print("Collecting tasks created by " + person + " ... ");
		try
		{
			CallableStatement cs = conn.prepareCall("CALL getTasksByCreator(?)");
			cs.setString(1, person.getPnumber());
			ResultSet rs = cs.executeQuery();
			while(rs.next())
			{
				int taskId = rs.getInt("id");
				String ident = rs.getString("identifier");
				dc.Person creator = dao.Person.read(conn, rs.getInt("creator"));
				dc.Person executor = dao.Person.read(conn, rs.getInt("executor"));
				String title = rs.getString("title");
				String desc = rs.getString("description");
				LocalDateTime crTime = rs.getTimestamp("creationTime").toLocalDateTime();
				LocalDateTime exTime = rs.getTimestamp("expirationTime").toLocalDateTime();
				
				dc.Task task = new dc.Task(ident, creator, executor, title, desc, crTime, exTime);
				
				//check if the current task is a completed task
				PreparedStatement psComp = conn.prepareStatement("SELECT task, completitionTime, annotations FROM CompletedTask WHERE task = ? LIMIT 1");
				psComp.setInt(1, taskId);
				ResultSet rsComp = psComp.executeQuery();
				if(rsComp.next())
				{
					LocalDateTime coTime = rsComp.getTimestamp("completitionTime").toLocalDateTime();
					String annotations = rsComp.getString("annotations");
					task = new dc.CompletedTask(ident, creator, executor, title, desc, crTime, exTime, coTime, annotations);
				}
				
				//check if the current task is a rejected task
				PreparedStatement psRej = conn.prepareStatement("SELECT task, rejectionTime, annotations FROM RejectedTask WHERE task = ? LIMIT 1");
				psRej.setInt(1, taskId);
				ResultSet rsRej = psRej.executeQuery();
				if(rsRej.next())
				{
					LocalDateTime rejTime = rsRej.getTimestamp("rejectionTime").toLocalDateTime();
					String annotations = rsRej.getString("annotations");
					task = new dc.RejectedTask(ident, creator, executor, title, desc, crTime, exTime, rejTime, annotations);
				}
				
				taskList.add(task);
			}
			
			rs.close();
			cs.close();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println(taskList.size() + " tasks found!");
		return taskList;
	}
	
	public static List<dc.Task> readByExecutor(Connection conn, dc.Person person)
	{
		List<dc.Task> taskList = new ArrayList();
		System.out.print("Collecting tasks (to be) executed by " + person + " ... ");
		try
		{
			CallableStatement cs = conn.prepareCall("CALL getTasksByExecutor(?)");
			cs.setString(1, person.getPnumber());
			ResultSet rs = cs.executeQuery();
			while(rs.next())
			{
				int taskId = rs.getInt("id");
				String ident = rs.getString("identifier");
				dc.Person creator = dao.Person.read(conn, rs.getInt("creator"));
				dc.Person executor = dao.Person.read(conn, rs.getInt("executor"));
				String title = rs.getString("title");
				String desc = rs.getString("description");
				LocalDateTime crTime = rs.getTimestamp("creationTime").toLocalDateTime();
				LocalDateTime exTime = rs.getTimestamp("expirationTime").toLocalDateTime();
				
				dc.Task task = new dc.Task(ident, creator, executor, title, desc, crTime, exTime);
				
				//check if the current task is a completed task
				PreparedStatement psComp = conn.prepareStatement("SELECT task, completitionTime, annotations FROM CompletedTask WHERE task = ? LIMIT 1");
				psComp.setInt(1, taskId);
				ResultSet rsComp = psComp.executeQuery();
				if(rsComp.next())
				{
					LocalDateTime coTime = rsComp.getTimestamp("completitionTime").toLocalDateTime();
					String annotations = rsComp.getString("annotations");
					task = new dc.CompletedTask(ident, creator, executor, title, desc, crTime, exTime, coTime, annotations);
				}
				
				//check if the current task is a rejected task
				PreparedStatement psRej = conn.prepareStatement("SELECT task, rejectionTime, annotations FROM RejectedTask WHERE task = ? LIMIT 1");
				psRej.setInt(1, taskId);
				ResultSet rsRej = psRej.executeQuery();
				if(rsRej.next())
				{
					LocalDateTime rejTime = rsRej.getTimestamp("rejectionTime").toLocalDateTime();
					String annotations = rsRej.getString("annotations");
					task = new dc.RejectedTask(ident, creator, executor, title, desc, crTime, exTime, rejTime, annotations);
				}
				
				taskList.add(task);
			}
			
			rs.close();
			cs.close();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println(taskList.size() + " tasks found!");
		return taskList;
	}
	//<editor-fold>

	private static final String CREATE_COMPLETED = "INSERT INTO CompletedTask (task, completitionTime, annotations) VALUES (?,?,?)";
	public static boolean update(Connection dbconn, dc.CompletedTask task)
	{
		try
		{
			PreparedStatement ps = dbconn.prepareStatement(CREATE_COMPLETED);
			ps.setInt(1, readId(dbconn, task));
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(task.getCompletitionTime()));
			ps.setString(3, task.getAnnotations());
			ps.execute();
			ps.close();
			return true;
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}
	
	private static final String CREATE_REJECTED = "INSERT INTO RejectedTask (task, rejectionTime, annotations) VALUES (?,?,?)";
	public static boolean update(Connection dbconn, dc.RejectedTask task)
	{
		boolean success = false;
		
		try
		{
			PreparedStatement ps = dbconn.prepareStatement(CREATE_REJECTED);
			ps.setInt(1, readId(dbconn, task));
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(task.getRejectionTime()));
			ps.setString(3, task.getAnnotations());
			ps.execute();
			ps.close();
			success = true;
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return success;
	}

	private static final String READ_ID = "SELECT id FROM Task WHERE identifier = ? LIMIT 1";
	private static int readId(Connection dbconn, dc.Task task)
	{
		int id = -1;
		
		try
		{
			PreparedStatement ps = dbconn.prepareStatement(READ_ID);
			ps.setString(1, task.getIdentifier());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				id = rs.getInt("id");
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return id;
	}
}