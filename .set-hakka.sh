#!/bin/sh
#
# Download and set up the shell for Activator.
# This must be executed like `. .set-hakka.sh` or `source .set-hakka.sh`.
# If some trouble happens, remove directory and zip archive then try again.
#
current=`pwd`
version="1.3.2"
# This directory has more free space than home folder.
directory="/tmp"
cd $directory
if [ ! -f "$directory/typesafe-activator-$version" ];
then
  if [ ! -f "$directory/typesafe-activator-$version.zip" ];
  then
    echo Downloading…
    wget --progress=bar http://downloads.typesafe.com/typesafe-activator/1.3.2/typesafe-activator-$version.zip
  fi
  echo Extracting…
  unzip -q typesafe-activator-$version.zip -d typesafe
fi
PATH=$PATH:$directory/typesafe/activator-$version
echo Set up finished.
cd $current
