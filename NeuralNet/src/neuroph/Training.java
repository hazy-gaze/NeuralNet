/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuroph;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.learning.LearningRule;

/**
 *
 * @author Kristina Krtinic
 */
public class Training {
    NeuralNetwork neuralNet;
    DataSet dataset;
    int iterations;
    double totalError;
    int hiddenNeurons;
    double learningRate;

    public Training(NeuralNetwork neuralNet, DataSet dataset, int iterations, double totalError, int hiddenNeurons, double learningRate) {
        this.neuralNet = neuralNet;
        this.dataset = dataset;
        this.iterations = iterations;
        this.totalError = totalError;
        this.hiddenNeurons = hiddenNeurons;
        this.learningRate = learningRate;
    }

    public NeuralNetwork getNeuralNet() {
        return neuralNet;
    }

    public void setNeuralNet(NeuralNetwork neuralNet) {
        this.neuralNet = neuralNet;
    }

    public DataSet getDataset() {
        return dataset;
    }

    public void setDataset(DataSet dataset) {
        this.dataset = dataset;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double getTotalError() {
        return totalError;
    }

    public void setTotalError(double totalError) {
        this.totalError = totalError;
    }

    public int getHiddenNeurons() {
        return hiddenNeurons;
    }

    public void setHiddenNeurons(int hiddenNeurons) {
        this.hiddenNeurons = hiddenNeurons;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
    
    
    
    
}
