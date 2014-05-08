package com.irinel.craiu.liceanse.shareGeneration;


import java.awt.image.BufferedImage;

public class EncryptedImage {
    BufferedImage firstShare;
    BufferedImage secondShare;

    public BufferedImage getFirstShare() {
        return firstShare;
    }

    public void setFirstShare(BufferedImage firstShare) {
        this.firstShare = firstShare;
    }

    public BufferedImage getSecondShare() {
        return secondShare;
    }

    public void setSecondShare(BufferedImage secondShare) {
        this.secondShare = secondShare;
    }
}
