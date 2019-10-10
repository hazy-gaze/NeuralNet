/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ner;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.learning.BackPropagation;

/**
 *
 * @author Kristina Krtinic
 */
public class TrainingRunner implements Runnable {
    NeuralNetwork neuralNet;
    DataSet dataset;

    public TrainingRunner(NeuralNetwork neuralNet, DataSet dataset) {
        this.neuralNet = neuralNet;
        this.dataset = dataset;
    }

    @Override
    public void run() {
        System.out.println("Training neural net: "+neuralNet.getLabel());
        neuralNet.learn(dataset);
        
        BackPropagation bp = (BackPropagation) neuralNet.getLearningRule();
        System.out.println("Iterations: "+bp.getCurrentIteration());
        System.out.println("Standard error: "+bp.getTotalNetworkError());
        
        System.out.println("Saving neural net: "+neuralNet.getLabel());
        neuralNet.save(neuralNet.getLabel()+".nnet");
        
    }
    
    
}
