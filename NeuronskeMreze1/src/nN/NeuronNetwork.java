/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nN;

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
public class NeuronNetwork implements LearningEventListener {
    
    String dataSetFile ="lenses_data.txt";
    
    int inputCount = 9;
    int outputCount = 3;
    
    int minHiddenNeurons = 10;
    int maxHiddenNeurons = 20;
    
    double minLearningRate = 0.1;
    double maxLearningRate = 0.9;
    
    int trainingCount=0;
    
    List<Training> trainings = new ArrayList<>();
    Training minIterationsTraining;
    Training minErrorTraining;
    
    
    public static void main(String[] args) {
        (new NeuronNetwork()).run();
    }
    
    
    private void run() {
        DataSet dataset = DataSet.createFromFile(dataSetFile, inputCount, outputCount, " ");
        
        for(int hiddenNeurons = minHiddenNeurons; hiddenNeurons<maxHiddenNeurons; hiddenNeurons++) {
            for(double learningRate = minLearningRate; learningRate<maxLearningRate; learningRate+=0.1) {
                
                MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(inputCount, hiddenNeurons, outputCount);
                BackPropagation bp = neuralNet.getLearningRule();
                bp.setLearningRate(learningRate);
                bp.setMaxError(0.01);
                bp.setMaxIterations(20000);
                
                bp.addListener(this);
                
                trainingCount++;
                System.out.println("---------------------------------------------");
                System.out.println("Training: "+trainingCount);
                System.out.println("HiddenNeurons: "+hiddenNeurons+" LearningRate: "+learningRate);
                
                neuralNet.learn(dataset);
                
                int iterations = bp.getCurrentIteration();
                double error = bp.getTotalNetworkError();
                
                Training t = new Training(neuralNet, dataset, iterations, hiddenNeurons, learningRate, error);
                trainings.add(t);
            }
        }
        analyzeTrainings();
        
        System.out.println("Testiranje sa minIterationTraining:");
        testNeuralNetwork(minIterationsTraining.getNeuralNet(),minIterationsTraining.getDataset());
        System.out.println("Testiranje sa minErrorTraining:");
        testNeuralNetwork(minErrorTraining.getNeuralNet(), minErrorTraining.getDataset());
        
    }

    @Override
    public void handleLearningEvent(LearningEvent le) {
        BackPropagation bp = (BackPropagation) le.getSource();
        if(le instanceof LearningStoppedEvent) {
            System.out.println("");
            System.out.println("Training done in: "+bp.getCurrentIteration()+" iterations.");
            System.out.println("With standard error: "+bp.getTotalNetworkError());
        
        } else {
            System.out.println(bp.getCurrentIteration()+". iteration | Total error: "+bp.getTotalNetworkError());
        }
        
    }

    private void analyzeTrainings() {
        
        double avgError;
        double avgIterations;
        double sumError =0;
        double sumIterations=0;
        
        minIterationsTraining = trainings.get(0);
        minErrorTraining = trainings.get(0);
        
        for(Training t: trainings) {
            if(t.getIterations()<minIterationsTraining.getIterations()) {
                minIterationsTraining = t;
            }
            if(t.getError()<minErrorTraining.getError()) {
                minErrorTraining = t;
            }
        
            sumError+=t.getError();
            sumIterations += t.getIterations();
            
            
        }
         avgIterations = sumIterations/trainings.size();
            avgError = sumError/trainings.size();
            
            System.out.println("Average iterations: "+avgIterations);
            System.out.println("Averge error: "+avgError);
    }

    private void testNeuralNetwork(NeuralNetwork neuralNet, DataSet dataset) {
       for(DataSetRow dsr : dataset.getRows()) {
           neuralNet.setInput(dsr.getInput());
           neuralNet.calculate();
           
           double[] netOutput = neuralNet.getOutput();
           
           System.out.println("Input: "+Arrays.toString(dsr.getInput())+" Output: "+Arrays.toString(netOutput));
       
       }
    }

    
}
