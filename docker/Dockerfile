FROM fedora:23

# Install Yocto host deps
# Based on http://www.yoctoproject.org/docs/current/yocto-project-qs/yocto-project-qs.html
# and missing/spurious build deps reported by bitbake
RUN dnf install --assumeyes make wget tar bzip2 python2 unzip perl patch \
    diffstat git cpp gcc gcc-c++ glibc-devel texinfo chrpath ccache \
    perl-Data-Dumper perl-Text-ParseWords perl-Thread-Queue perl-bignum socat \
    findutils which SDL-devel xterm cpio zlib-devel glibc.i686

# Add user for the build, sharing local user IDs
RUN groupadd -g 1000 yocto
RUN useradd -g 1000 -u 1000 yocto

# Create directory for mount point
RUN mkdir /yocto

#CMD ["/bin/bash", "-e", "su", "yocto"]
CMD ["/bin/bash"]
