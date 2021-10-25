#!/bin/sh

echo "The application will start in 15s..." && sleep 5
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -cp /app/resources/:/app/classes/:/app/libs/* "com.olsmca.mutant_ms.MutantMsApplication"  "$@"