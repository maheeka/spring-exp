1. Start the application with
    mvn spring-boot:run

2. Invoke the service's get today's weather API with
     $ curl http://localhost:9000/today?city=Sydney
       Weather today is broken clouds


   To pass the correlation ID in the request, pass the X-Correlation-Idheader.
     $ curl --header "X-Correlation-Id: 123" http://localhost:9000/today?city=Sydney
