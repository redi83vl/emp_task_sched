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
package etc;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import dc.Task;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Exporter
{

	public static void saveAsPDF(File file, Task task)
	{
		try
		{
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			
			
			
			document.open();
			Paragraph taskid = new Paragraph(
				"ID e Detyres: " + task.getIdentifier(),
				FontFactory.getFont(
					FontFactory.COURIER, 
					10, Font.ITALIC
				)
			);
			document.add(taskid);
			
			Paragraph creator = new Paragraph(
				"Krijuar nga " + task.getCreator().toString() + "[" + task.getCreationTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH.mm")) + "]",
				FontFactory.getFont(
					FontFactory.COURIER, 
					10, Font.ITALIC
				)
			);
			document.add(creator);
			
			Paragraph executor = new Paragraph(
				"Per zbatim prej: " + task.getExecutor().toString(),
				FontFactory.getFont(
					FontFactory.COURIER, 
					10, Font.ITALIC
				)
			);
			document.add(executor);
			
			Paragraph title = new Paragraph(task.getTitle().toUpperCase(),FontFactory.getFont(FontFactory.COURIER,14,Font.BOLD));
			document.add(title);
			
			Paragraph description = new Paragraph(task.getDescription(),FontFactory.getFont(FontFactory.COURIER,12,Font.NORMAL));
			document.add(description);
			
			PdfPCell cell = new PdfPCell();
			cell.setBorder(Rectangle.BOTTOM);

			PdfPTable table = new PdfPTable(1);
			table.addCell(cell);
			table.setWidthPercentage(100f);
			document.add(table);
			
			if(task instanceof dc.CompletedTask)
			{				
				document.add(new Paragraph("Gjendja: Perfunduar [" + ((dc.CompletedTask)task).getCompletitionTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm")) + "]",FontFactory.getFont(FontFactory.COURIER,10, Font.ITALIC)));
				document.add(new Paragraph("Shenimet e Zbatuesit".toUpperCase(),FontFactory.getFont(FontFactory.COURIER,14,Font.BOLD)));
				document.add(new Paragraph(((dc.CompletedTask)task).getAnnotations(),FontFactory.getFont(FontFactory.COURIER,12,Font.NORMAL)));
			}
			else if(task instanceof dc.RejectedTask)
			{
				document.add(new Paragraph("Gjendja: Refuzuar [" + ((dc.RejectedTask)task).getRejectionTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm")) + "]",FontFactory.getFont(FontFactory.COURIER,10, Font.ITALIC)));
				document.add(new Paragraph("Shenimet e Zbatuesit".toUpperCase(),FontFactory.getFont(FontFactory.COURIER,14,Font.BOLD)));
				document.add(new Paragraph(((dc.RejectedTask)task).getAnnotations(),FontFactory.getFont(FontFactory.COURIER,12,Font.NORMAL)));
			}
			else
			{
				document.add(new Paragraph("Gjendja: Ne Pritje",FontFactory.getFont(FontFactory.COURIER,10, Font.ITALIC)));
			}
			
			document.close();
			
			Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
			p.waitFor();
		}
		catch (DocumentException | InterruptedException | IOException ex)
		{
			Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
