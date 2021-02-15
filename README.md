# web-crawler
Web Crawler for BuildIt Technical Assignment

Spring boot project based on java and maven created to develop web crawler and expose a friendly REST end point
to retrieve the output

To build the project use: 
mvn clean install
Please note this will generate a jar in the target folder

To run:
Option 1: Run it as spring-boot:run using editor
Option 2: java -jar jarget/web-crawler-0.0.1-SNAPSHOT.jar. I will upload a jar in the project for reference.

Use this end point: http://localhost:8080/web-crawler/crawl?url=<<website url>>

Please note, REST end point is just an additional feature added just to facilitate the testing. It neither has  
a robust exceptional handling nor any tests.

