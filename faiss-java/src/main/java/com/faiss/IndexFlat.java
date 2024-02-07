package com.faiss;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.LongPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Cast;
import org.bytedeco.javacpp.annotation.Const;
import org.bytedeco.javacpp.annotation.MemberGetter;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Properties;

import com.faiss.Faiss.MetricType;

@Properties(inherit = com.faiss.Faiss.class)
@Namespace("faiss")
@Name("IndexFlat")
public class IndexFlat extends Pointer {
    static {
        Loader.load();
    }

    public IndexFlat(@Cast("faiss::idx_t") long d, @Cast("faiss::MetricType") MetricType metric) {
        allocate(d, metric);
    }

    private native void allocate(@Cast("faiss::idx_t") long d, @Cast("faiss::MetricType") MetricType metric);

    public native void add(@Cast("faiss::idx_t") long n, @Cast("const float*") FloatPointer x);

    public native @MemberGetter @Cast("bool") boolean is_trained();

    public native @MemberGetter @Cast("faiss::idx_t") long ntotal();

    public native void search(@Cast("faiss::idx_t") long n, @Cast("const float*") FloatPointer x,
            @Cast("faiss::idx_t") long k, FloatPointer distances, @Cast("faiss::idx_t*") LongPointer labels,
            @Const SearchParameters params);
}
