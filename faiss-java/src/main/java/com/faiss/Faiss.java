package com.faiss;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(
    target = "com.faiss.Faiss",
    value = {
        @Platform(include = {"faiss/MetricType.h", "faiss/IndexFlat.h", "faiss/utils/distances.h"}, link = {"faiss"}),
    }
)
@Namespace("faiss")
public class Faiss {
    static {
        Loader.load();
    }

    public enum MetricType {
        METRIC_INNER_PRODUCT(0), METRIC_L2(1), METRIC_L1(2), METRIC_Linf(3), METRIC_Lp(4);

        public final int value;

        MetricType(int value) {
            this.value = value;
        }
    }

    public static native void fvec_renorm_L2(int d, int nx, @Cast("float*") FloatPointer x);
}
