package com.faiss;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.LongPointer;

import java.util.Random;

public class CosineSimilaritySearch {

    public static void searchRandom() {
        int dim = 2048;
        int dbSize = 85000;
        int querySize = 3;

        float[][] features = new float[dbSize][];
        float[][] querys = new float[querySize][];

        Random random = new Random();
        for (int i = 0; i < dbSize; i++) {
            features[i] = new float[dim];
            for (int j = 0; j < dim; j++) {
                features[i][j] = -1 + (random.nextFloat() * 2);
            }
        }
        for (int i = 0; i < querySize; i++) {
            querys[i] = new float[dim];
            for (int j = 0; j < dim; j++) {
                querys[i][j] = -1 + (random.nextFloat() * 2);
            }
        }

        FloatPointer xb = Utils.makeFloatArray(features);
        Faiss.fvec_renorm_L2(dim, dbSize, xb);

        FloatPointer xq = Utils.makeFloatArray(querys);
        Faiss.fvec_renorm_L2(dim, querySize, xq);

        try (IndexFlatIP index = new IndexFlatIP(dim)) {
            index.add(dbSize, xb);

            xb.deallocate();

            int k = 1;
            LongPointer labels = new LongPointer(k * querySize);
            FloatPointer distances = new FloatPointer(k * querySize);

            index.search(querySize, xq, k, distances, labels, null);
        }
    }

    public static void search() {
        float[] f1 = new float[] { 0.9f, 0.9f };
        float[][] features = new float[1][];
        features[0] = f1;

        float[] q1 = new float[] { 0.25f, 1 };
        float[][] querys = new float[1][];
        querys[0] = q1;

        int d = f1.length; // dimension
        int nb = features.length; // database size
        int nq = querys.length;

        FloatPointer xb = Utils.makeFloatArray(features);
        FloatPointer xq = Utils.makeFloatArray(querys);

        Faiss.fvec_renorm_L2(d, nb, xb);
        Faiss.fvec_renorm_L2(d, nq, xq);

        IndexFlatIP index = new IndexFlatIP(d);
        System.out.println("is_trained = " + index.is_trained());
        index.add(nb, xb);
        System.out.println("ntotal = " + index.ntotal());

        int k = 1;
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
