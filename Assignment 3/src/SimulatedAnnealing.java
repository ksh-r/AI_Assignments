import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulatedAnnealing {

    // Calculate the acceptance probability
    public static double acceptanceProbability(int energy, int newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br  = new BufferedReader(new BufferedReader(new FileReader("D:/TravellingSalesmanProblem/Data/rajasthan.txt")));
        String line = "";
        // reading when each line has x, y
        List<Integer> x = new ArrayList<Integer>(); 
        List<Integer> y = new ArrayList<Integer>(); 
        List<String> c = new ArrayList<String>(); 
        while((line = br.readLine())!=null){
             String[] t = line.split(" ");
            x.add(Integer.parseInt(t[1].trim()));
            y.add(Integer.parseInt(t[2].trim())); 
            c.add(t[0].trim());
        } 

        for (int i=0; i<x.size(); i++){
            TourManager.addCity(new City(c.get(i), x.get(i), y.get(i)));
        }
        
        br.close();

        // Set initial temp
        double temp = 10000;

        // Cooling rate
        double coolingRate = 0.003;

        // Initialize intial solution
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();
        System.out.println();
        System.out.println("Initial solution distance: " + currentSolution.getDistance());
        System.out.println();
        System.out.println("Initial solution Tour: " + currentSolution);
        System.out.println();
        System.out.println("************************************************************************************************************************************************************************");
        System.out.println();
        // Set as current best
        Tour best = new Tour(currentSolution.getTour());
        
        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            Tour newSolution = new Tour(currentSolution.getTour());

            // Get a random positions in the tour
            int tourPos1 = (int) (newSolution.tourSize() * Math.random());
            int tourPos2 = (int) (newSolution.tourSize() * Math.random());

            // Get the cities at selected positions in the tour
            City citySwap1 = newSolution.getCity(tourPos1);
            City citySwap2 = newSolution.getCity(tourPos2);

            // Swap them
            newSolution.setCity(tourPos2, citySwap1);
            newSolution.setCity(tourPos1, citySwap2);
            
            // Get energy of solutions
            int currentEnergy = currentSolution.getDistance();
            int neighbourEnergy = newSolution.getDistance();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = new Tour(newSolution.getTour());
            }

            // Keep track of the best solution found
            if (currentSolution.getDistance() < best.getDistance()) {
                best = new Tour(currentSolution.getTour());
            }
            
            // Cool system
            temp *= 1-coolingRate;
        }

        System.out.println("Final solution distance: " + best.getDistance());
        System.out.println();
        System.out.println("Tour: " + best);
    }
}