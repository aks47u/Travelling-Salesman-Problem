package TSP_GA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

// Create a tour and evolve a solution
public class TSP_GA {
	private static ArrayList<String> towns;
	private static double[][] coords;
	private static int size;

	public static void main(String[] args) throws Exception {
		// loadFile(new File("Towns.csv"));
		// loadFile(new File("Tuesday2013.csv"));
		// loadFile(new File("Friday2013.csv"));
		// loadFile(new File("Tuesday2014.csv"));
		loadFile(new File("Friday2014.csv"));

		// Convert input to correct format
		arrayListToDouble();

		// Declare Infinity because it's awesome
		final double INFINITY = Double.POSITIVE_INFINITY;

		// Add input into initial Tour
		for (int i = 0; i < size; i++) {
			City city = new City((int) coords[i][0], coords[i][1], coords[i][2]);
			TourManager.addCity(city);
		}

		// Initialise population(s)
		int popSize = size / 10;
		Population[] popArray = new Population[popSize];

		for (int i = 0; i < popSize; i++) {
			popArray[i] = new Population(size, true);
			popArray[i] = GA.evolvePopulation(popArray[i]);
		}

		// For use in finding shortest route
		double sh, shortest = INFINITY;
		double[] dist = new double[popSize];
		int pos = 0;

		// Evolve population for X generations
		for (int i = 0; true; i++) {
			sh = INFINITY;

			for (int j = 0; j < popSize; j++) {
				popArray[j] = GA.evolvePopulation(popArray[j]);
				dist[j] = popArray[j].getFittest().getDistance();

				if (dist[j] < sh) {
					sh = dist[j];
					pos = j;
				}
			}

			// If new shortest route is found, print and update
			if (sh < shortest) {
				shortest = sh;
				printShortest(i, shortest, popArray[pos]);
			}

			// Print a generation count to show program is still running
			if (i % 5000 == 0) {
				System.out
				.println(new StringBuilder("Generation:\t").append(i));
			}
		}
	}

	// Read in input file
	private static void loadFile(File aFile) throws Exception {
		BufferedReader input = new BufferedReader(new FileReader(aFile));
		String line = null;
		towns = new ArrayList<String>();

		while ((line = input.readLine()) != null) {
			towns.add(line);
		}

		input.close();
		size = towns.size();
	}

	// Convert input to usable format
	public static void arrayListToDouble() {
		String[][] temp = new String[size][4];
		coords = new double[size][3];

		for (int i = 0; i < size; i++) {
			temp[i] = towns.get(i).split(",");
			coords[i][0] = Double.parseDouble(temp[i][0]);
			coords[i][1] = Double.parseDouble(temp[i][2]);
			coords[i][2] = Double.parseDouble(temp[i][3]);
		}
	}

	// Custom print method
	public static void printShortest(int gen, double dist, Population pop) {
		System.out.println(new StringBuilder("Generation:\t").append(gen));
		System.out.println(new StringBuilder("Distance:\t").append(dist));
		System.out.println(new StringBuilder("Route:\t\t").append(
				pop.getFittest()).append("\n"));
	}
}
