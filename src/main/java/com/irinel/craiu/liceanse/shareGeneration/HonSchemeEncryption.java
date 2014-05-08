package com.irinel.craiu.liceanse.shareGeneration;


import com.irinel.craiu.liceanse.imageutils.DecomposedCMYImage;
import com.irinel.craiu.liceanse.imageutils.EncryptedImage;
import com.irinel.craiu.liceanse.imageutils.ExpandedPixel;
import com.irinel.craiu.liceanse.imageutils.PixelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class HonSchemeEncryption {

    Logger LOG = LoggerFactory.getLogger(HonSchemeEncryption.class);

    public EncryptedImage getEncryptedImage(DecomposedCMYImage decomposedImage) {

        return new EncryptedImage();
    }


    public ExpandedPixel getExpandedPixel(int pixel, int colorChannel) {
        ExpandedPixel expandedPixel = new ExpandedPixel();
        int color = PixelConverter.getChanellColorFromInt(pixel, colorChannel);
        int[][] expandedPixelAux = new int[2][2];

        expandedPixelAux[0][0] = 0;
        expandedPixelAux[0][1] = 255;
        expandedPixelAux[1][0] = 255;
        expandedPixelAux[1][1] = 0;

        if (color == 0) {
            expandedPixel.setFirstShareExpandedPixel(expandedPixelAux);
            expandedPixel.setSecondShareExpandedPixel(getReversedPixel());
        } else if (color == 255) {
            expandedPixel.setFirstShareExpandedPixel(expandedPixelAux);
            expandedPixel.setSecondShareExpandedPixel(expandedPixelAux);
        } else {
            LOG.error(String.format("Something went wrong, found pixel with color Channel value = %s", colorChannel));
        }
        return expandedPixel;
    }

    public int[][] getReversedPixel() {
        int[][] reversedPixel = {{255, 0}, {0, 255}};
        return reversedPixel;
    }


}
