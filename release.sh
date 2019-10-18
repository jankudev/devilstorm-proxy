#!/bin/sh

USERNAME=jankudev
IMAGE=devilstorm-proxy

# udpate
git pull

# bump version
docker run --rm -v "${PWD}":/app treeder/bump patch
VERSION=`cat VERSION`
echo "version: ${VERSION}"

# build
./build.sh

# tag release
git add -A
git commit -m "version ${VERSION}"
git tag -a "${VERSION}" -m "version ${VERSION}"
git push
git push --tags

docker tag "${USERNAME}/${IMAGE}:latest" "${USERNAME}/${IMAGE}:${VERSION}"

# push
docker push "${USERNAME}/${IMAGE}:latest"
docker push "${USERNAME}/${IMAGE}:${VERSION}"
