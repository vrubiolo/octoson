# octoson - an introduction
octoson = OctoPrint + Intel Edison
<add links to images>

The Intel Edison using the mini-breakout board has a convenient form factor 
which makes it very suitable for integration in the body of a 3D printer and
run OctoPrint to control it. 

This project is about doing such a build, both hardware and software for a 
Printrbot Play.

# Status
As of 2017-01-03, the software side using the manual install is working, as is
the Edison powered via a separate wall-wart via a soldered barrel jack. No
enclosure is available.

# Hardware
The end goal is to have the Edison connected to the Play serial port using USB
host, with a 3D printed case held to the printer chassis using magnets. Power-
wise, we will investigate both solutions of having the Edison using a dedicated
power supply or the printer one.

## Enclosure
We will use [this Thingiverse design](http://www.thingiverse.com/thing:578459)
as the base for our case. We will need to remix it to make space of the barrel
jack and the USB host adapter.

## Power requirements
It is mandatory to use the external power to have USB host working, one cannot 
power the Edison via USB.
I am currently using a barrel jack soldered to the Edison mini breakout board 
to power the Edison module (took it from an Arduino board I had). 

The other solution is to reuse the Play power supply, by plugging into the 
[hotend connector](http://www.printrbottalk.com/forum/viewtopic.php?f=16&t=11204)
 but I still need to assess if the normal Play power brick has enough room for
it (I am not using a heated bed but also do not want to switch to a bulky ATX
power supply).

## Enabling USB host
Currently, I am using a standard phone [USB host adapter](http://www.newegg.com/Product/Product.aspx?Item=9SIABH952C6796&cm_re=usb_host_adapter-_-9SIABH952C6796-_-Product) which I
chose for its compactness. The next step is to build a sleeker adapter by 
[rewiring USB cables](http://makezine.com/projects/usb-otg-cable/).

# Software
There are two ways to do it here: either manually install OctoPrint on the 
Edison normal Linux image or create an image from scratch using Yocto.

## Manual install
Follow these steps to perform the installation:
1. Flash Edison official image, downloaded from the [Intel website](https://software.intel.com/iot/hardware/edison/downloads). I am using iot-devkit-prof-dev-image-edison-20160606-patch.zip.
1. configure_edison --setup
1. useradd -d /home/octoson -m -U -G dialout octoson
1. git clone https://github.com/foosel/octoprint octoprint.git
1. python setup.py install
1. create /lib/systemd/system/octoprint.service
1. systemctl enable octoprint && systemctl start octoprint
1. connect to 192.168.1.165:5000 and follow OctoPrint setup wizard
1. systemctl disable ofono|clloader|wyliodrin|etc to remove unused services from
boot sequence and system memory.
1. add octoson.sudo to /etc/sudoers.d so that the OctoPrint UI can perform 
administative tasks like restarting the OctoPrint server or the system itself.

## Using Yocto to rebuild an image
This is the cleaner solution, so that the image can be tailored to OctoPrint, 
with the unwanted parts removed but is is currently stuck on several issues:
1. The Edison Yocto image is old and based on the Yocto daisy branch. This
means it is not possible to use the latest version of certain packages because
they depend on a newer version of the poky repository. An example of this is the
existing [meta-maker](http://git.yoctoproject.org/cgit/cgit.cgi/meta-maker/about/)
layer which depends on [meta-openembedded](http://cgit.openembedded.org/meta-openembedded/).
Even using the daisy branch of meta-openembedded and meta-maker leads to errors,
cf [my question on openembedded-devel](http://lists.openembedded.org/pipermail/openembedded-devel/2016-December/110576.html) about the problem.
2. The Edison Yocto image does not appear to be maintained anymore. Some links
in the recipes archives have changed and now break. Some fixes need to be
[manually applied](https://communities.intel.com/thread/109249) to the Bitbake
recipes as a result.

# Issues
Software Yocto side currently stuck on the versioning issue (see above).

# Credits
+ [logxen github gist](https://gist.github.com/logxen/ad195ccd31914bab8869) for
the first draft of the manual installation instructions and showing me it was
possible to do.
+ the [meta-maker](http://git.yoctoproject.org/cgit/cgit.cgi/meta-maker/about/)
Yocto maintainers for the nice packaging bits and making all the hard integration
work.

# TODOs
+ Add reference to barrel jack part
+ Add link to official Edison Yocto tarball
+ Add pictures 
+ Detail exact commands to disable system services and/or create script
+ Add more links to various sources.
+ Make Yocto image build work.
+ More testing, esp w.r.t bootup time and init order (it currently seems that
if the Edison boots with the printer plugged in, it can misdetect it).
