#!/bin/bash

# Some very basic sanity check
if [ -f "README.md" ] && [ -d "recipes-aws" ] && [ -d "../../layers" ]
then
    # patches
    cd ../meta-freescale
    patch -p1 < ../meta-pasta-demo/patches/meta-freescale/0001-pasta-demo-support-wayland-weston-vulkan-etc.patch
    patch -p1 < ../meta-pasta-demo/patches/meta-freescale/0002-pasta-demo-add-gobject-introspection-for-gst-plugins.patch
    cd ../meta-toradex-nxp
    patch -p1 < ../meta-pasta-demo/patches/meta-toradex-nxp/0001-pasta-demo-support-wayland-weston.patch
    patch -p1 < ../meta-pasta-demo/patches/meta-toradex-nxp/0002-pasta-demo-use-older-devicetree-name.patch
    cd ../meta-toradex-torizon
    patch -p1 < ../meta-pasta-demo/patches/meta-toradex-torizon/0001-pasta-demo-changes-to-support-wayland-weston.patch
    cd ../openembedded-core
    patch -p1 < ../meta-pasta-demo/patches/openembedded-core/0001-pasta-demo-use-Gstreamer-1.14.4-bindings.patch
    
    # local.conf
    cd ../meta-pasta-demo
    cp conf/local.conf.example ../../build-torizon/conf/local.conf
    echo 'Remember to accept NXP EULA by un-commeting ACCEPT_FSL_EULA="1" from conf/local.conf'

    # extra layers
    cd ../ # go to layers directory
    git clone https://github.com/bachp/meta-homeassistant.git

    # bblayers.conf
    cd ../build-torizon
    sed -i '$d' ./conf/bblayers.conf
    echo '  ${OEROOT}/layers/meta-pasta-demo \' >> ./conf/bblayers.conf
    echo '  ${OEROOT}/layers/meta-homeassistant \' >> ./conf/bblayers.conf
    echo '"' >> ./conf/bblayers.conf
else
    echo "Please run this script from the meta-pasta-demo root directory!"
fi