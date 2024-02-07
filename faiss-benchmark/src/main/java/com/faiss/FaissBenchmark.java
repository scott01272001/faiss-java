package com.faiss;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bytedeco.javacpp.PointerScope;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.PointerPointer;
import org.openjdk.jmh.annotations.Benchmark;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
@Warmup(time = 1, iterations = 1)
public class FaissBenchmark {
    private static final int dim = 512;
    private static final int dbSize = 100000;
    private static final int querySize = 1;

    private float[][] features = new float[dbSize][];
    private float[][] querys = new float[querySize][];

    @Setup
    public void setup() {
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
    }

    @Benchmark
    @Measurement(time = 20, iterations = 3)
    public void searchInCosineSimilarity() {
        try (PointerScope scope = new PointerScope()) {
            FloatPointer xb = VectorHelper.makeFloatArray(new PointerPointer<>(features), dbSize, dim);
            Faiss.fvec_renorm_L2(dim, dbSize, xb);

            FloatPointer xq = VectorHelper.makeFloatArray(new PointerPointer<>(querys), querySize, dim);
            Faiss.fvec_renorm_L2(dim, querySize, xq);

            try (IndexFlatIP index = new IndexFlatIP(dim)) {
                index.add(dbSize, xb);

                int k = 1;
                LongPointer labels = new LongPointer(k * querySize);
                FloatPointer distances = new FloatPointer(k * querySize);

                index.search(querySize, xq, k, distances, labels, null);
            }
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(FaissBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
