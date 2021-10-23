# mutant-ms
Aplicacion Spring Boot responde peticiones REST


### Integration Test
####Install Newman
```npm install -g newman```
####Install Report
```npm install -g newman-reporter-htmlextra```

####Run Script
```newman run test_data/MutantDNA.postman_collection.json -d test_data/MutantDNA.postman_data.json -n 10 -r htmlextra,cli```