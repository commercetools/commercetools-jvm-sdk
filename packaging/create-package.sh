#!/bin/bash
mkdir -p sphere-store-kela/DEBIAN
mkdir -p sphere-store-kela/usr/share/sphere-store-kela
cp -r ../target/staged/* sphere-store-kela/usr/share/sphere-store-kela
chmod 755 sphere-store-kela/usr/bin/sphere-store-kela
chmod 755 sphere-store-kela/etc/init.d/sphere-store-kela
TIMESTAMP=`date +%Y%m%d%H%M%S`
sed "s/%VERSION%/1.0-SNAPSHOT-${TIMESTAMP}/g" control.template > sphere-store-kela/DEBIAN/control
fakeroot dpkg-deb --build sphere-store-kela ../target/sphere-store-kela-1.0-SNAPSHOT-${TIMESTAMP}_all.deb
