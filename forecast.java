import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.net.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
public class forecast {
	public static void main(String[] args) throws Exception {
		int month=0;
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		BufferedImage bufferedImage = ImageIO.read(new URL("https://coursera-university-assets.s3.amazonaws.com/f8/5c185343dfb525fda5abae3e7216df/emory_square_coursera.png"));
		Image newimg = bufferedImage.getScaledInstance(180, 180,java.awt.Image.SCALE_SMOOTH);
		ImageIcon icon=new ImageIcon(newimg);
		//frame.setIconImage(new ImageIcon(image).getImage());
		
		String monthstr="";
		monthstr = (String) JOptionPane.showInputDialog(null,"Enter how many years about past since 2016:\nEnter INTEGERS only. For example, enter \"4\" for 2020, since 4 years have passed.","UK Voting Forecast (Based on 1975 and 2016 Referendums)",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
		month = Integer.parseInt(monthstr);
		
		String menstr="";
		menstr = (String) JOptionPane.showInputDialog(null,"Enter the percentage of men in the population:\nEnter NUMBERS only. For example, enter \"0.75\" for 75%","UK Voting Forecast (Based on 1975 and 2016 Referendums)",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
		double men = Double.parseDouble(menstr);
		
		String ageonestr="";
		ageonestr = (String) JOptionPane.showInputDialog(null,"Enter the percentage of people aged from 18-34 in the population:\nEnter NUMBERS only. For example, enter \"0.75\" for 75%","UK Voting Forecast (Based on 1975 and 2016 Referendums)",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
		double ageone = Double.parseDouble(ageonestr);
		
		String agetwostr="";
		agetwostr = (String) JOptionPane.showInputDialog(null,"Enter the percentage of people aged from 35-54 in the population:\nEnter NUMBERS only. For example, enter \"0.75\" for 75%","UK Voting Forecast (Based on 1975 and 2016 Referendums)",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
		double agetwo = Double.parseDouble(agetwostr);
		
		String abcstr="";
		abcstr = (String) JOptionPane.showInputDialog(null,"Enter the percentage of ABC1 Income group in the population:\nEnter NUMBERS only. For example, enter \"0.75\" for 75%","UK Voting Forecast (Based on 1975 and 2016 Referendums)",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
		double abc= Double.parseDouble(abcstr);	

		int tempInt=2016+month;
		String tempStr=Integer.toString(tempInt);
		double result=getVotingResult(month,men,ageone,agetwo,abc);
		double resultfinal=(double)Math.round(result * 100d) / 100d;
		JOptionPane.showMessageDialog(frame,
				"The result of the referendum (percentage of people choosing to remain in the EU) at year " + tempStr + " is "+resultfinal+"%",
				"Summary of voting result",
				JOptionPane.INFORMATION_MESSAGE, icon);
			System.exit(0);
	}
	public static double getVotingResult (int n, double men, double ageone, double agetwo, double abc){
		int year=n;
		double changemen=(20.0/44.0)*year*men+(26.0/44.0)*year*(1-men);
		double changeage=(2.0/44.0)*year*ageone+(26.0/44.0)*year*agetwo+(38.0/44.0)*year*(1-ageone-agetwo);
		double changeabc=(19.0/44.0)*year*abc+(16.0/44.0)*year*(1-abc);
		
		return (52-(changemen+changeage+changeabc)/3.0);
		
	}
	public static double adjTotal(List<List<String>> lines, int month){
		double result=0;
		int lineNo = 1;
		String tempMonth="";
		String tempStr="";
		int intMonth=0;
		for(List<String> line: lines) {
			int columnNo = 1;
			for (String value: line) {
				if ((columnNo==1)&& (lineNo>=6)){
					tempMonth=value.substring(0,1);
					tempStr=value.substring(1,2);
				}
				//System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
				try {
					intMonth=Integer.parseInt(tempMonth);
				}catch(NumberFormatException ex){
					
				}
				if ((intMonth==month)&&(tempStr.equals("/"))){
					if ((columnNo==5) && (lineNo>=5)) {
						result+=Double.parseDouble(value);
						//System.out.println(Double.parseDouble(value));
						//System.out.println(value);
					}
				}
				columnNo++;	
			}
			lineNo++;
		}
		return result;	
	}
	
	public static double funcFees(List<List<String>> lines, int month){
		double result=0;
		int lineNo = 1;
		String tempMonth="";
		String tempStr="";
		int intMonth=0;
		for(List<String> line: lines) {
			int columnNo = 1;
			for (String value: line) {
				if ((columnNo==1)&& (lineNo>=6)){
					tempMonth=value.substring(0,1);
					tempStr=value.substring(1,2);
				}
				//System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
				try {
					intMonth=Integer.parseInt(tempMonth);
				}catch(NumberFormatException ex){
					
				}
				if ((intMonth==month)&&(tempStr.equals("/"))){
					if ((columnNo==7) && (lineNo>=5)) {
						result+=Double.parseDouble(value);
						//System.out.println(Double.parseDouble(value));
						//System.out.println(value);
					}
				}
				columnNo++;	
			}
			lineNo++;
		}
		return result;	
	}
	public static double cashBasis (List<List<String>> lines, int month){
		return (funcFees(lines,month)-adjTotal(lines,month));	
	}
}