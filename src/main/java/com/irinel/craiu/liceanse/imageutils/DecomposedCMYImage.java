package com.irinel.craiu.liceanse.imageutils;


import java.awt.image.BufferedImage;

public class DecomposedCMYImage {

    private BufferedImage cyanImage;
    private BufferedImage mangentaImage;
    private BufferedImage yellowImage;

    public BufferedImage getCyanImage() {
        return cyanImage;
    }

    public void setCyanImage(BufferedImage cyanImage) {
        this.cyanImage = cyanImage;
    }

    public BufferedImage getMangentaImage() {
        return mangentaImage;
    }

    public void setMangentaImage(BufferedImage mangentaImage) {
        this.mangentaImage = mangentaImage;
    }

    public BufferedImage getYellowImage() {
        return yellowImage;
    }

    public void setYellowImage(BufferedImage yellowImage) {
        this.yellowImage = yellowImage;
    }
}
