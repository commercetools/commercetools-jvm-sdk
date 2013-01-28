#!/bin/bash

usage() {
    echo "$(basename $0) - Generates the SDK and Java Client Javadoc in the specified path."
    echo ""
    echo "Arguments:"
    echo "-o <output path>: output path where generated Javadoc is placed - default 'target/doc'"
    echo "-d: do something - otherwise nothing is done."
}

update_lib() {
    sbt clean update
    LIB_DIR=$(mktemp -d)
    trap "rm -rf '${LIB_DIR}'" EXIT
    find ~/.ivy2/cache -name '*.jar' -exec cp {} "${LIB_DIR}" \;
}

clean_output() {
    rm -rf "${DOC_DIR}"
    mkdir -p "${DOC_DIR}"
}

create_doc() {
    javadoc -d "${DOC_DIR}" -classpath "$LIB_DIR/*" -use -sourcepath sphere-java-client/src/main/java:sphere-sdk-java/app -subpackages de.commercetools.sphere -subpackages sphere
}

set -e

while getopts "c:d" OPT; do
    case "${OPT}" in
        c)
            DOC_DIR="${OPTARG}"
            ;;
        d)
            readonly DO_IT="true"
            ;;
        r)
    esac
done

if [ -z "${DOC_DIR}" ]; then
    DOC_DIR="target/doc"
fi

if [ "${DO_IT}" != "true" ];then
    usage
    exit 1
fi

update_lib
clean_output
create_doc

echo "===================================="
echo "Done."
echo "Find generated Javadoc in '${DOC_DIR}'"
echo "===================================="
