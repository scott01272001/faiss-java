package com.faiss;

import java.util.List;

import org.bytedeco.javacpp.FloatPointer;

public class Utils {

    public static FloatPointer makeFlattenedArray(float[][] features) {
        int size = features.length;
        int dim = features[0].length;

        float[] flattenedArray = new float[size * dim];
        int currentPosition = 0;
        for (float[] array : features) {
            System.arraycopy(array, 0, flattenedArray, currentPosition, array.length);
            currentPosition += array.length;
        }
        return new FloatPointer(flattenedArray);
    }

    public static void enableJavaCppDebug() {
        System.setProperty("org.bytedeco.javacpp.logger.debug", "true");
    }

    public static void disableJavaCppDebug() {
        System.setProperty("org.bytedeco.javacpp.logger.debug", "false");
    }

}
