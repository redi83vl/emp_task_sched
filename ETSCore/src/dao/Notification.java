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

import dc.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Notification
{
	private static final String CREATE = "INSERT INTO Notification (timeStamp, content, person) VALUES (?,?,?)";
	public static boolean create(Connection conn, dc.Notification notif)
	{
		boolean success = false;
		try
		{
			PreparedStatement ps = conn.prepareStatement(CREATE);
			ps.setTimestamp(1, java.sql.Timestamp.valueOf(notif.getTimeStamp()));
			ps.setString(2, notif.getContent());
			ps.setInt(3, dao.Person.readId(conn, notif.getPerson()));
			ps.execute();
			ps.close();
			success = true;
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
		}
		return success;
	}
	
	private static final String READ = "SELECT timeStamp, content, person FROM Notification WHERE person = ?";
	public static List<dc.Notification> read(Connection conn, dc.Person person)
	{
		List<dc.Notification> notifList = new ArrayList();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(READ);
			ps.setInt(1, dao.Person.readId(conn, person));
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				LocalDateTime timeStamp = rs.getTimestamp("timeStamp").toLocalDateTime();
				String content = rs.getString("content");
				notifList.add(new dc.Notification(timeStamp, content, person));
			}
			rs.close();
			ps.close();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
		}
		return notifList;
	}

	private static final String DELETE = "DELETE FROM Notification WHERE person = ?" ;
	public static void delete(Connection conn, dc.Person user)
	{
		try
		{
			PreparedStatement ps = conn.prepareStatement(DELETE);
			ps.setInt(1, dao.Person.readId(conn, user));
			ps.execute();
			ps.close();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
