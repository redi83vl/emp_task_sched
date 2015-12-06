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

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Redjan Shabani
 */
public class Task
{
	private final String identifier;
	private final Person creator;
	private final Person executor;
	private final String title;
	private final String description;
	private final LocalDateTime creationTime;
	private final LocalDateTime expirationTime;

	public Task(String identifier, Person creator, Person executor, String title, String description, LocalDateTime creationTime, LocalDateTime expirationTime)
	{
		this.identifier = identifier;
		this.creator = creator;
		this.executor = executor;
		this.creationTime = creationTime;
		this.title = title;
		this.description = description;
		this.expirationTime = expirationTime;
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public Person getCreator()
	{
		return creator;
	}

	public Person getExecutor()
	{
		return executor;
	}

	public LocalDateTime getCreationTime()
	{
		return creationTime;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDescription()
	{
		return description;
	}

	public LocalDateTime getExpirationTime()
	{
		return expirationTime;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 53 * hash + Objects.hashCode(this.identifier);
		hash = 53 * hash + Objects.hashCode(this.creator);
		hash = 53 * hash + Objects.hashCode(this.creationTime);
		hash = 53 * hash + Objects.hashCode(this.title);
		hash = 53 * hash + Objects.hashCode(this.description);
		hash = 53 * hash + Objects.hashCode(this.expirationTime);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Task other = (Task) obj;
		return Objects.equals(this.identifier, other.identifier);
	}
}