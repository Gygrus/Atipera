# Atipera
Simple app communicating with client by using Github API


### Tech stack
- Java 17
- Spring boot 3.1.0
- Gradle
- IDE: IntelliJ

### How to run
In order for this application to run smoothly and without any restrictions, create an .env file in the main directory of the project, in which write line `GITHUB_TOKEN={YOUR_GITHUB_API_TOKEN}`, where `{YOUR_GITHUB_API_TOKEN}` should be replaced by the token that can be aquired via https://github.com/settings/tokens.

To build and run this application, open the project via IntelliJ IDE and run `AtiperaApplication` class.

Server is running on `8085` port on localhost and simple communication can be achieved by sending a GET request to the endpoint:  
`http://localhost:8085/api/userrepositories/{username}`  
where `{username}` should be replaced by the github user name, whose repositories we want to list.
