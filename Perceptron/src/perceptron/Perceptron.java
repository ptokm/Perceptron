package perceptron;

/**
 *
 * @author paraskevi_tokmakidou
 */
public class Perceptron {

    public static void main(String[] args) {
        Dataset dataset = new Dataset();
        dataset.loadSpecificDataset("fruits.txt");
        
        Algorithm algorithm = new Algorithm();
        algorithm.train();
    }
    
}
