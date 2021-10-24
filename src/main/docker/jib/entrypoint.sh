#!/bin/sh

echo "The application will start in 15s..." && sleep 15
exec java -jar -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.olsmca.mutant_ms.MutantMsApplication"  "$@"