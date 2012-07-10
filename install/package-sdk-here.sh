#!/bin/sh

echo "Pack SDK: Package sphere sdk into a zipfile (sphere-sdk.zip) into current working directory."

INSTALL_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
ROOT_PATH=$INSTALL_PATH/..
TEMP_SDK_DIR="_sphere-sdk"
SAMPLE_STORE="sample-store-java"
SAMPLE_NAME="sphere-quickstart"
ZIP_NAME="sphere-sdk.zip"

# Copy to current directory
echo "Pack SDK: Copying store template '$ROOT_PATH/$SAMPLE_STORE' to '`pwd`/$TEMP_SDK_DIR'"
mkdir -p $TEMP_SDK_DIR/templates
cp -r $ROOT_PATH/$SAMPLE_STORE $TEMP_SDK_DIR/templates/
mv $TEMP_SDK_DIR/templates/$SAMPLE_STORE $TEMP_SDK_DIR/templates/$SAMPLE_NAME

echo "Pack SDK: Enabling sample build file by renaming '_project' to 'project'."
mv $TEMP_SDK_DIR/templates/$SAMPLE_NAME/_project $TEMP_SDK_DIR/templates/$SAMPLE_NAME/project

echo "Pack SDK: Cleaning up the template a bit."
pushd $TEMP_SDK_DIR/templates/$SAMPLE_NAME/ > /dev/null # quiet
rm -r .idea
rm -r .DS_Store
rm -r logs
rm -r packaging
rm -r target
popd > /dev/null # quiet

echo "Pack SDK: Copying sphere script '$ROOT_PATH/install/sphere' to '$TEMP_SDK_DIR'."
cp $ROOT_PATH/install/sphere $TEMP_SDK_DIR/

echo "Pack SDK: Zipping everything in the temporary directory."
zip -r --quiet $ZIP_NAME $TEMP_SDK_DIR/

echo "Pack SDK: Cleaning up the temporary directory."
rm -r $TEMP_SDK_DIR

echo "Pack SDK: Done: `pwd`/$ZIP_NAME"
