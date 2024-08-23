#!/usr/bin/env bash

echo "################################################################"
echo "#                                                              #"
echo "#  This script will run the webApp frontend test               #"
echo "#  the integration tests found in this project against it      #"
echo "#                                                              #"
echo "################################################################"

function runTests {

    echo "Getting selenium host ip"
    docker-compose up -d

    if [ "${bamboo}" == "bamboo" ]; then
        ip=$(docker-compose ps -q selenium | docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(xargs))
    fi

    sleep 10

    echo Selenium hub ip address: ${ip:=localhost}

    ENVIRONMENT=${testEnv}
    ISNEWTOKENENDPOINT=${newTokenEndpoint:=false}
    BROWSERFILE=${browserfile:=combinesuite.xml}

    echo Running tests in ${ENVIRONMENT:=dev} environment
    echo Running tests with new token endpoint ${ISNEWTOKENENDPOINT}

    ./gradlew clean test -Dtestfile=${BROWSERFILE} -Denvironment=${ENVIRONMENT} -Dremote.driver.url=http://${ip}:4444/wd/hub -Dclose_browser_after_test=false -DnewTokenEndpoint=${ISNEWTOKENENDPOINT}
 }

function cleanup {
  echo "Cleaning up docker compose environment..."
  docker-compose logs > compose.log
  docker-compose stop
  docker-compose rm -f
}


function usage {
  echo "Run with one argument: one of  'test' or 'rm'"
}

case "$1" in
  test)
  testEnv=$2
  bamboo=$3
  newTokenEndpoint=$4
  browserfile=$5
	runTests
	;;
  rm)
	cleanup
	;;
  *)
	usage
	exit 1
	;;
esac