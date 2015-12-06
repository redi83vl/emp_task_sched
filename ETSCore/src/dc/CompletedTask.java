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

/**
 *
 * @author Redjan Shabani
 */
public class CompletedTask extends Task
{
	private final LocalDateTime completitionTime;
	private final String annotations;

	public CompletedTask(String identifier, Person creator, Person executor, String title, String description, LocalDateTime creationTime, LocalDateTime expirationTime, LocalDateTime completitionTime, String annotations)
	{
		super(identifier, creator, executor, title, description, creationTime, expirationTime);
		this.completitionTime = completitionTime;
		this.annotations = annotations;
	}

	public LocalDateTime getCompletitionTime()
	{
		return completitionTime;
	}

	public String getAnnotations()
	{
		return annotations;
	}
}