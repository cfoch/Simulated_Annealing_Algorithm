#!/bin/bash

GIT_REPO_DIR=$(git rev-parse --show-toplevel)
PRE_COMMIT_HOOK_DST=$GIT_REPO_DIR/.git/hooks/pre-commit

if [ ! -f $PRE_COMMIT_HOOK_DST ]; then
	cp $GIT_REPO_DIR/hooks/pre-commit.sh $PRE_COMMIT_HOOK_DST
	chmod +x $PRE_COMMIT_HOOK_DST
fi
