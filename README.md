## Goal:

Create an API server which can analyse the content of big xml files
A distributable docker container running the server (Bonus)

## Requirement:
- Create an API with Spring Boot (Java) and Maven.
- A POST request should be possible with a url to an XML file (this file can be > 1GB)
- The response of the post request should hold an overview of the analysation of the XML (see example below, feel free to add more fields).
- The code should be unit and component tested.
- The code should pass the maven build and be runnable via cli with max of 512MB of memory.
At least a single Java 8 feature should be included.

### Request
``curl -i -X POST \
     -H "Content-Type:application/json" \
     -d \
  '{
    "url": "{{url}}"
  }' \
   'http://localhost:8080/analyze/'``
   
#### Response:
``{  
     "analyseDate":"2016-04-25T14:52:30+00:00",
     "details" {
         "firstPost":"2016-01-12T18:45:19.963+00:00",
         "lastPost":"2016-03-04T13:30:22.410+00:00",
         "totalPosts":80,
         "totalAcceptedPosts":7,
         "avgScore":13
     }
  }``
   
## Run application
Latest image will be pulled from Docker Hub. 
Just copy docker-compose.yml and run ``docker-compose up -d``
