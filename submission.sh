#!/bin/bash

# cleanup directories
rm -Rf submission/classes/*
rm -Rf submission/src/*
rm -Rf submission/test/*
rm -Rf sunmission/data/*

# populate directories
cp -R src/sg submission/src
cp -R test/sg submission/test
cp -R data-sample/. submission/data
mkdir -p submission/classes

# zip
cd submission
zip -r ../se24_F02.zip *
cd ..