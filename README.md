# meta-rpi-kiosk

Yocto layer for building a lightweight kiosk Linux distribution for **Raspberry Pi Zero 2 W**.

The layer provides a ready-to-use kiosk configuration based on Yocto/Poky and the Raspberry Pi BSP. The resulting system boots directly into a fullscreen application without a conventional desktop environment.

The current example application is **Nu, Pogodi!**, an SDL2-based game. The layer can also be used as a starting point for building other single-purpose Raspberry Pi kiosk systems.

## Features

- Raspberry Pi Zero 2 W target
- Yocto **Scarthgap** support
- Dedicated `rpi-kiosk` distribution
- Matchbox window manager
- Automatic application startup
- No window title bar
- No mouse cursor
- Quiet Linux boot
- Linux kernel modules included
- SDL2-based **Nu, Pogodi!** application included
- MIT licensed layer

## Layer structure

```text
meta-rpi-kiosk/
├── conf/
│   ├── distro/
│   │   └── rpi-kiosk.conf
│   └── layer.conf
│
├── recipes-bsp/
│   └── bootfiles/
│       └── rpi-cmdline.bbappend
│
├── recipes-games/
│   └── nu-pogodi/
│       └── nu-pogodi.bb
│
└── recipes-sato/
    └── matchbox-sato/
        ├── files/
        │   └── session
        └── matchbox-session-sato_%.bbappend
```

## Dependencies

The layer depends on:

- Yocto/Poky
- `meta-raspberrypi`
- `meta-openembedded`
- Raspberry Pi BSP support

The layer is compatible with **scarthgap** and has `core` as its base layer dependency.


## Building

Initialize the Yocto build environment:

```bash
source poky/oe-init-build-env build-rpi
```

Select the Raspberry Pi Zero 2 W machine:

```text
MACHINE = "raspberrypi0-2w-64"
```

Select the kiosk distribution:

```text
DISTRO = "rpi-kiosk"
```

Clone the repository into the Yocto sources directory:

```bash
git clone -b scarthgap https://github.com/yoctoproject/poky.git

git clone -b scarthgap https://git.openembedded.org/meta-openembedded

git clone -b scarthgap https://git.yoctoproject.org/meta-raspberrypi

git clone -b scarthgap https://github.com/artyomsoft/meta-rpi-kiosk.git
```

```bash
bitbake-layers add-layer ../meta-openembedded/meta-oe

bitbake-layers add-layer ../meta-raspberrypi

bitbake-layers add-layer ../meta-raspberrypi
```

Make sure the required Raspberry Pi and OpenEmbedded layers are already present in your Yocto build environment.

```bash
bitbake-layers show-layers
```

Then build an image:

```bash
bitbake core-image-sato
```

The exact image target can be changed depending on the image configuration used by your Yocto project.

The `rpi-kiosk` distribution automatically adds `nu-pogodi` and `kernel-modules` to the image.

## Kiosk session

The layer replaces the standard Matchbox Sato session with a minimal session that:

1. starts Matchbox without a title bar;
2. disables the mouse cursor;
3. changes to `/usr/bin`;
4. starts `nupogodi`.

The session is therefore intended for a dedicated single-application device rather than a general-purpose desktop.

The relevant startup sequence is:

```sh
matchbox-window-manager -use_titlebar no -use_cursor no &

cd /usr/bin
exec /usr/bin/nupogodi
```

## Boot configuration

The layer modifies the Raspberry Pi kernel command line to reduce boot-time console output:

```text
quiet loglevel=0 vt.global_cursor_default=0
```

This helps give the device the appearance of an appliance rather than a conventional Linux system.

## Nu, Pogodi!

The example application is built from:

```text
https://github.com/artyomsoft/nupogodi-sdl
```

The recipe builds the SDL2 version of the game and installs the executable as:

```text
/usr/bin/nupogodi
```

Game resources are installed under:

```text
/usr/bin/resources/nupogodi/
```

The recipe uses SDL2 and SDL2_image as runtime dependencies.

## Customizing the kiosk

The layer is intentionally small so that it can be adapted to another application.

To replace `Nu, Pogodi!` with your own application, the main places to modify are:

### 1. Application recipe

Create or replace a recipe under:

```text
recipes-app/
```

and install your executable into `${bindir}`.

### 2. Matchbox session

Modify:

```text
recipes-sato/matchbox-sato/files/session
```

and replace:

```sh
exec /usr/bin/nupogodi
```

with your application.

For example:

```sh
exec /usr/bin/my-application
```

### 3. Image contents

The kiosk distribution currently adds:

```text
nu-pogodi
kernel-modules
```

to the image. Replace `nu-pogodi` with your own package when creating a different kiosk appliance.

## Status

This is a small example project demonstrating how to turn a Raspberry Pi Zero 2 W into a dedicated kiosk device using Yocto.

The repository currently contains only the components required for the example system and is not intended to be a complete general-purpose kiosk framework.

## License

This project is licensed under the **MIT License**.

See [COPYING.MIT](COPYING.MIT) for the full license text.

## Links

- [meta-rpi-kiosk](https://github.com/artyomsoft/meta-rpi-kiosk)
- [nupogodi-sdl](https://github.com/artyomsoft/nupogodi-sdl)
- [Yocto Project](https://www.yoctoproject.org)
- [Raspberry Pi Yocto BSP](https://github.com/agherzan/meta-raspberrypi)