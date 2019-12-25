package com.company.Miscellaneous;

public class StatisticalValue {

    public double valueSum;
    public double valueSum_Squared;

    public double valueMean;
    public double valueVariance;
    public double valueAccuracy;

    public int valueRecordNumber;

    public boolean hasMetDesiredAccuracy;

    public StatisticalValue(){
        valueAccuracy = Double.MAX_VALUE;
        hasMetDesiredAccuracy = false;

    }

    public void updateSums(double newRecord){

        valueRecordNumber++;
        valueSum += newRecord;
        valueSum_Squared += newRecord*newRecord;
    }

    public void updateValueMean(){
        valueMean = valueSum/valueRecordNumber;
    }

    public void updateValueVariance(){
        valueVariance = (valueSum_Squared/valueRecordNumber) - (valueMean*valueMean);
    }

    public void updateValueAccuracy(){
        valueAccuracy = 1.96*valueVariance/(valueMean*Math.sqrt((double) valueRecordNumber));
    }

    public boolean update(double newRecord){
        updateSums(newRecord);
        updateValueMean();
        updateValueVariance();
        updateValueAccuracy();
        hasMetDesiredAccuracy = valueAccuracy <= 0.05;
        return hasMetDesiredAccuracy;
    }


}
