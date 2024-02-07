# Introduction
This project is a Java API for Faiss, bridged from the Faiss C++ library using JavaCPP.

Ref: 
- faiss: https://github.com/facebookresearch/faiss
- javacpp: https://github.com/bytedeco/javacpp

# Motivation
Currently, Faiss provides API only for C++ and Python, with no offical plans to develop a Java interface. Therefore, I'm attempting to build it myself,
utilizing the capabilities of JavaCPP to enable Java to leverage the advantages of Faiss.

# Requirement
- OS: Linux
- liblapack3 installed (faiss depend on libblas)
- Java 11

# Supported faiss version
1.7.4

# Note
Only support CPU version faiss now

# Currently bridged API
- IndexFlatL2
- IndexFlatIP
- fvec_renorm_L2 (used for cosine similarity search)

# Build
mvn clean package

