#!/bin/bash

set -e

patch_sbt_versions() {
    sed -i "s;2.0.4;2.0;g" project/plugins.sbt
    sed -i "s;0.11.3;0.11.2;g" project/build.properties
}

build_it() {
    sbt clean test:compile
    sbt stage
}

revert_changes() {
    git checkout -- project/plugins.sbt project/build.properties
}

package() {
    cd packaging
    ./create-package.sh
}

while getopts "d" OPT; do
case "${OPT}" in
        d)
            readonly DO_IT=true
            ;;
    esac
done

if [ "${DO_IT}" != "true" ]; then
    echo "Read the source luke..."
    exit 1
fi

revert_changes
patch_sbt_versions
build_it
revert_changes
package
