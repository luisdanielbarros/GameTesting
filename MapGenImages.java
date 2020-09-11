package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapGenImages {
	public static Image Draw(String pathtofile, Tileset tileset)
	{
		BufferedImage Toreturn;
		Graphics g;
		BufferedReader br = null;
		BufferedReader br2 = null;
		try {
			br = new BufferedReader(new FileReader(pathtofile));
			br2 = new BufferedReader(new FileReader(pathtofile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		int linesnumber = 0;
		int mapswidth = 0;
		int mapsheight = 0;
		int tempmapswidth = 0;
		int tempmapsheight = 0;
		String linesdigit = "";
		try {
			while ((line = br.readLine()) != null)
			{				
				linesnumber++;
				//each line corresponds to a height value
				//if a sub-layer has ended, and the program should jump to the (0, 0) coordinate of the map to draw on top
				if (line.substring(0, 1).equals("-") && line.length() == 1) tempmapsheight = 0;
				tempmapsheight++;
				if (tempmapsheight > mapsheight) mapsheight = tempmapsheight;
				//each number in a line corresponds to a width value
				for (int i = 0; i < line.length(); i++)
				{
					//if the char is a hyphen, it's time to jump layers
					if (line.substring(i, i + 1).equals("-") && line.length() == 1);
					//if the char is a regular number, add it to the string
					else if (line.substring(i, i + 1).equals("0") || line.substring(i, i + 1).equals("1") || line.substring(i, i + 1).equals("2") || line.substring(i, i + 1).equals("3") || line.substring(i, i + 1).equals("4") || line.substring(i, i + 1).equals("5") || line.substring(i, i + 1).equals("6") ||line.substring(i, i + 1).equals("7") || line.substring(i, i + 1).equals("8") || line.substring(i, i + 1).equals("9")) linesdigit += line.substring(i, i + 1);
					//if the char is a comma, it's time to put the number to use
					//commas indicate that a number has ended, that it should be put to use, and that the linesdigit string should be cleaned to be overwritten with next number
					else if (line.substring(i, i + 1).equals(","))
					{
						//if the previous number to the comma was the negative number '-1' it'll take that into account and not use it
						if (linesdigit.length() > 0)
						{
							tempmapswidth++;
							linesdigit = "";
						}
					}
					//if the char is the negative number '-1' it'll skip it, completely ignoring it
					//the program that's being used to make the text files marks empty tiles as '-1', hence the code ignoring and skipping this number only
					else if (line.length() > 1 && line.substring(i, i + 1).equals("-") && line.substring(i + 1, i + 2).equals("1"))
					{
						tempmapswidth++;
						linesdigit = "";						
						i++;
					}
					//if anything goes wrong, it'll warn me about it!
					else
					{
						System.out.println("class DrawImagefromTilegrid has found an issue, and has read the value '" + line.substring(i, i + 1) + "' at the line number " + linesnumber + " from the text file");
					}
					//if the last char in a line is a number, that number will also be counted even though it doesn't have a comma after
					if (i == line.length() - 1)
					{
						tempmapswidth++;
						linesdigit = "";
					}
				}
				linesdigit = "";
				if (tempmapswidth > mapswidth) mapswidth = tempmapswidth;
				tempmapswidth = 0;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toreturn = new BufferedImage(mapswidth * (int)Game.TileWidth, mapsheight * (int)Game.TileHeight, BufferedImage.TYPE_INT_ARGB);
		g = Toreturn.createGraphics();
		linesnumber = 0;
		tempmapsheight = 0;
		tempmapswidth = 0;
		try {
			while ((line = br2.readLine()) != null)
			{
				//each line corresponds to a height value
				//if a sub-layer has ended, and the program should jump to the (0, 0) coordinate of the map to draw on top
				if (line.substring(0, 1).equals("-") && line.length() == 1) tempmapsheight = -1;
				//if the program shouldn't jump layers and if it just started running through the lines, don't skip the first line
				else if (linesnumber > 0) tempmapsheight++;
				linesnumber++;
				//each number in a line corresponds to a width value
				for (int i = 0; i < line.length(); i++)
				{
					//if the char is a hyphen, it's time to jump layers
					if (line.substring(i, i + 1).equals("-") && line.length() == 1);
					//if the char is a regular number, add it to the string
					else if (line.substring(i, i + 1).equals("0") || line.substring(i, i + 1).equals("1") || line.substring(i, i + 1).equals("2") || line.substring(i, i + 1).equals("3") || line.substring(i, i + 1).equals("4") || line.substring(i, i + 1).equals("5") || line.substring(i, i + 1).equals("6") ||line.substring(i, i + 1).equals("7") || line.substring(i, i + 1).equals("8") || line.substring(i, i + 1).equals("9")) linesdigit += line.substring(i, i + 1);
					//if the char is a comma, it's time to put the number to use
					//commas indicate that a number has ended, that it should be put to use, and that the linesdigit string should be cleaned to be overwritten with next number
					else if (line.substring(i, i + 1).equals(","))
					{
						//if the previous number to the comma was the negative number '-1' it'll take that into account and not use it
						if (linesdigit.length() > 0 && !line.substring(i, i + 2).equals("-1"))
						{
							g.drawImage(tileset.getImageAt(Integer.parseInt(linesdigit)).getImage(), tempmapswidth * (int)Game.TileWidth, tempmapsheight * (int)Game.TileHeight, (int)Game.TileWidth, (int)Game.TileHeight, null);
							tempmapswidth++;
							linesdigit = "";
						}
					}
					//if the char is the negative number '-1' it'll skip it, completely ignoring it
					//the program that's being used to make the text files marks empty tiles as '-1', hence the code ignoring and skipping this number only
					else if (line.substring(i, i + 2).equals("-1"))
					{
						tempmapswidth++;
						linesdigit = "";
						i++;
					}
					//if anything goes wrong, it'll warn me about it!
					else
					{
						System.out.println("class DrawImagefromTilegrid has found an issue, and has read the value '" + line.substring(i, i + 1) + "' at the line number " + linesnumber + " from the text file");
					}
					//if the last char in a line is a number, that number will also be counted even though it doesn't have a comma after
					if (i == line.length() - 1 && linesdigit != "")
					{
						g.drawImage(tileset.getImageAt(Integer.parseInt(linesdigit)).getImage(), tempmapswidth * (int)Game.TileWidth, tempmapsheight * (int)Game.TileHeight, (int)Game.TileWidth, (int)Game.TileHeight, null);
						tempmapswidth++;
						linesdigit = "";
					}
				}
				linesdigit = "";
				tempmapswidth = 0;
			}
		} catch (NumberFormatException e) {
			System.out.println("class DrawImagefromTilegrid went in catch");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("class DrawImagefromTilegrid went in catch");
			e.printStackTrace();
		}
		g.drawImage(Toreturn, 0, 0, null);
		
		return Toreturn;
	}	
}
	
