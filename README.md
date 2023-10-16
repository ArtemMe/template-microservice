## Intro
Hi! This is draft for microservice on springboot with crud/h2/feign 
and integration test with wiremock.

## Build
`docker build -t demo_draft/myapp_v1 .`   
For M1   
`docker build -t demo_draft/myapp_v1 . --platform linux/amd64`

## Run
`docker run -p 8080:8080 demo_draft/myapp_v1`