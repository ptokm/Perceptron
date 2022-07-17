package perceptron;

import java.util.ArrayList;
import java.util.*;

public class Algorithm {
    private final Dataset _dataset;
    private final ArrayList <ArrayList <Double>> _patterns;
    private final ArrayList <Double> _output;
    private ArrayList <Double> _realOutput;
    private ArrayList <Double> _weights; 
    
    private final Double _learningRate; // Must 0 < learningRate < 1
    
    Algorithm() {
        this._learningRate = 0.1; 
        this._dataset = new Dataset();
        this._patterns = _dataset.getPatterns();
        this._output = _dataset.getOutput();
        
        this._weights = new ArrayList();
        this._patterns.get(0).forEach(element -> {
            _weights.add(1.0);
        });
    }
    
    public void train() {
       while(true) {
            this._realOutput = new ArrayList();
            int countSuccessPaterns = 0;
            for (int i = 0; i < this._patterns.size(); i++) {
                Double output = 0.0; //Calculate u(x)
                for (int j = 0; j < _patterns.get(i).size(); j++) {
                    output += _patterns.get(i).get(j) * _weights.get(j);
                }

                //If u(x) >= 0 then the object is orange, else is apple
                if (output >= 0 ) {
                    this._realOutput.add(1.0);
                    //If real output is different from class that we know it is,
                    //then the weights need education
                    if (this._output.get(i) != 1.0) {
                        countSuccessPaterns = 0;
                        educateWeights(i);
                    }else 
                        countSuccessPaterns++;
                }else {
                    this._realOutput.add(-1.0);  
                    //If real output is different from class that we know it is,
                    //then the weights need education
                    if (this._output.get(i) != -1.0) {
                        countSuccessPaterns = 0;
                        educateWeights(i);
                    }else 
                        countSuccessPaterns++;
                }
            } 
            
            if (countSuccessPaterns == _patterns.size()) {
                break;
            }
        }
        
        trainNewPattern();
       
    }
    
    public void educateWeights(int pattern) {
        ArrayList <Double> newWeights = new ArrayList();
        for (int i = 0; i < this._weights.size(); i++) {
          newWeights.add(this._weights.get(i) + (this._learningRate * (this._output.get(pattern)) - this._realOutput.get(pattern)) * this._patterns.get(pattern).get(i));
        }
        _weights = newWeights;     
    }
    
    public void trainNewPattern() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to train a new model? (Write 1 if you wish, and 0 if not)");
        try {
            int want = scanner.nextInt();
            switch (want) {
                case 1 -> {
                    ArrayList <Double> pattern = new ArrayList ();
                    System.out.println("I want you to give me the details of a new template");
                    try {
                        System.out.println("1st feature");
                        pattern.add(scanner.nextDouble());
                    }catch(Exception e) {
                        System.out.println("Ivalid feature, try again.");
                        trainNewPattern();
                    }   
                    try {
                        System.out.println("2nd feature");
                        pattern.add(scanner.nextDouble());
                    }catch(Exception e) {
                        System.out.println("Ivalid feature, try again.");
                        trainNewPattern();
                    }   
                    
                    pattern.add(1.0);
                    educatePattern(pattern);
                    trainNewPattern();
                }
                case 0 -> System.out.println("Thank you!");
                default -> trainNewPattern();
            }
        }catch (Exception e) {
            trainNewPattern();
        }
    }
    
    public void educatePattern(ArrayList <Double> pattern) {
        Double output = 0.0;
        for (int i=0; i<pattern.size(); i++) {
            output += pattern.get(i) * _weights.get(i);
        }
        
        if (output >= 0)
            System.out.println("It belongs to the oranges");
        else
            System.out.println("It belongs to the apples");
    }
}
