package perceptron;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Dataset {
    private static  ArrayList <ArrayList <Double>> _patterns;
    private static ArrayList <Double> _output;

    /**
     * @return the patterns
     */
    public ArrayList <ArrayList <Double>> getPatterns() {
        return Dataset._patterns;
    }

    /**
     * @param patterns the patterns to set
     */
    public void setPatterns(ArrayList <ArrayList <Double>> patterns) {
        Dataset._patterns = patterns;
    }
    
    public void loadSpecificDataset(String filename) {
        boolean isValid = true;
        ArrayList <ArrayList <Double>> patterns = new ArrayList<>();
        ArrayList <Double> output = new ArrayList<>();
        int dimension = -1;
        try {
            FileReader file = new FileReader(filename);
            try (Scanner in = new Scanner(file)) {
                int i = 0;
                //Read the file line-by-line
                while(in.hasNextLine())  {
                    if (isValid) {
                        String line=in.nextLine();
                        if (line.startsWith("#"))
                            continue;

                        ArrayList <Double> newPattern = new ArrayList<>();
                        String[] characteristics = line.split(",");
                        if (i == 0) {
                            dimension = characteristics.length - 1;
                            for (int it = 0; it < characteristics.length - 1; it++) {
                                newPattern.add(Double.parseDouble(characteristics[it].trim()));
                            }
                            newPattern.add(1.0); // For bias
                            patterns.add(newPattern);
                            output.add(Double.parseDouble(characteristics[characteristics.length -1].trim()));
                        }else if ((characteristics.length - 1) == dimension) {
                            for (int it = 0; it < characteristics.length - 1; it++) {
                                newPattern.add(Double.parseDouble(characteristics[it].trim()));
                            }
                            newPattern.add(1.0); // For bias
                            patterns.add(newPattern);
                             output.add(Double.parseDouble(characteristics[characteristics.length -1].trim()));
                        }else {
                            isValid = false;
                        }
                    }
                }

                if (isValid) {
                    this.setPatterns(patterns);
                    this.setOutput(output);
                } else {
                   System.out.println("Something went wrong with patterns");
                }
            }
        }catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println("Something went wrong with file");
        }
    }

    /**
     * @return the output
     */
    public ArrayList <Double> getOutput() {
        return _output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(ArrayList <Double> output) {
        Dataset._output = output;
    }
}
