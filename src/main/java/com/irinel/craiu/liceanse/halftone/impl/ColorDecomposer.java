package com.irinel.craiu.liceanse.halftone.impl;


import com.irinel.craiu.liceanse.imageutils.DecomposedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.image.BufferedImage;

public class ColorDecomposer {
    private static final Logger LOG = LoggerFactory.getLogger(ColorDecomposer.class);

    public DecomposedImage decomposeImageToPrimaryColors(BufferedImage bufferedImage) {
        LOG.info("Attempting to decompose input image into \"colorscale\"");
        checkNotNull(bufferedImage, "Input image buffer is null");


        return new DecomposedImage();
    }


}
