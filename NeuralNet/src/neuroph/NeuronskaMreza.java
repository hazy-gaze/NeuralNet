/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuroph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.events.LearningStoppedEvent;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;

/**
 *
 * @author Kristina Krtinic
 */
public class NeuronskaMreza implements LearningEventListener {
    
    String dataSetFile = "lenses_data.txt";
    
    int inputCount = 9;
    int outputCount = 3;
    
    int minHiddenNeurons = 10;
    int maxHiddenNeurons = 20;
    
    double minLearningRate = 0.1;
    double maxLearningRate = 0.9;
    
    int trainingCount = 1;
    
    List<Training> trainings = new ArrayList<>();
    Training minIterationTraining;
    Training minErrorTrainig;
    
    public static void main(String[] args) {
        (new NeuronskaMreza()).run();
    }

    private void run() {
        
        DataSet dataSet = DataSet.createFromFile(dataSetFile, inputCount, outputCount, " ");
        
        for(int hiddenNeurons = minHiddenNeurons; hiddenNeurons<=maxHiddenNeurons; hiddenNeurons++) {
            for(double learningRate = minLearningRate; learningRate<=maxLearningRate; learningRate=learningRate+0.1) {
                
                MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(inputCount,hiddenNeurons,outputCount);
                
                BackPropagation bp = neuralNet.getLearningRule();
                bp.setLearningRate(learningRate);
                bp.setMaxError(0.01);
                bp.setMaxIterations(20000);
                
                bp.addListener(this);
                
                trainingCount++;
                System.out.println("--------------------------------------------------------");
                System.out.println("Training: "+trainingCount);
                System.out.println("HiddenNeurons: "+hiddenNeurons+", LearningRate: "+learningRate+"\n");
                
                neuralNet.learn(dataSet);
                
                int iterations = bp.getCurrentIteration();
                double totalError = bp.getTotalNetworkError();
                
                Training t = new Training(neuralNet, dataSet, iterations, totalError, hiddenNeurons, learningRate);
                trainings.add(t);
                
            }
        }
        analyzeTrainings();
        System.out.println("Testiranje sa minIterationTraining:");
        testNeuralNetwork(minIterationTraining.getNeuralNet(),minIterationTraining.getDataset());
        
    }

    @Override
    public void handleLearningEvent(LearningEvent le) {
        BackPropagation bp = (BackPropagation) le.getSource();
        if(le instanceof LearningStoppedEvent) {
            System.out.println("");
            System.out.println("Training completed in: "+bp.getCurrentIteration()+" iterations.");
            System.out.println("With total error: "+bp.getTotalNetworkError()+"\n");
        } else {
            System.out.println(bp.getCurrentIteration()+". iteration | Total error: "+bp.getTotalNetworkError());
        }
    }

    private void analyzeTrainings() {
        
        double avgError;
        double avgIteration;
        double sumError = 0;
        double sumIteration = 0;
        
        minIterationTraining = trainings.get(0);
        minErrorTrainig = trainings.get(0);
        
        for(Training t: trainings) {
            if(t.getIterations()<minIterationTraining.getIterations()) {
                minIterationTraining = t;
            }
            if(t.getTotalError()<minErrorTrainig.getTotalError()) {
                minErrorTrainig = t;
            }
            
            sumError = sumError + t.getTotalError();
            sumIteration = sumIteration + t.getIterations();
            
        }
        
        avgError = sumError / trainings.size();
        avgIteration = sumIteration/trainings.size();
        
        System.out.println("\nAverage error: "+avgError);
        System.out.println("Average iterations: "+avgIteration);

        
    }

    private void testNeuralNetwork(NeuralNetwork neuralNet, DataSet dataset) {
        for(DataSetRow datasetRow: dataset.getRows()) {
            neuralNet.setInput(datasetRow.getInput());
            neuralNet.calculate();
            
            double[] output = neuralNet.getOutput();
            
            System.out.println("Input: "+Arrays.toString(datasetRow.getInput()));
            System.out.println("Output: "+Arrays.toString(output));
        
        }
    }
    
}
