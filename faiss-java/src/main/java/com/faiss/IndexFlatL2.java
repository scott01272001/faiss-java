package com.faiss;

import org.bytedeco.javacpp.annotation.Cast;
import com.faiss.Faiss.MetricType;

public class IndexFlatL2 extends IndexFlat {

    public IndexFlatL2(@Cast("faiss::idx_t") long d) {
        super(d, MetricType.METRIC_L2);
    }

}
