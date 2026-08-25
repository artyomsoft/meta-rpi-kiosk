FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://session"

LIC_FILES_CHKSUM = "file://session;endline=3;md5=9d8115f65e5fb57260b875d4130c5555"

do_install:append() {
   install -m 0755 ${WORKDIR}/session ${D}/etc/matchbox/session
}
