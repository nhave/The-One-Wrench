package com.nhave.tow.utils;

public class ColorUtils
{
	public static int[] colorCodes = new int[] {1644825, 16711680, 65280, 6704179, 255, 11685080, 5013401, 10066329, 6710886, 15892389, 8388371, 15059968, 6730495, 15027416, 16757299, 16777215};
	public static String[] colorNames = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "lightGray", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
	public static String[] oreDict = new String[] {"dyeBlack", "dyeRed",  "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
	
	public static int black = colorCodes[0];
	public static int red = colorCodes[1];
	public static int green = colorCodes[2];
	public static int brown = colorCodes[3];
	public static int blue = colorCodes[4];
	public static int purple = colorCodes[5];
	public static int cyan = colorCodes[6];
	public static int lightGray = colorCodes[7];
	public static int gray= colorCodes[8];
	public static int pink = colorCodes[9];
	public static int lime = colorCodes[10];
	public static int yellow = colorCodes[11];
	public static int lightBlue = colorCodes[12];
	public static int magenta = colorCodes[13];
	public static int orange = colorCodes[14];
	public static int white = colorCodes[15];
	
	public static int RGBtoINT(int r, int g, int b)
	{
		return (256 * 256 * r) + (256 * g) + b;
	}
	
	public static int HEXtoINT(String color)
	{
		if (color.startsWith("#")) color.replaceFirst("#", "");
		if (color == null || color.length() != 6) return 0;

		int r = Integer.parseInt(String.valueOf(color.charAt(0)) + String.valueOf(color.charAt(1)), 16);
		int g = Integer.parseInt(String.valueOf(color.charAt(2)) + String.valueOf(color.charAt(3)), 16);
		int b = Integer.parseInt(String.valueOf(color.charAt(4)) + String.valueOf(color.charAt(5)), 16);
		
		return RGBtoINT(r, g, b);
	}
	
	public static String INTtoHEX(int color)
	{
		return String.format("#%06X", (0xFFFFFF & color));
	}
	
	public static String RGBtoHEX(int r, int g, int b)
	{
		return INTtoHEX(RGBtoINT(r, g, b));
	}
	
	public static int[] HEXtoRGB (String color)
	{
		if (color.startsWith("#")) color.replaceFirst("#", "");
		if (color == null || color.length() != 6) return new int[] {0, 0, 0};

		int r = Integer.parseInt(String.valueOf(color.charAt(0)) + String.valueOf(color.charAt(1)), 16);
		int g = Integer.parseInt(String.valueOf(color.charAt(2)) + String.valueOf(color.charAt(3)), 16);
		int b = Integer.parseInt(String.valueOf(color.charAt(4)) + String.valueOf(color.charAt(5)), 16);
		
		return new int[] {r, g, b};
	}
	
	public static int[] INTtoRGB (int color)
	{
		return HEXtoRGB(INTtoHEX(color));
	}
}