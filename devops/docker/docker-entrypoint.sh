#!/bin/sh

set -e
echo "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"
echo "mmmmmmmmmmmmmmmmmmmmmm Booting Adobe Test WebServer   mmmmmmmmmmmmmmmmm"
echo "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"
echo "- Environment:"
echo "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"


java -jar /var/lib/adobe/adobe-test-webserver-all-1.0.jar
