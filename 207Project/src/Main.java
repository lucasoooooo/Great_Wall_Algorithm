import java.util.ArrayList;

/**
 * 207 Traveling Salesman Project.
 * 11/14/2019
 * Great Wall Algorithm
 * 
 * 
 * 
 * @author Alex
 * @author Lucas
 * 
 */

//Create Junit Tests

public class Main {

	public static void main(String[] args) {
		String[] cityNames = {"Rockville","Silver Spring","Philadelphia","PittsBurgh","Baltimore","Cleveland","New York City"};
		
		final int [][]CITIES_DISTANCES = {					//In order:
				// 0    1    2    3    4    5    6
				{  0,  13, 142, 225,  40, 352, 227},		//0			Rockville,
				{ 13,   0, 136, 237,  34, 363, 222},		//1			Silver Spring,
				{141, 135,   0, 305, 101, 432,  97},		//2			Philadelphia,
				{226, 237, 304,   0, 248, 133, 371},		//3			Pittsburgh,
				{ 40,  34, 106, 248,   0, 374, 192},		//4			Baltimore,
				{352, 364, 431, 133, 375,   0, 462},		//5			Cleveland,
				{228, 222,  97, 370, 188, 462,   0},		//6			New York City
				};
		
		
		
		
		int[] list = {0,1,2,3,4,5,6};
		//printCities(pairedCities(list, CITIES_DISTANCES), cityNames);
		
		ArrayList<Integer> cityPath = new ArrayList<Integer>();
		connectCityPath(pairedCities(list, CITIES_DISTANCES), CITIES_DISTANCES);
		//System.out.println(cityPath);
	}
	
	
	
	private static ArrayList<Integer> pairedCities(int[] city, int[][] cityD) {
		ArrayList<Integer> pair = new ArrayList<Integer>();
		//ArrayList<String> citiesPaired = new ArrayList<String>();
		
		
		int shortest, shortestIdx;
		for(int i = 0; i < city.length; i++) {
			if(city[i] == -1) {
				continue;
			}
			
			shortest = cityD[city[i]][0];
			shortestIdx = i;
			
			
			if(cityD[city[i]][0] == 0) {
				shortest = cityD[city[i]][1];
				shortestIdx = 1;
			}
			
			for(int j = 1; j < city.length; j++) {
				if(city[j] == -1) {
					continue;
				}
				if(cityD[i][j] < shortest && cityD[i][j] != 0) {
					shortest = cityD[city[i]][j];
					shortestIdx = j;
					
				}
				
			}
			
			pair.add(i);
			if(i != shortestIdx)
				pair.add(shortestIdx);
			
			
			System.out.println(city[i] +" is paired with " +shortestIdx);
			city[i] = -1;
			city[shortestIdx] = -1;
			
		}
		
		System.out.println(pair);
		System.out.println();
		
		//citiesPaired = printCities(pair);
		
		return pair;
	}
	
	public static void printCities(ArrayList<Integer> pList, String[] cNames){
		
		for(int i = 0; i < pList.size(); i++) {
			if(i%2 == 0) {
				if(pList.size()-1 == i) {
					System.out.println(cNames[pList.get(i)] +" is a lone pair");
				}
				else {
					System.out.print(cNames[pList.get(i)] +" is paried with ");
				}
			}
			else {
				System.out.println(cNames[pList.get(i)]);
			}
		}
		
	}
	
	/**
	 * Comparing distances of pairs, finding smallest, and save into array
	 * @param pair
	 * @param cityDistance
	 * @return
	 */
	public static ArrayList<Integer> connectCityPath(ArrayList<Integer> pair, int[][] cityDistance){
	
		ArrayList<Integer> returnPath = new ArrayList<Integer>();
		int size = pair.size();
		boolean hasLonePair =false;
		boolean lonePairConnected = false;
		ArrayList<Pairs> pairsArray = new ArrayList<Pairs>();
		Pairs smallest;
		int currentCity,smallestInd;
		
		//Checks if there is a lone pair
		if (pair.size()%2!=0) {
			size --;								//prevent loop from cycling into the lone pair
			hasLonePair = true;						//Keep track that there is a lone pair
		}
		
		
		//Pair fPair = new Pair()
		for(int i=0; i < pair.size(); i+=2) {
			//pair.add(i);
			if(pair.size()-1 == i) {
				pairsArray.add(new Pairs(pair.get(i), -1, 0));				//Adding lone pair
			}
			else {
				Pairs newPair = new Pairs(pair.get(i), pair.get(i+1), getCityDistance(cityDistance, pair.get(i), pair.get(i+1)));
				pairsArray.add(newPair);
			}
		}
		
		
		
		System.out.println("Pairs: "+pair);
		System.out.println("pairsArray:"+pairsArray);
		
		//Adds first pair directly to the return array
		returnPath.add(pairsArray.get(0).getCityA());
		returnPath.add(pairsArray.get(0).getCityB());
		pairsArray.remove(0);
		
		int pairSize = pairsArray.size();
		//Runs for the length of the pairs array
		for(int i=0; i<pairSize;i++) {
			//Adds first pairs
			
			//sets standard small element to compare later ones to
			smallest=pairsArray.get(0);
			smallestInd=0;
			
//		System.out.println("smallest distance: "+smallest.getCityDistance());
			//Cycles through the current pair array ( constantly removing from this array)
			for (int j=1;j<pairsArray.size();j++) {
				if(pairsArray.get(j).getCityDistance()<smallest.getCityDistance()) {
					smallest=pairsArray.get(i); //keeps track of smallest pair object
					smallestInd=j; //keeps track of smallest pair object index
				}
			}
			currentCity = returnPath.get(returnPath.size()-1); //The last element from the return array
//			System.out.println("ReturnPath:"+returnPath);
//			System.out.println("PairsArray: "+pairsArray);
//			
			
			//Test the distances with the lone pair's distance to last city added to the return array
			if (hasLonePair) {
				//Checks if lone pair distance's is shorter than either city in the pair 
				//(this is the pair with the smallest distance between the 2 cities inside the pair)
				if(cityDistance[smallest.getCityA()][currentCity]   >    cityDistance[pair.get(pair.size()-1)][currentCity]
						||
						cityDistance[smallest.getCityB()][currentCity]   >    cityDistance[pair.get(pair.size()-1)][currentCity]) {
					//Adds to the array
					returnPath.add(pairsArray.get(pairsArray.size()-1).getCityA());
					// removes from the pairsArray so its not checked again	
					pairsArray.remove(pairsArray.size()-1);
//					System.out.println("Adding lone pair with distance of "+cityDistance[pair.get(pair.size()-1)][currentCity]);
//					System.out.println(pair.get(pair.size()-1));
//					System.out.println(currentCity);
					hasLonePair=false;
				}
			}
			
			//Ignore lone pair since it has already been added, Focuses on next two shortest distances
			else {
				returnPath.add(smallest.getCityA());
				returnPath.add(smallest.getCityB());
			//	System.out.println("Adding : "+smallest.getCityA()+" and "+smallest.getCityB());
				pairsArray.remove(smallestInd);
			}
				
			
		}
		System.out.println("ReturnPath:"+returnPath);
//		if (returnPath.size() < 7) {
//			returnPath.add(
//		}
		System.out.println(returnPath);
		
		return returnPath;			//Return array of sorted points.
	}
	
