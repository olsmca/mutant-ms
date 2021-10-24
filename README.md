# mutant-ms
Aplicacion Spring Boot responde peticiones REST

###Run Application
```./gradlew```

### Integration Test
####Install Newman
```npm install -g newman```
####Install Report
```npm install -g newman-reporter-htmlextra```

####Run Script
```newman run test_data/MutantDNA.postman_collection.json -d test_data/MutantDNA.postman_data.json -n 10 -r htmlextra,cli```

### Coverage Code
#### Run Sonar
```docker-compose -f src/main/docker/sonar.yml up -d```

#### Run Sonar Scanner
```./gradlew clean check jacocoTestReport sonarqube```
