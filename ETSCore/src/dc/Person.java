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
package dc;

import java.util.Objects;

/**
 *
 * @author user
 */
public class Person
{
	private final String pnumber;
	private final String fname;
	private final String lname;

	public Person(String pnumber, String fname, String lname)
	{
		this.pnumber = pnumber.toUpperCase();
		this.fname = fname.substring(0,1) + fname.substring(1).toLowerCase();
		this.lname = lname.substring(0,1) + lname.substring(1).toLowerCase();
	}

	public String getPnumber() { return pnumber; }

	public String getFname() { return fname; }

	public String getLname() { return lname; }

	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 31 * hash + Objects.hashCode(this.pnumber);
		hash = 31 * hash + Objects.hashCode(this.fname);
		hash = 31 * hash + Objects.hashCode(this.lname);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Person other = (Person) obj;
		return Objects.equals(this.pnumber, other.pnumber);
	}

	@Override
	public String toString() { return this.fname + " " + this.lname; }
}