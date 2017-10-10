FROM fedora:23

# Install Yocto host deps necessary for Pyro
# Based on http://www.yoctoproject.org/docs/current/yocto-project-qs/yocto-project-qs.html
# and missing/spurious build deps reported by bitbake
RUN dnf install --assumeyes gawk make wget tar bzip2 gzip python3 unzip perl patch \
    diffutils diffstat git cpp gcc gcc-c++ glibc-devel texinfo chrpath \
    ccache perl-Data-Dumper perl-Text-ParseWords perl-Thread-Queue perl-bignum socat \
    python3-pexpect findutils which file cpio python python3-pip xz which SDL-devel xterm \
# Those were found via the bitbake errors
    hostname iproute procps iputils

# Add user for the build, sharing local user IDs
RUN groupadd -g 1000 yocto
RUN useradd -g 1000 -u 1000 yocto

# Create directory for mount point
RUN mkdir /yocto

USER yocto
# Workaround for https://bugzilla.yoctoproject.org/show_bug.cgi?id=10165 based on
# https://github.com/fedora-cloud/docker-brew-fedora/issues/14
ENV LANG en_US.UTF-8
CMD ["/bin/bash"]
