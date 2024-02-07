package com.faiss;

import org.bytedeco.javacpp.annotation.Cast;
import com.faiss.Faiss.MetricType;

public class IndexFlatIP extends IndexFlat {

    public IndexFlatIP(@Cast("faiss::idx_t") long d) {
        super(d, MetricType.METRIC_INNER_PRODUCT);
    }
    
}
