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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Redjan Shabani
 */
public class Person
{
	private static final String CREATE = "INSERT INTO Person (pnumber, fname, lname) VALUES (?,?,?)";
	public static boolean create(Connection conn, dc.Person person)
	{
		boolean success;
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(CREATE);
			ps.setString(1, person.getPnumber());
			ps.setString(2, person.getFname());
			ps.setString(3, person.getLname());
			
			ps.execute();
			
			success = true;
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
			success = false;
		}
		
		return success;
	}
	
	private static final String READ_ALL = "SELECT pnumber, fname, lname FROM Person ORDER BY fname ASC, lname ASC";
	public static List<dc.Person> read(Connection conn)
	{
		List<dc.Person> personList = new ArrayList();
		try
		{
			ResultSet rs = conn.prepareStatement(READ_ALL).executeQuery();			
			while(rs.next())
			{
				String pnumber = rs.getString("pnumber");
				String fname = rs.getString("fname");
				String lnme = rs.getString("lname");
				
				dc.Person person = new dc.Person(pnumber, fname, lnme);
				
				personList.add(person);
			}
			
			rs.close();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return personList;
	}
	
	private static final String READ_BY_ID = "SELECT pnumber, fname, lname FROM Person WHERE id = ? LIMIT 1";
	public static dc.Person read(Connection conn, int id)
	{
		dc.Person person = null;
		try
		{
			PreparedStatement ps = conn.prepareStatement(READ_BY_ID);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				String pnumber = rs.getString("pnumber");
				String fname = rs.getString("fname");
				String lnme = rs.getString("lname");
				
				person = new dc.Person(pnumber, fname, lnme);
			}
			
			rs.close();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return person;
	}
	
	private static final String READ_BY_PNUMBER = "SELECT pnumber, fname, lname FROM Person WHERE pnumber = ? LIMIT 1";
	public static dc.Person read(Connection conn, String pnumber)
	{
		dc.Person person = null;
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(READ_BY_PNUMBER);
			ps.setString(1, pnumber);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				String fname = rs.getString("fname");
				String lnme = rs.getString("lname");
				
				person = new dc.Person(pnumber, fname, lnme);
			}
			
			rs.close();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return person;
	}
	
	private static final String READ_ID = "SELECT id FROM Person WHERE pnumber = ?";
	public static int readId(Connection conn, dc.Person person)
	{
		int id = -1;
		try
		{
			PreparedStatement ps = conn.prepareStatement(READ_ID);
			ps.setString(1, person.getPnumber());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				id = rs.getInt("id");
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
		}
		return id;
	}
	
	private static final String DELETE = "DELETE FROM Person WHERE pnumber = ?";
	public static boolean delete(Connection conn, dc.Person person)
	{
		boolean success;
		try
		{
			PreparedStatement ps = conn.prepareStatement(DELETE);
			ps.setString(1, person.getPnumber());
			ps.execute();
			ps.close();
			success = true;
		}
		catch (SQLException ex)
		{
			Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
			success = false;
		}
		return success;
	}
}
