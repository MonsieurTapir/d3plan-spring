if [[ -z "${SPRING_DATASOURCE_URL}" ]]; then
  export DB_URI=`echo ${DATABASE_URL} | sed -E 's/postgres:\/\/(.+):(.+)@(.+)/jdbc:postgresql:\/\/\3\?user=\1\&password=\2/'`
else
  export DB_URI=`echo ${SPRING_DATASOURCE_URL}`
fi

java -Dspring.datasource.url=$DB_URI -Xmx300m -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -jar /work/app.jar
