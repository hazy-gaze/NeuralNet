/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nN;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;

/**
 *
 * @author Kristina Krtinic
 */
public class Training {
    NeuralNetwork neuralNet;
    DataSet dataset;
    int iterations;
    int hiddenNeurons;
    double learningRate;
    double error;

    public Training(NeuralNetwork neuralNet, DataSet dataset, int iterations, int hiddenNeurons, double learningRate, double error) {
        this.neuralNet = neuralNet;
        this.dataset = dataset;
        this.iterations = iterations;
        this.hiddenNeurons = hiddenNeurons;
        this.learningRate = learningRate;
        this.error = error;
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

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
    
    
    
}
