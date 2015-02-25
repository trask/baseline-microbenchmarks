#!/bin/bash
set -e

if [ "$1" == "jmh" ]; then
  shift
  exec java -Djava.security.egd=file:/dev/urandom -jar benchmarks.jar "$@"
fi

exec "$@"
