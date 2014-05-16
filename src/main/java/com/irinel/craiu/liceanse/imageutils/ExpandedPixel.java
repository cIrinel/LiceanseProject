package com.irinel.craiu.liceanse.imageutils;


import java.util.Arrays;

public class ExpandedPixel {
    private int[][] firstShareExpandedPixel;
    private int[][] secondShareExpandedPixel;

    public int[][] getFirstShareExpandedPixel() {
        return firstShareExpandedPixel;
    }

    public void setFirstShareExpandedPixel(int[][] firstShareExpandedPixel) {
        this.firstShareExpandedPixel = firstShareExpandedPixel;
    }

    public int[][] getSecondShareExpandedPixel() {
        return secondShareExpandedPixel;
    }

    @Override
    public String toString() {
        return "ExpandedPixel{" +
                "firstShareExpandedPixel=" + Arrays.deepToString(firstShareExpandedPixel) +
                ", secondShareExpandedPixel=" + Arrays.deepToString(secondShareExpandedPixel) +
                '}';
    }

    public void setSecondShareExpandedPixel(int[][] secondShareExpandedPixel) {
        this.secondShareExpandedPixel = secondShareExpandedPixel;
    }
}
