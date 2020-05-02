**Better assignment**

_The application contains a solution for cascade inserting or updating 
doctors with patients and their diseases to a database via Http request or XML parsing._

Put xml files into the directory "input" or send http post request.

Build artifact: 
````
mvn clean test package assembly:single
````
**Unpack better.zip, the port 8080 should be free, Java version 14**

- Application start: java -jar better.jar
- Application test db: http://localhost:8080/h2-console/

Post example:
POST http://localhost:8080/api/save HTTP/1.1
Content-Type: application/json
````
 {
   "id": "101",
   "department": "better",
   "patients": [
     {
       "id": "13",
       "first_name": "John",
       "last_name": "Smith",
       "diseases": [
         "nice_to_people",
         "long_legss"
       ]
     },
     {
       "id": "20",
       "first_name": "Marys",
       "last_name": "Novak",
       "diseases": [
         "used_to_have_dredds",
         "nice_to_people"
       ]
     },
     {
       "id": "30",
       "first_name": "Alice",
       "last_name": "Woods",
       "diseases": [
         "chocaholic",
         "great_haircut"
       ]
     }
   ]
 }
````