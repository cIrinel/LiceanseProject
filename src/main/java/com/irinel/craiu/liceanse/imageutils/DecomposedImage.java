package com.irinel.craiu.liceanse.imageutils;


import java.awt.image.BufferedImage;

public class DecomposedImage {

    private BufferedImage imageInPrimaryColor1;
    private BufferedImage imageInPrimaryColor2;
    private BufferedImage imageInPrimaryColor3;

    public BufferedImage getImageInPrimaryColor1() {
        return imageInPrimaryColor1;
    }

    public void setImageInPrimaryColor1(BufferedImage imageInPrimaryColor1) {
        this.imageInPrimaryColor1 = imageInPrimaryColor1;
    }

    public BufferedImage getImageInPrimaryColor2() {
        return imageInPrimaryColor2;
    }

    public void setImageInPrimaryColor2(BufferedImage imageInPrimaryColor2) {
        this.imageInPrimaryColor2 = imageInPrimaryColor2;
    }

    public BufferedImage getImageInPrimaryColor3() {
        return imageInPrimaryColor3;
    }

    public void setImageInPrimaryColor3(BufferedImage imageInPrimaryColor3) {
        this.imageInPrimaryColor3 = imageInPrimaryColor3;
    }
}
