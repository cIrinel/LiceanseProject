package com.irinel.craiu.liceanse.imageutils;


import java.util.Arrays;

public class DitheredImageDifference {
    int[][] redScaleDifferences;
    int[][] greenScaleDifferences;
    int[][] blueScaleDifferences;

    public DitheredImageDifference(int[][] redScaleDifferences, int[][] greenScaleDifferences, int[][] blueScaleDifferences) {
        this.redScaleDifferences = redScaleDifferences;
        this.greenScaleDifferences = greenScaleDifferences;
        this.blueScaleDifferences = blueScaleDifferences;
    }



    public int[][] getRedScaleDifferences() {
        return redScaleDifferences;
    }

    public void setRedScaleDifferences(int[][] redScaleDifferences) {
        this.redScaleDifferences = redScaleDifferences;
    }

    public int[][] getGreenScaleDifferences() {
        return greenScaleDifferences;
    }

    public void setGreenScaleDifferences(int[][] greenScaleDifferences) {
        this.greenScaleDifferences = greenScaleDifferences;
    }

    public int[][] getBlueScaleDifferences() {
        return blueScaleDifferences;
    }

    public void setBlueScaleDifferences(int[][] blueScaleDifferences) {
        this.blueScaleDifferences = blueScaleDifferences;
    }

    @Override
    public String toString() {
        return "DitheredImageDifference{" +
                "redScaleDifferences=" + Arrays.toString(redScaleDifferences) +
                ", greenScaleDifferences=" + Arrays.toString(greenScaleDifferences) +
                ", blueScaleDifferences=" + Arrays.toString(blueScaleDifferences) +
                '}';
    }

}
