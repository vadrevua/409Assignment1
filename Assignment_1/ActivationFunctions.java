/*
 * Program that trains and test two types of activation functions on a dataset
 */

import java.io.*;
import java.util.*;

public class ActivationFunctions {
  
  double[][] dataArr = new double[4000][3];
  
  public static void main(String[] args) {
    
    ActivationFunctions af = new ActivationFunctions();
    af.importData();
    af.hardActivationFunction(0.75, 0.2); //2.011x + 2.002
    af.softActivationFunction(0.75, 0.2); //2.01x + 2.07
    af.hardActivationFunction(0.5, 0.2); //2.081x + 1.674
    af.softActivationFunction(0.5, 0.2); //2.08x + 1.709
    af.hardActivationFunction(0.25, 0.2); //1.979x + 0.834
    af.softActivationFunction(0.25, 0.2); //2.086x + 0.894
    
  }
  
  // Read data and add it to array
  public void importData() {
    
    try {
      
      File data = new File("C:/Users/Barack/Documents/GitHub/409Assignment1/data.txt");
      Scanner scan = new Scanner(data);
      scan.nextLine();
      scan.useDelimiter(",|\n");
      
      int i = 0;
      while(scan.hasNextLine()) {
        dataArr[i][0] = scan.nextDouble();
        dataArr[i][1] = scan.nextDouble();
        dataArr[i][2] = Integer.parseInt(scan.next().trim());
        i++;
      }
      
      scan.close();
      
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    
  }
  
  public void hardActivationFunction(double percentTraining, double alpha) {
    
    if(percentTraining >= 1.0 || percentTraining <= 0) throw new NumberFormatException("Percent must be in between 0 and 1");
    
    double[] weights = new double[]{Math.random() * 100, Math.random() * 100, Math.random() * 100}; // Assigns random values to weights
    
    for(int i = 0; i < 1000; i++) {
      double totalError = 0.0;
      for(int j = 0; j < dataArr.length * percentTraining; j++) {
        double net = 0.0;
        for(int k = 0; k < 2; k++) {
          net += (weights[k] * dataArr[j][k]);
        }
        net += weights[2];
        double output = 0.5;
        if(net > 0) output = 1.0;  // Output is always 1 or -1
        if(net < 0) output = -1.0;
        double dout = dataArr[j][2] == 0.0 ? 1.0 : -1.0;
        totalError += Math.pow(dout - output, 2.0);
        double error = dout - output;
        double learn = alpha * error;
        for(int k = 0; k < 2; k++) {
          weights[k] += (learn * dataArr[j][k]);
        }
        weights[2] += learn;
      }
      if(totalError < 0.00001) break; // Stops training once error is very low
    }
    
    System.out.println("Weights: " + weights[0] + " " + weights[1] + " " + weights[2]);
    System.out.println("y = " + (weights[0] / (-1 * weights[1])) + "x + " + (weights[2] / (-1 * weights[1])));
    
    double tp = 0;
    double tn = 0;
    double fp = 0;
    double fn = 0;
    
    for(int i = (int) (dataArr.length * percentTraining); i < 4000; i++) {
      
      if((weights[0] * dataArr[i][0]) + (weights[1] * dataArr[i][1]) + weights[2] > 0 && dataArr[i][2] == 0) tp++;
      if((weights[0] * dataArr[i][0]) + (weights[1] * dataArr[i][1]) + weights[2] < 0 && dataArr[i][2] == 1) tn++;
      if((weights[0] * dataArr[i][0]) + (weights[1] * dataArr[i][1]) + weights[2] > 0 && dataArr[i][2] == 1) fp++;
      if((weights[0] * dataArr[i][0]) + (weights[1] * dataArr[i][1]) + weights[2] < 0 && dataArr[i][2] == 0) fn++;
      
    }
    
    System.out.println("True positives = " + tp);
    System.out.println("True negatives = " + tn);
    System.out.println("False positives = " + fp);
    System.out.println("False negatives = " + fn);
    System.out.println();
    
  }
  
  public void softActivationFunction(double percentTraining, double alpha) {
    
    if(percentTraining >= 1.0 || percentTraining <= 0) throw new NumberFormatException("Percent must be in between 0 and 1");
    
    double[] weights = new double[]{Math.random() * 100, Math.random() * 100, Math.random() * 100};
    
    for(int i = 0; i < 1000; i++) {
      double totalError = 0.0;
      for(int j = 0; j < dataArr.length * percentTraining; j++) {
        double net = 0.0;
        for(int k = 0; k < 2; k++) {
          net += (weights[k] * dataArr[j][k]);
        }
        net += weights[2];
        double output = (2 / (1 + Math.exp(-2 * 0.2 * net))) - 1;  // Output can vary between 1 and -1
        double dout = dataArr[j][2] == 0.0 ? 1.0 : -1.0;
        totalError += Math.pow(dout - output, 2.0);
        double error = dout - output;
        double learn = alpha * error;
        for(int k = 0; k < 2; k++) {
          weights[k] += (learn * dataArr[j][k]);
        }
        weights[2] += learn;
      }
      if(totalError < 0.00001) break; // Stops training once error is very low
    }
    
    System.out.println("Weights: " + weights[0] + " " + weights[1] + " " + weights[2]);
    System.out.println("y = " + (weights[0] / (-1 * weights[1])) + "x + " + (weights[2] / (-1 * weights[1])));
    
    double tp = 0;
    double tn = 0;
    double fp = 0;
    double fn = 0;
    
    for(int i = (int) (dataArr.length * percentTraining); i < 4000; i++) {
      
      if((weights[0] * dataArr[i][0]) + (weights[1] * dataArr[i][1]) + weights[2] > 0 && dataArr[i][2] == 0) tp++;
      if((weights[0] * dataArr[i][0]) + (weights[1] * dataArr[i][1]) + weights[2] < 0 && dataArr[i][2] == 1) tn++;
      if((weights[0] * dataArr[i][0]) + (weights[1] * dataArr[i][1]) + weights[2] > 0 && dataArr[i][2] == 1) fp++;
      if((weights[0] * dataArr[i][0]) + (weights[1] * dataArr[i][1]) + weights[2] < 0 && dataArr[i][2] == 0) fn++;
      
    }
    
    System.out.println("True positives = " + tp);
    System.out.println("True negatives = " + tn);
    System.out.println("False positives = " + fp);
    System.out.println("False negatives = " + fn);
    System.out.println();
    
  }
  
}