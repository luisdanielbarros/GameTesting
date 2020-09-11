package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import main.Collisions;

public class MapGenCollisions {
	public static Collisions[][] Set(String pathtocollisionsfile)
	{
		BufferedReader br = null;
		BufferedReader br2 = null;
		try {
			br = new BufferedReader(new FileReader(pathtocollisionsfile));
			br2 = new BufferedReader(new FileReader(pathtocollisionsfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		int mapswidth = 0;
		int mapsheight = 0;
		int tempmapswidth = 0;
		int tempmapsheight = 0;
		String linesdigit = "";
		try {
			while ((line = br.readLine()) != null)
			{		
				//each line corresponds to a height value
				//each number in a line corresponds to a width value
				tempmapsheight++;
				for (int i = 0; i < line.length(); i++)
				{
					//if the char is a zero or a one, add it to the string
					if (line.substring(i, i + 1).equals("0") || line.substring(i, i + 1).equals("1")) linesdigit += line.substring(i, i + 1);
					//if the char is a comma, it's time to use the number catched with the above if clause
					//anything else like spaces is ignored
					else if (line.substring(i, i + 1).equals(","))
					{
						tempmapswidth++;
						linesdigit = "";
					}
					//if anything goes wrong, it'll warn me about it!
					else
					{
						System.out.println("class CalculateCollisionfromTilegrid has found an issue, and has read the value '" + line.substring(i, i + 1) + "' from the text file");
					}
					//if the last char in the line was a number, it will also be catched and accounted for
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
		mapsheight = tempmapsheight;
		tempmapsheight = -1;
		tempmapswidth = 0;
		Collisions[][] Toreturn = new Collisions[mapswidth + 1][mapsheight + 1];
		try {
			while ((line = br2.readLine()) != null)
			{
				//each line corresponds to a height value
				//each number in a line corresponds to a width value
				tempmapsheight++;
				for (int i = 0; i < line.length(); i++)
				{
					//if the char is a zero or a one, add it to the string
					if (line.substring(i, i + 1).equals("0") || line.substring(i, i + 1).equals("1")) linesdigit += line.substring(i, i + 1);
					//if the char is a comma, it's time to use the number catched with the above if clause
					//anything else like spaces is ignored
					else if (line.substring(i, i + 1).equals(","))
					{
						if (Integer.parseInt(linesdigit) == 0) Toreturn[tempmapswidth][tempmapsheight] = new Collisions(false, false);
						else if (Integer.parseInt(linesdigit) == 1) Toreturn[tempmapswidth][tempmapsheight] = new Collisions(true, false);
						tempmapswidth++;
						linesdigit = "";
					}
					//if anything goes wrong, it'll warn me about it!
					else
					{
						System.out.println("class CalculateCollisionfromTilegrid has found an issue, and has read the value '" + line.substring(i, i + 1) + "' from the text file");
					}
					//if the last char in the line was a number, it will also be catched and accounted for
					if (i == line.length() - 1)
					{
						if (Integer.parseInt(linesdigit) == 0) Toreturn[tempmapswidth][tempmapsheight] = new Collisions(false, false);
						else if (Integer.parseInt(linesdigit) == 1) Toreturn[tempmapswidth][tempmapsheight] = new Collisions(true, false);
						else if (Integer.parseInt(linesdigit) == 2) Toreturn[tempmapswidth][tempmapsheight] = new Collisions(true, true);
						tempmapswidth++;
						linesdigit = "";
					}
				}
				linesdigit = "";
				tempmapswidth = 0;
			}
		} catch (NumberFormatException e) {
			System.out.println("class CalculateCollisionfromTilegrid went in catch");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("class CalculateCollisionfromTilegrid went in catch");
			e.printStackTrace();
		}
		return Toreturn;
	}
}
