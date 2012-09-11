#!/bin/bash

# Creates a zip file with the Sphere SDK ready for download.

echo "===== Pack SDK: Create a zipfile ($ZIP_NAME.zip) with the Sphere SDK for download, in current working directory."

ZIP_NAME="sphere-sdk"
INSTALL_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
ROOT_PATH=$INSTALL_PATH/..
TEMP_WORK_DIR="_sphere-sdk"
SAMPLE_STORE="sample-store-java"
SAMPLE_NAME="sphere-quickstart"
PROJECT_TEMPLATE="_project"
CONF_TEMPLATE="application.conf.template"

# Work in a temp subdirectory
mkdir $TEMP_WORK_DIR
cd $TEMP_WORK_DIR

# Copy to current directory
echo "  - Copying store template '$ROOT_PATH/$SAMPLE_STORE' to '`pwd`/$ZIP_NAME'"
mkdir -p $ZIP_NAME/templates
cp -r $ROOT_PATH/$SAMPLE_STORE $ZIP_NAME/templates/
mv $ZIP_NAME/templates/$SAMPLE_STORE $ZIP_NAME/templates/$SAMPLE_NAME

echo "  - Enabling template's build file by renaming '$PROJECT_TEMPLATE' to 'project'."
mv $ZIP_NAME/templates/$SAMPLE_NAME/$PROJECT_TEMPLATE $ZIP_NAME/templates/$SAMPLE_NAME/project

echo "  - Enabling template's config file by renaming '$CONF_TEMPLATE' to 'application.conf'."
mv $ZIP_NAME/templates/$SAMPLE_NAME/conf/$CONF_TEMPLATE $ZIP_NAME/templates/$SAMPLE_NAME/conf/application.conf

echo "  - Cleaning up the template a bit."
pushd $ZIP_NAME/templates/$SAMPLE_NAME/ > /dev/null # quiet
rm -r .idea
rm -r .DS_Store
rm -r logs
rm -r packaging
rm -r target
popd > /dev/null # quiet

echo "  - Copying sphere script '$ROOT_PATH/install/sphere' to '$ZIP_NAME'."
cp $ROOT_PATH/install/sphere $ZIP_NAME/

echo "  - Zipping everything in the temporary directory."
zip -r --quiet $ZIP_NAME $ZIP_NAME/
mv $ZIP_NAME.zip ../

cd ..
echo "  - Cleaning up the temporary directory."
rm -r $TEMP_WORK_DIR

echo "===== Pack SDK: Done: `pwd`/$ZIP_NAME.zip. Have fun!"
