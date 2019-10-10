/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neur;

import java.util.Arrays;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.events.LearningStoppedEvent;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TrainingSetImport;

/**
 *
 * @author Kristina Krtinic
 */
public class NeuralNet2 implements LearningEventListener {
    
    String dataSetFile = "wine_classification_data.txt";
    
    int inputCount = 13;
    int outputCount = 3;
            
    int hiddenNeurons = 10;
    
    int trainSetSize[] = {50,60,70,80};
    
    public static void main(String[] args) {
        (new NeuralNet2()).run();
    }
     

    private void run() {
        
        DataSet dataset = DataSet.createFromFile(dataSetFile, inputCount, outputCount, "\t");
        
        for(int i=0; i<trainSetSize.length; i++) {
            DataSet[] trainAndTestData = dataset.sample(trainSetSize[i]);
            DataSet trainSet = trainAndTestData[0];
            DataSet testSet = trainAndTestData[1];
            
            MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(inputCount, hiddenNeurons, outputCount);
            BackPropagation bp = neuralNet.getLearningRule();
            bp.setMaxError(0.01);
            bp.setMaxIterations(20000);
            
            bp.addListener(this);
            System.out.println("Training: "+i+" TrainSet = "+trainSetSize[i]+"%.");
            
            neuralNet.learn(trainSet);
            
            testNeuralNetwork(neuralNet, testSet);

        }
    }
    
    @Override
    public void handleLearningEvent(LearningEvent le) {
        BackPropagation bp = (BackPropagation) le.getSource();
        if(le instanceof LearningStoppedEvent) {
            System.out.println("");
            System.out.println("Training done in: "+bp.getCurrentIteration()+" iterations.");
            System.out.println("With standard error: "+bp.getTotalNetworkError());
        
        } else {
            System.out.println(bp.getCurrentIteration()+". iteration | Total net error: "+bp.getTotalNetworkError());
        
        }
    }

    private void testNeuralNetwork(MultiLayerPerceptron neuralNet, DataSet testSet) {
        for(DataSetRow dsr: testSet.getRows()) {
            neuralNet.setInput(dsr.getInput());
            neuralNet.calculate();
            
            double[] netOutput = neuralNet.getOutput();
            System.out.println("Input: "+Arrays.toString(dsr.getInput())+" Output: "+Arrays.toString(netOutput));
        
        }
    }

    
    
}
