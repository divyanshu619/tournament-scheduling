## Kabaddi Tournament Scheduler

* Build the project and docker image with : 
`./gradlew clean build buildDocker`
* Update the server port and network mode if required in .env file in the root directory
* Start the server with the command `docker-compose up` in the root directory
* Execute the following curl request to add the initial teams : 

`curl --location --request POST 'http://localhost:8080/team/add-teams' --header 'Content-Type: application/json' --data-raw '{ "teamList" : [ { "teamName": "Puneri Paltan", "homeLocation": "Pune" }, { "teamName": "Patna Pirates", "homeLocation": "Patna" }, { "teamName": "Teleugu Titans", "homeLocation": "Hyderabad" }, { "teamName": "Bengaluru Bulls", "homeLocation": "Bengaluru" }, { "teamName": "Bengal Warriors", "homeLocation": "Kolkata" }, { "teamName": "Dabang Delhi", "homeLocation": "Patna" }, { "teamName": "U Mumba", "homeLocation": "Mumbai" }, { "teamName": "Jaipur Pink Panthers", "homeLocation": "Patna" } ] }'`
 
 * The tournament can be created which will have the schedule of matches with the following request : 
 
 `curl --location --request POST 'http://localhost:8080/tournament/create' --header 'Content-Type: application/json' --data-raw '{ "tournamentName": "PRO Kabaddi 2020", "startingDate": "2020-08-19T10:15:30+05:30[Asia/Kolkata]", "teamIdList": [1, 2, 3, 4, 5, 6, 7, 8] }'`