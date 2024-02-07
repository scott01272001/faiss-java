package com.faiss;

import org.bytedeco.javacpp.FloatPointer;

public class Utils {

    public static FloatPointer makeFloatArray(float[][] features) {
        int dbSize = features.length;
        int dim = features[0].length;

        float[] r = new float[dbSize * dim];
        for (int i = 0; i < dbSize; i++) {
            for (int j = 0; j < dim; j++) {
                float feature = features[i][j];
                r[dbSize * j + i] = feature;
            }
        }
        return new FloatPointer(r);
    }

    public static void enableJavaCppDebug() {
        System.setProperty("org.bytedeco.javacpp.logger.debug", "true");
    }

    public static void disableJavaCppDebug() {
        System.setProperty("org.bytedeco.javacpp.logger.debug", "false");
    }

}
