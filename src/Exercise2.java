import java.util.Scanner;
import java.io.*;
import java.net.*;

/**
 * @author joshfram
 */
public class Exercise2 { 
	private String userInput;
	private String artistID;
	private String latestAlbum;
	private boolean albumHit;
	private boolean queryHit = false;

	public Exercise2() {
		System.out.println("Please enter an artist name:");	//prompt user
		Scanner scanInput = new Scanner(System.in);
		this.userInput = scanInput.nextLine();
		scanInput.close();	//close Scanner
		this.execute();
	}

	public static void main(String[] args) { 
		new Exercise2();	//create new Exercise2 object. 
	}

	/**
	 * This method is the control method for the program
	 * methods called declare object variables, which are used 
	 * in this method to return the correct result to the user
	 */
	private void execute() {
		this.parseInput();	
		if(!this.queryHit) {
			System.out.println("Artist Not Found.");
			return;
		}
		this.albumSearch();
		if(!this.albumHit) {
			System.out.println("Artist has no albums on file.");
		}
		else { 
			System.out.println(this.latestAlbum);
		}
	}

	/**
	 * This method takes the user input, and processes it so it can 
	 * be used in the search URL. It then generates the search URL 
	 * and calls the spotifySearch() method, with the base input as 
	 * an input parameter. 
	 */
	private void parseInput() {
		String searchQuery = this.userInput.replaceAll(" ", "+");
		String baseURL = "https://api.spotify.com/v1/search?q=" + 
				searchQuery + "&type=artist";
		spotifySearch(baseURL); 
	}

	/**
	 * @param baseURL the URL to be sent to search for the matching artist ID
	 * this method sends a request to the spotify servers, and parses the 
	 * result to get the artistID
	 */
	private boolean spotifySearch(String baseURL) {
		try {
			URL url = new URL(baseURL);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String line;

			//parse JSON
			while ((line = reader.readLine()) != null) {
				if(line.contains("\"id\" :")) { 
					String [] parsedResult = line.split("\"");	//split on "
					this.artistID = parsedResult[3];	//3rd index has ID
					this.queryHit  = true;	//request returned a valid artist
					return queryHit;
				}
			}
			reader.close();		//close BufferedReader

		} catch (MalformedURLException e) {
			System.out.println("Sorry, there was an error in "
					+ "processing your request.");
		} catch (IOException e) {
			System.out.println("Sorry, there was an error in "
					+ "processing your request.");
		}
		return queryHit; 
	}

	/**
	 * this method uses the previously declared artistID to find the 
	 * artist, and then parses the result of searching for that artist to 
	 * get the most recent album
	 */
	private boolean albumSearch() {
		try {
			String baseURL = "https://api.spotify.com/v1/artists/" + 
					this.artistID + "/albums?album_type=album";
			URL url = new URL(baseURL);	//send URL to server
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String line;

			//parse JSON
			while ((line = reader.readLine()) != null) {
				if(line.contains("\"name\" :")) { 
					String [] parsedResult = line.split("\"");	//split on "
					this.latestAlbum = parsedResult[3];	//3rd index has album
					this.albumHit = true;	// request returned a valid album
					return albumHit;
				}
			}
			reader.close();	//close BufferedReader

		} catch (MalformedURLException e) {
			System.out.println("Sorry, there was an error in "
					+ "processing your request.");
		} catch (IOException e) {
			System.out.println("Sorry, there was an error in "
					+ "processing your request.");
		}
		return albumHit;

	}
}