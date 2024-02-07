package com.faiss;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.Properties;

@Properties(inherit = com.faiss.Faiss.class)
@Namespace("faiss")
@Name("SearchParameters")
public class SearchParameters extends Pointer {
    static {
        Loader.load();
    }
}
