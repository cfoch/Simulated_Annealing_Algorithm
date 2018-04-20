#!/bin/bash -e
function get_module() {
  local path=$1
  while true; do
    path=$(dirname $path)
    if [ -f "$path/pom.xml" ]; then
      echo "$path"
      return
    elif [[ "./" =~ "$path" ]]; then
      return
    fi
  done
}

modules=()
curdir=$PWD
git_repo_dir=$(git rev-parse --show-toplevel)
changed_java_files=$(git diff --name-only --cached $git_repo_dir/*.java)

# Go to root dir for a while
cd $git_repo_dir

for file in $changed_java_files; do
  module=$(get_module "$file")
  if [ "" != "$module" ] \
      && [[ ! " ${modules[@]} " =~ " $module " ]]; then
    modules+=("$module")
  fi
done

if [ ${#modules[@]} -eq 0 ]; then
  exit
fi

modules_arg=$(printf ",%s" "${modules[@]}")
modules_arg=${modules_arg:1}

export MAVEN_OPTS="-client
  -XX:+TieredCompilation
  -XX:TieredStopAtLevel=1
  -Xverify:none"

config_file=$git_repo_dir/guidelines/sun_checks.xml
includes=$(git diff --name-only --cached \*.java | cut -d'/' -f4- | xargs -I{} printf "**\/{},")

mvn -pl "$modules_arg" checkstyle:check -Dcheckstyle.config.location=$config_file \
    -Dcheckstyle.includes=$includes

exit_status=$?
if [ $exit_status -ne 0 ]; then
    cd $curdir
    exit 1
fi
