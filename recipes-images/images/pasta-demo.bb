SUMMARY = "AWS and NXP AI at the Edge Pasta Detection Demo"
DESCRIPTION = "Collaborative demo with Amazon and NXP to plug the pasta demo \
to AWS services such as AWS IoT Greengrass and AWS SageMaker Neo. Built on top \
of TorizonCore with Docker runtime."

require recipes-images/images/torizon-core-container.inc

# Ideally we remove those when the project is finished
# For now it's kept as it may help people evaluating things and also
# the Apalis iMX8 has plenty of storage
CORE_IMAGE_DEV_PKGS = " \
    packagegroup-core-buildessential \
    packagegroup-core-sdk \
    git \
    cmake \
    weston-examples \
    gtk+3-demo \
    clutter-1.0-examples \
    kmscube \
"

CORE_IMAGE_WAYLAND_WESTON = " \
    wayland \
    wayland-protocols \
    weston \
    weston-xwayland \
    weston-init \
"

# Packages related to video capture and processing
CORE_IMAGE_VIDEO_PKGS = " \
    kernel-module-v4l2loopback \
    v4l2loopback-utils \
    v4l2loopback-examples \
    v4l-utils \
    media-ctl \
    \
    libatomic \
    python3-pycairo \
    \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-base \
    \
    gstreamer1.0-plugins-base-tcp \
    gstreamer1.0-plugins-base-pango \
    gstreamer1.0-plugins-base-videorate \
    gstreamer1.0-plugins-base-videoscale \
    gstreamer1.0-plugins-base-videoconvert \
    gstreamer1.0-plugins-good-jpeg \
    gstreamer1.0-plugins-good-isomp4 \
    gstreamer1.0-plugins-good-multifile \
    gstreamer1.0-plugins-good-video4linux2 \
    gstreamer1.0-plugins-bad \
    \
    gstreamer1.0-python \
"

CORE_IMAGE_ELECTRON_PKGS = " \
    libxcomposite \
    nss \
    libxscrnsaver \
    libxi \
    libxtst \
    libxrandr \
    at-spi2-atk \
    cups \
    gconf \
"

# AWS Greengrass packages
# Add "aws-iot-greengrass-core-software-device-credentials" if you want
# to deploy with device credentials already
CORE_IMAGE_AWS_GREENGRASS= " \
    aws-iot-greengrass-core-software \
    aws-iot-greengrass-core-software-init \
    aws-iot-greengrass-core-software-certificate \
    greengrass-dependency-checker \
    credentials-setup \
    system-status \
    system-control \
    local-ui \
"

# We need those for running AWS Greengrass SDK on the host
CORE_IMAGE_PYTHON_PKGS = " \
    python-modules python3-modules \
    python-pip python3-pip \
    python-psutil python3-psutil \
    python-flask python3-flask \
    python-flask-restful python3-flask-restful \
    python-flask-jsonpify python3-flask-jsonpify \
    python-greengrasssdk python3-greengrasssdk \
    python3-pillow \
    python-imaging \
    python3-imageio \
    python3-jsonschema \
    python3-importlib-metadata \
    python3-boto3 \
"

# We need those for running AWS Greengrass SDK on the host
CORE_IMAGE_NODEJS_PKGS = " \
    nodejs nodejs-npm \
"
# Alternative to the Python Wheel provided by Sagemaker is to build from source
# sagemaker-neo-ai-dlr
# python3-sagemaker-neo-ai-dlr

# Add "aktualizr-pasta-demo" if you want OTA
CORE_IMAGE_OTA = ""
#CORE_IMAGE_OTA = " \
#    aktualizr-pasta-demo
#"

CORE_IMAGE_BASE_INSTALL += " \
    docker \
    python3-docker-compose \
    ${CORE_IMAGE_ELECTRON_PKGS} \
    ${CORE_IMAGE_DEV_PKGS} \
    ${CORE_IMAGE_PYTHON_PKGS} \
    ${CORE_IMAGE_NODEJS_PKGS} \
    ${CORE_IMAGE_AWS_GREENGRASS} \
    ${CORE_IMAGE_VIDEO_PKGS} \
    ${CORE_IMAGE_WAYLAND_WESTON} \
    ${CORE_IMAGE_OTA} \
"

EXTRA_USERS_PARAMS += "\
usermod -a -G docker torizon; \
groupadd -r ggc_group; \
useradd -r -g ggc_group -p '' ggc_user; \
"

IMAGE_INSTALL_remove = "vim-tiny"
