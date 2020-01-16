docker build -t ${DOCKER_IMG_NAME} .
docker tag ${DOCKER_IMG_NAME} registry.heroku.com/${HEROKU_APPNAME}/web
docker push registry.heroku.com/${HEROKU_APPNAME}/web
heroku container:release web -a ${HEROKU_APPNAME}