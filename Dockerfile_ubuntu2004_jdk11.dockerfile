FROM ubuntu:20.04

ARG DEBIAN_FRONTEND=noninteractive

# Install necessary packages including OpenSSH server
RUN apt update && apt install -y \
    liblapack-dev \
    liblapack3 \
    g++ \
    make \
    openjdk-11-jdk \
    maven \
    vim \
    openssh-server

# Set up SSH
RUN mkdir /var/run/sshd
RUN echo 'root:password' | chpasswd
RUN sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config

# SSH login fix, otherwise user is kicked off after login
RUN sed -i 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' /etc/pam.d/sshd

ENV NOTVISIBLE "in users profile"
RUN echo "export VISIBLE=now" >> /etc/profile

WORKDIR /home
RUN wget https://github.com/Kitware/CMake/releases/download/v3.28.1/cmake-3.28.1-linux-x86_64.tar.gz
RUN tar -xzvf cmake-3.28.1-linux-x86_64.tar.gz
RUN ln -s /home/cmake-3.28.1-linux-x86_64/bin/cmake /usr/bin

# Expose the SSH port
EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]

# docker build -t faiss -f ./Dockerfile_ubuntu2004_jdk11.dockerfile .
# docker run -tdi -p 6667:22 -v $(pwd):/opt/ --name faiss faiss

# cmake -B build -DFAISS_ENABLE_GPU=OFF -DFAISS_ENABLE_PYTHON=OFF -DCMAKE_BUILD_TYPE=Release -DBUILD_SHARED_LIBS=ON -DBUILD_TESTING=OFF -DFAISS_ENABLE_C_API=ON .

# cmake -B build -DFAISS_ENABLE_GPU=OFF -DFAISS_ENABLE_PYTHON=OFF -DCMAKE_BUILD_TYPE=Release -DBUILD_TESTING=OFF -DFAISS_ENABLE_C_API=ON .

# make -C build install

# build example
# make -C build demo_ivfpq_indexing
# make -C build 1-Flat-org
