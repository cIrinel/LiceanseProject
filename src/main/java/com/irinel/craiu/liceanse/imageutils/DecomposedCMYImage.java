package com.irinel.craiu.liceanse.imageutils;


import java.awt.image.BufferedImage;

public class DecomposedCMYImage {

    private BufferedImage cyanScaleImage;
    private BufferedImage mangentaScaleImage;
    private BufferedImage yellowScaleImage;

    public BufferedImage getCyanScaleImage() {
        return cyanScaleImage;
    }

    public void setCyanScaleImage(BufferedImage cyanScaleImage) {
        this.cyanScaleImage = cyanScaleImage;
    }

    public BufferedImage getMangentaScaleImage() {
        return mangentaScaleImage;
    }

    public void setMangentaScaleImage(BufferedImage mangentaScaleImage) {
        this.mangentaScaleImage = mangentaScaleImage;
    }

    public BufferedImage getYellowScaleImage() {
        return yellowScaleImage;
    }

    public void setYellowScaleImage(BufferedImage yellowScaleImage) {
        this.yellowScaleImage = yellowScaleImage;
    }
}
