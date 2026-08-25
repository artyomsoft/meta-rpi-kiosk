SUMMARY = "Nu, Pogodi!"
DESCRIPTION = "SDL2-based Nu Pogodi"
LICENSE = "MIT"

SRC_URI = "git://github.com/artyomsoft/nupogodi-sdl.git;branch=yocto;protocol=https"
SRCREV = "65c26bdd3e27ba68d16f38ef6f526c821b52b762"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1643afdae60f67513b07ff7184ecc366"
PV = "1.0+git"

S = "${WORKDIR}/git"

DEPENDS = "libsdl2 libsdl2-image pkgconfig"
RDEPENDS:${PN} += "libsdl2 libsdl2-image"

inherit pkgconfig

EXTRA_OEMAKE += "\
    CC='${CC}' \
    CFLAGS='${CFLAGS} -I${S} -I${S}/cpu -I${S}/device -I${S}/data' \
    LDFLAGS="${LDFLAGS} -lSDL2 -lSDL2_image -lm" \
    LIBS='-lSDL2 -lSDL2_image -lm' \
"
do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 nupogodi-sdl ${D}${bindir}/nupogodi
    install -d ${D}${bindir}/resources/nupogodi
    install -m 0644 resources/nupogodi/fg.png ${D}${bindir}/resources/nupogodi/fg.png
    install -m 0644 resources/nupogodi/im-02.bin ${D}${bindir}/resources/nupogodi/im-02.bin
    install -m 0644 resources/nupogodi/nupogodi.svg ${D}${bindir}/resources/nupogodi/nupogodi.svg
}