	//Checks again if it is really the smallest distance (because 0 1 4, and now we are on i=2(which should be city3 and city5); however city2 and city6 should have smaller distance, so we need to adjust it.
	private static boolean determineInArray(ArrayList<Integer> returnPath, ArrayList<Pairs> pairsArr, int idx) {
		boolean isInArr = false;
		int cityA = pairsArr.get(idx).getCityA();
		int cityB = pairsArr.get(idx).getCityB();
		
		for(int i = 0; i < returnPath.size(); i++) {
			if(cityA == returnPath.get(i) || cityB == returnPath.get(i)) {
				isInArr = true;
				break;
			}
		}
		
		//System.out.println("smallestDistance method: " +smallest);
		return isInArr;
	}
	
	
	
	
	
	
	public static int getCityDistance(int[][] citryDistance, int cityA, int cityB) {
		return citryDistance[cityA][cityB];
	}
	
	
	/**
	 * Calculate the total time in hours spent on the road
	 * @param distance total road distance
	 * @return total time in hours spent on the road
	 */
	public static double hoursOnRoad(double distance){
		return distance *60;
	}
	
	/**
	 * Calculate the total cost of fuel
	 * @param distance total road distance
	 * @return total cost of fuel
	 */
	public static double fuelCost(double distance){
		 return (distance/7.0) * 3.32;
	}
	
	/**git
	 * Calculate truck driver’s salary
	 * @param distance total road distance
	 * @return truck driver’s salary
	 */
	public static double driversSalary(double distance){
		return (distance * 0.56) + 1200;
	}
	
	/**
	 * Calculate helper's salary
	 * @param distance total road distance
	 * @return helper's salary
	 */
	public static double helpersSalary(double distance){
		return (distance * 0.42) + 900;
	}
	
	/**
	 * Calculate the total cost of the hotel
	 * @param numCities number of cities a single delivery truck has to supply to the supermarkets
	 * @return total cost of the hotel
	 */
	public static double hotelCost(int numCities){
		return 100.0 * 2 * numCities;
	}
	
	/**
	 * Calculate the total cost of meal
	 * @param numCities number of cities a single delivery truck has to supply to the supermarkets
	 * @return total cost of meal
	 */
	public static double mealCost(int numCities){
		return 68.0 * 2 * numCities;
	}
	
	/**
	 * Calculate the cost of maintenance
	 * @param distance total road distance
	 * @return cost of maintenance
	 */
	public static double maintenanceCost(double distance){
		return 0.88 * distance;
	}
	
	/**
	 * Calculate the total cost of operating a single delivery truck on a single supply chain mission 
	 * for supplying to the supermarket stores across the seven cities.
	 * @param fuel the total cost of fuel
	 * @param dSalary truck driver’s salary
	 * @param hSalary helper's salary
	 * @param hotel total cost of the hotel
	 * @param meal total cost of meal
	 * @param maintenance cost of maintenance
	 * @return total cost of operating a single delivery truck on a single supply chain mission
	 */
	public static double totalCostTruck(double fuel, double dSalary, double hSalary, double hotel, double meal, double maintenance) {
		return fuel + dSalary + hSalary + hotel + meal + maintenance;
	}
	
	public static int totalDistance(int[][] array) {
		
		int totalDistance = 0;
		
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				totalDistance += array[i][j];
			}
		}
		
		return totalDistance;
	}
	
	public static String cityNames() {
		return null;
	}
}
