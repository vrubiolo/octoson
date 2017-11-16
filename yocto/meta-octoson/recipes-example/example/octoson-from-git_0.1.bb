#
# This file was derived from the 'Hello World!' example recipe in the
# Yocto Project Development Manual.
#

DESCRIPTION = "Octoprint on the Intel Edison"
SECTION = "maker"
LICENSE = "GPL"

SRC_URI = "git://github.com/foosel/OctoPrint.git"

S = "${WORKDIR}"
