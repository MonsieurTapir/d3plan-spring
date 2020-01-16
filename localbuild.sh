source myenvtest.env
docker start postgresdiablo
./gradlew build
docker build -t diabloplan .
docker stop postgresdiablo
source myenv.env