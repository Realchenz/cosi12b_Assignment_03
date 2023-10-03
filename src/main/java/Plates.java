import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Plates.java - a class for generating and manipulating license plates
 * please refer to the README.md for more information
 */

@Data
public class Plates {
	private final String fileName = "plates.txt";
	private final String[] plates = readPlatesFromFile(fileName);

//	Given a string representing a serial format and an integer corresponding to the month of vehicle
//	expiration, return a randomly generated plate adhering to the serial format.	
	public static String createRandomPlate(String serial, int month) {
		char[] characters = serial.toCharArray();
		for (int i = 0; i < characters.length - 1; i++) {
			if (Character.isLetter(characters[i])) {
				characters[i] = (char) ((int) (Math.random() * 26) + 'A'); //randomly generate a letter
			}
			if (Character.isDigit(characters[i])) {
				if(i == 0){
					characters[i] = (char) ((int) (Math.random() * 9) + '1'); //randomly generate a digit between 1 and 9
				}
				else{
					characters[i] = (char) ((int) (Math.random() * 10) + '0'); //randomly generate a digit between 0 and 9
				}
			}
		}
		characters[characters.length - 1] = (char) (month + '0'); //the last character is the month
		return new String(characters); //return the plate
	}

//	Given a string representing a license plate,
//	return a new string representing the incremented, next plate in the series.	
	public static String nextPlate(String plate) {
		char[] characters = plate.toCharArray();
		for (int i = characters.length - 1; i >= 0; i--) {
			if (Character.isDigit(characters[i])) {
				if (characters[i] == '9') { //if the digit is 9, carry to 0
					characters[i] = '0';
				} else {
					characters[i] = (char) ((int) characters[i] + 1);
					break; //if the digit is not 9, increment and break
				}
			} else if (Character.isLetter(characters[i])) {
				if (characters[i] == 'Z') {
					characters[i] = 'A'; //if the letter is Z, carry to A
				} else {
					characters[i] = (char) ((int) characters[i] + 1);
					break; //if the letter is not Z, increment and break
				}
			}
		}
		if(characters[0] == '0'){
			return "error";
		}else {
			return new String(characters);
		}
	}
	
//	Given a plate string, return a string corresponding to the serial format of that plate
	public static String getSerial(String plate) {
		char[] characters = plate.toCharArray();
		int indexDigit = 1;
		char indexLetter = 'A';
		for (int i = 0; i < characters.length; i++) { //replace digits with 1,2,3...
			if (Character.isDigit(characters[i])) {
				characters[i] = (char) (indexDigit + '0');
				indexDigit++;
			} else if (Character.isLetter(characters[i])) { //replace letters with A,B,C...
				characters[i] = indexLetter;
				indexLetter++;
			}
		}
        return new String(characters);
    }
	
//	Given a plate string, return a boolean value denoting whether the plate is a legal vanity plate.

	public static boolean isLegalVanityPlate(String plate) {
		int length = plate.length();
		if(length > 6 || length < 2){ //length should be between 2 and 6
			return false;
		}
		char[] characters = plate.toCharArray();
		if(!Character.isUpperCase(characters[0]) || !Character.isUpperCase(characters[1])){ //first two characters should be uppercase letters
			return false;
		}
		int indexDigit = 0;
		for (int i = 2; i < characters.length; i++) {
			if (Character.isDigit(characters[i])) {
				indexDigit = i;
				break;
			}
		}
		// TODO: 10/1/23 check if there is a letter after the first digit
		if(indexDigit != 0){
			for (int i = indexDigit; i < characters.length; i++) {
				if (Character.isLetter(characters[i])) {
					return false; //if there is a letter after the first digit, return false
				}
			}
            return characters[indexDigit] != '0'; //if the first digit is 0, return false
		}
		return true; //if there is no digit, return true
	}
	
//	Given an array of plate strings, return an array of floats corresponding to the
//	frequency of each expiration month.
	public static float[] getMonthStats(String[] plate) {
		int[] months = new int[10]; //count the number of each month
        for (String s : plate) {
            int month = s.charAt(s.length() - 1) - '0';
            months[month]++;
        }
		float[] result = new float[10]; //calculate the frequency
		for (int i = 0; i < result.length; i++) {
			result[i] = (float) months[i] / plate.length;
		}
		return result;
	}
	
//	Given an array of plate strings and an array of serial format strings, return an array of floats 
//	corresponding to the frequency of each serial format.
	public static float[] getSerialStats(String[] plate, String[] serials) {
		int[] serialsCount = new int[serials.length]; //count the number of each serial format
        for (String s : plate) { //get the serial format of each plate
            for (int j = 0; j < serials.length; j++) {
                if (getSerial(s).equals(serials[j])) { //if the serial format matches, count
                    serialsCount[j]++;
                    break;
                }
            }
        }
		float[] result = new float[serials.length]; //calculate the frequency
		for (int i = 0; i < result.length; i++) {
			result[i] = (float) serialsCount[i] / plate.length;
		}
		return result;
	}

//	Given a partial string and an array of plate strings, return an array of strings corresponding
//	to the plates that match. 	
	public static String[] matchPlate(String partial, String[] plates) {
		String[] partials = partial.split("-");
		List<String> result = new ArrayList<>();
		boolean ifMatch = true;
		for (String s : plates) {
			String temp = s;
			for(String p : partials){
                if (!temp.contains(p)) {
                    ifMatch = false; //if any partial string does not match, break
                    break;
                }else {
					temp = temp.replace(p, "**"); //replace the partial string with "**"
				}

			}
			if(ifMatch){
				result.add(s); //if all partial strings match, add to result
			}
			ifMatch = true;//reset
		}
		return result.toArray(new String[0]);
	}

//	Utility method for reading from plates.txt
	public static String[] readPlatesFromFile(String fileName) {
		String currentDirectory = System.getProperty("user.dir");
		String filePath = currentDirectory + "/src/main/resources/" + fileName;
		List<String> plates = new ArrayList<>();
		try {
			FileReader reader = new FileReader(filePath);
			BufferedReader br = new BufferedReader(reader);

			String line;
            while ((line = br.readLine()) != null) {
            	plates.add(line);
            }
            reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return plates.toArray(new String[0]);
	}

}
