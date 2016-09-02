package com.example.image;

import java.awt.image.BufferedImage;

import boofcv.io.UtilIO;
import boofcv.io.image.UtilImageIO;

public class VerticalImagesHolder {

    private String[]        imagePaths = new String[Config.NUMBER_OF_IMAGES_IN_ONE_VERTICAL_MOTION];
    private BufferedImage[] images     = new BufferedImage[Config.NUMBER_OF_IMAGES_IN_ONE_VERTICAL_MOTION];
    private BufferedImage   mergedImage;

    public VerticalImagesHolder(String[] imagePaths) {
        if (imagePaths == null || imagePaths.length != Config.NUMBER_OF_IMAGES_IN_ONE_VERTICAL_MOTION) {
            throw new IllegalArgumentException(
                    "Number of images expected was " + Config.NUMBER_OF_IMAGES_IN_ONE_VERTICAL_MOTION);
        }
        this.imagePaths = imagePaths;
        this.initializeAndStitch();
    }

    private void initializeAndStitch() {
        int indx = 0;
        for (String path : imagePaths) {
            images[indx++] = UtilImageIO.loadImage(UtilIO.pathExample(path));
        }

        if (images.length == 1) {
            this.mergedImage = images[0];
            return;
        }

        BufferedImage tempOutput = null;
        for (indx = 1; indx < images.length; indx++) {
            if (indx == 1) {
                tempOutput = StitchingProcessor.stitch(images[0], images[1]);
            }
            else {
                tempOutput = StitchingProcessor.stitch(tempOutput, images[indx]);
            }
        }
        this.mergedImage = tempOutput;
    }

    public BufferedImage[] getImages() {
        return images;
    }

    public void setImages(BufferedImage[] images) {
        this.images = images;
    }

    public BufferedImage getMergedImage() {
        return mergedImage;
    }
}
