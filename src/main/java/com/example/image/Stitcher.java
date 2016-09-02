package com.example.image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class Stitcher {

    // image_0_1.png
    // image_0_2.png
    // image_0_3.png

    // image_1_1.png
    // image_1_2.png
    // image_1_3.png

    // ....
    // ....
    // ....

    // image_10_1.png
    // image_10_2.png
    // image_10_3.png

    // [3][10]
    private static final String VERTICAL_CUT_REGEX = "([\\S]*)_([\\d]*)_([\\d]*)([\\S]*)";

    public void stitchImages(String folder, String outFile) throws IOException {
        String[][] fnames = new String[Config.NUMBER_OF_IMAGES_IN_ONE_VERTICAL_MOTION][Config.NUMBER_OF_HORIZONTAL_MOTIONs];
        Pattern patt = Pattern.compile(VERTICAL_CUT_REGEX);

        File dir = new File(folder);
        File[] listFiles = dir.listFiles();
        for (File f : listFiles) {
            String name = f.getName();
            System.out.println(name);
            Matcher m = patt.matcher(name);
            if (m.matches()) {
                Integer row = Integer.valueOf(m.group(3));
                Integer column = Integer.valueOf(m.group(2));
                if (row >= Config.NUMBER_OF_IMAGES_IN_ONE_VERTICAL_MOTION
                    || column >= Config.NUMBER_OF_HORIZONTAL_MOTIONs)
                    {
                        continue;
                    }
                
                System.out.println(row + " " + column + " " + f.getAbsolutePath());
                fnames[row][column] = f.getAbsolutePath();
            }
        }
        VerticalImagesHolder[] verticalImagesHolders = new VerticalImagesHolder[Config.NUMBER_OF_HORIZONTAL_MOTIONs];
        for (int col = 0; col < Config.NUMBER_OF_HORIZONTAL_MOTIONs; col++) {
            List<String> imagePaths = new ArrayList<String>();
            for (int row = 0; row < Config.NUMBER_OF_IMAGES_IN_ONE_VERTICAL_MOTION; row++) {
                imagePaths.add(fnames[row][col]);
            }
            verticalImagesHolders[col] = new VerticalImagesHolder(imagePaths.toArray(new String[0]));
        }
        System.out.println("Saving output file");
        AllImagesHolder allImages = new AllImagesHolder(verticalImagesHolders);
        File outputfile = new File(outFile);
        ImageIO.write(allImages.getMergedImage(), "jpg", outputfile);

    }

    public static void main(String[] args) {
        System.out.println("Starting ");
        try {
            new Stitcher().stitchImages(
                    "/Users/harshit/Desktop/softwares/eclipse/Parser/images/7",
                    "/Users/harshit/Desktop/softwares/eclipse/Parser/images/7/out1.jpg");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
