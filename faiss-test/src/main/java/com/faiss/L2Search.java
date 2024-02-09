package com.faiss;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.LongPointer;

public class L2Search {

    public static void search() {
        float[] f1 = new float[] {13.0f, 96.0f};
        float[] f2 = new float[] {83f, 22f};
        float[] f3 = new float[] {0.5f, 1};
        float[][] features = new float[3][];
        features[0] = f1;
        features[1] = f2;
        features[2] = f3;

        float[] q1 = new float[] {1.0f, 1.0f};
        float[][] querys = new float[1][];
        querys[0] = q1;

        int d = f1.length; // dimension
        int nb = features.length; // database size
        int nq = querys.length;

        FloatPointer xb = Utils.makeFlattenedArray(features);
        FloatPointer xq = Utils.makeFlattenedArray(querys);

        IndexFlatL2 index = new IndexFlatL2(d);
        System.out.println("is_trained = " + index.is_trained());
        index.add(nb, xb);
        System.out.println("ntotal = " + index.ntotal());

        int k = 3;
        LongPointer labels = new LongPointer(k * nq);
        FloatPointer distances = new FloatPointer(k * nq);

        index.search(nq, xq, k, distances, labels, null);

        for (int i = 0; i < nq; i++) {
            System.out.println("Query " + i);
            System.out.print("Labels: ");
            for (int j = 0; j < k; j++) {
                System.out.print(labels.get(i * k + j) + " ");
            }
            System.out.println();
            System.out.println("Distance: ");

            float[] results = new float[k * nq];
            distances.get(results);
            for (int j = 0; j < results.length; j++) {
                System.err.print(results[j] + " ");
            }
            System.out.println();
        }

        index.close();
    }

}
