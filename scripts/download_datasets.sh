#!/bin/bash
set -x
GIT_REPO_DIR=$(git rev-parse --show-toplevel)
DATASET_AUGERAT_DIR=$GIT_REPO_DIR/datasets/CVRP/augerat
ZIP_FILENAME="dataset.zip"

for t in {A,B,P} ; do
    dataset_dir=$DATASET_AUGERAT_DIR/$t
    dataset_path=$dataset_dir/$ZIP_FILENAME
    mkdir -p $dataset_dir
    curl http://neo.lcc.uma.es/vrp/wp-content/data/instances/Augerat/$t-VRP.zip \
        -o $dataset_path
    unzip $dataset_path -d $dataset_dir
    rm $dataset_path
done
