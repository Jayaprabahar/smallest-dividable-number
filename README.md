# Welcome

Welcome to Smallest Dividable Number within a range calcualtor !!
You know the case study!!!


## Solution source

This solution is based on unique prime factorization theorem. 
It says "Every positive integer n > 1 can be represented in exactly one way as a product of prime powers"

Refer here for the details:- 
[Canonical representation of a positive integer](https://en.wikipedia.org/wiki/Fundamental_theorem_of_arithmetic#Canonical_representation_of_a_positive_integer)


## Features/Constraints

1.	It is a SpringBootApplication
2.	There are two API calls written with POST methods for loading the upper limit and Get method for getting the response
3.	Session based data sharing between different API Calls. There are many better ways like redis, jwt. Couldn't opt due to time constraint.
4.	Three are 3 different type of Testing features implemented. Integration Testing, Unit testing the business logic and Performance testing.
5.	Allowed accept headers are JSON and XML
6.	Exceptions handled are UpperLimitNotSetException, UpperLimitReachedException, ConstraintViolationException and Not Acceptable (406)
7.	In-Code documentation and namings are made very clear.
8.	Test data csv is added and read through junit5 CsvFileSource
9.	The maximum time taken is less than 10 milliseconds in most of the cases.


## Versions

1.	spring-boot-starter-parent	2.3.1.RELEASE
2.	Open JDK 11
3.	SpringRunner 4.3


## Flexibility

1. Application name added to be suitable to be detected as a microservice with eureka
2. Server port is set to 8000, to avoid port conflict issue 8080
3. Allowed max limit value can be edited in application properties both in src and test
4.	Trying to benchmark the response with less than 20 milliseconds in the test application properties.


## Build

```
$ mvn clean install
```

You should able to see the application building, test execution for a total 12 testcases (10 + 1 + 1) and build success

```
[INFO] Scanning for projects...
[INFO]
[INFO] ---< com.jayaprabahar.europeana.assignment:smallestdividablenumber >----
[INFO] Building smallestdividablenumber 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
```

```
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ smallestdividablenumber ---
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
```

```
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.991 s - in com.jayaprabahar.europeana.assignment.integrationtest.ApplnIntegrationTest
[INFO] Running com.jayaprabahar.europeana.assignment.performance.ApplnPerformanceTest
```

```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.044 s - in com.jayaprabahar.europeana.assignment.performance.ApplnPerformanceTest
[INFO] Running com.jayaprabahar.europeana.assignment.unittest.ApplnLogicTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.003 s - in com.jayaprabahar.europeana.assignment.unittest.ApplnLogicTest
2020-06-30 02:50:46.656  INFO 22628 --- [extShutdownHook] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO]
[INFO] --- maven-jar-plugin:3.2.0:jar (default-jar) @ smallestdividablenumber ---
[INFO] Building jar: C:\Jayaprabahar\git\smallestdividablenumber\target\smallestdividablenumber-0.0.1-SNAPSHOT.jar
[INFO]
[INFO] --- spring-boot-maven-plugin:2.3.1.RELEASE:repackage (repackage) @ smallestdividablenumber ---
[INFO] Replacing main artifact with repackaged archive
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  10.499 s
[INFO] Finished at: 2020-06-30T02:50:47+02:00
[INFO] ------------------------------------------------------------------------
```


## Run

```
$ mvn spring-boot:run
[INFO] Scanning for projects...
[INFO]
[INFO] ---< com.jayaprabahar.europeana.assignment:smallestdividablenumber >----
[INFO] Building smallestdividablenumber 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
.
.
.
[INFO] --- spring-boot-maven-plugin:2.3.1.RELEASE:run (default-cli) @ smallestdividablenumber ---
[INFO] Attaching agents: []
.
.
.
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.1.RELEASE)
.
.
.
2020-06-30 03:20:48.950  INFO 7540 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8000 (http) with context path ''
2020-06-30 03:20:48.957  INFO 7540 --- [  restartedMain] j.e.a.SmallestDividableNumberApplication : Started SmallestDividableNumberApplication in 1.753 seconds (JVM running for 2.198)

```


## Scenarios

### Post Upperlimit - Correct Data - HTTP/1.1 201 - Created

```
$ curl -i -d "upperLimit=12" http://localhost:8000/europeana/smallestdividablenumber
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    13    0     0  100    13      0    619 --:--:-- --:--:-- --:--:--   650
HTTP/1.1 201
Set-Cookie: JSESSIONID=B692D092EF261BCB4C579FD8934AF79D; Path=/; HttpOnly
Content-Length: 0
Date: Tue, 30 Jun 2020 01:25:48 GMT
```


### Post Upperlimit - Non integer - HTTP/1.1 400 - Bad Request -Not a number error

```
$ curl -i -d "upperLimit=asdasd12" http://localhost:8000/europeana/smallestdividablenumber
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   328    0   309  100    19  16263   1000 --:--:-- --:--:-- --:--:-- 18222HTTP/1.1 400
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 30 Jun 2020 01:40:56 GMT
Connection: close

{"timestamp":"2020-06-30T01:40:56.604+00:00","status":400,"error":"Bad Request","message":"Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; nested exception is java.lang.NumberFormatException: For input string: \"asdasd12\"","path":"/europeana/smallestdividablenumber"}
```

### Post Upperlimit - bigger than allowed range - HTTP/1.1 400 - Bad Request - Allowed upper limit Error

```
$ curl -i -d "upperLimit=1212" http://localhost:8000/europeana/smallestdividablenumber
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   177    0   162  100    15   8100    750 --:--:-- --:--:-- --:--:--  8850HTTP/1.1 400
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 30 Jun 2020 01:41:04 GMT
Connection: close

{"timestamp":"2020-06-30T01:41:04.480+00:00","status":400,"error":"Bad Request","message":"Allowed upper limit is 25","path":"/europeana/smallestdividablenumber"}
```

### Post Upperlimit - Negative number - HTTP/1.1 400 - Bad Request - Only positive numbers are allowed Error

```

$ curl -i -d "upperLimit=-123" http://localhost:8000/europeana/smallestdividablenumber
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   211    0   196  100    15  21777   1666 --:--:-- --:--:-- --:--:-- 23444HTTP/1.1 400
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 30 Jun 2020 01:41:13 GMT
Connection: close

{"timestamp":"2020-06-30T01:41:13.206+00:00","status":400,"error":"Bad Request","message":"setUpperLimit.upperLimit: Only positive numbers are allowed","path":"/europeana/smallestdividablenumber"}
```

### Post Upperlimit - No post parameter - HTTP/1.1 400 - Bad Request - Required information missing error 

```
$ curl -i -d "" http://localhost:8000/europeana/smallestdividablenumber
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   191    0   191    0     0   9550      0 --:--:-- --:--:-- --:--:-- 10052HTTP/1.1 400
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 30 Jun 2020 01:41:36 GMT
Connection: close

{"timestamp":"2020-06-30T01:41:36.337+00:00","status":400,"error":"Bad Request","message":"Required Integer parameter 'upperLimit' is not present","path":"/europeana/smallestdividablenumber"}
```

### Get result - When no upper limit is set- HTTP/1.1 412 - Preconditons failed - Upper limit is not set error

```
$ curl -i http://localhost:8000/europeana/smallestdividablenumber
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   167    0   167    0     0  16700      0 --:--:-- --:--:-- --:--:-- 18555HTTP/1.1 412
Set-Cookie: JSESSIONID=50D5A1929A325C9D95D6255FFFF07232; Path=/; HttpOnly
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 30 Jun 2020 01:55:31 GMT

{"timestamp":"2020-06-30T01:55:31.441+00:00","status":412,"error":"Precondition Failed","message":"Upper limit is not set","path":"/europeana/smallestdividablenumber"}
```

As curl dont support sessions, i used Postman. 

### Get result - Accept header JSON/ Default

```
{
    "upperLimit": 12,
    "smallestdividablenumber": 27720,
    "timeTakenInMillis": 11
}
```

### Get result - Accept header XML

```
<Response>
    <upperLimit>12</upperLimit>
    <smallestdividablenumber>27720</smallestdividablenumber>
    <timeTakenInMillis>6</timeTakenInMillis>
</Response>

```

*Have fun!*

Best Regards,
Jayaprabahar
jpofficial@gmail.com