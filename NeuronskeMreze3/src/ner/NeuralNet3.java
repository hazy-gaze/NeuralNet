/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ner;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;

/**
 *
 * @author Kristina Krtinic
 */
public class NeuralNet3 {
    
    String dataSetFile = "balance_scale_data.txt";
    
    int inputCount = 20;
    int outputCount = 3;
    
    Thread[] trainingThreads;
    
    
    public static void main(String[] args) {
        (new NeuralNet3()).run();
    }

    private void run() {
        
        DataSet dataset = DataSet.createFromFile(dataSetFile, inputCount, outputCount, "\t");
        
        trainingThreads = new Thread[10];
        for(int i=0; i<trainingThreads.length; i++) {
            
            MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(inputCount, 10,outputCount);
            neuralNet.setLabel("NeuralNetwork_"+i);
            
            trainingThreads[i] = new Thread(new TrainingRunner(neuralNet, dataset));
            trainingThreads[i].start();
        }
        
    }
    
}
