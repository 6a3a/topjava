**Meal REST Controller cURL requests**

_getAll_  
`curl --location --request GET 'http://localhost:8080/topjava/rest/meals/'`

_getBetween_  
from 31.01.2020 to 31.01.2020  
from 10:00:00 to 21:00:00    
`curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=10:00:00&endDate=2020-01-31&endTime=21:00:00'`

_get_  
id 100002  
`curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100002'`

_delete_  
id 100002  
`curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100002'`

_create_  
    "dateTime": "2020-11-30T13:00:00",  
    "description": "from postman",  
    "calories": 1000  
`curl --location --request POST 'http://localhost:8080/topjava/rest/meals/' \
 --header 'Content-Type: application/json' \
 --data-raw '{
     "dateTime": "2020-11-30T13:00:00",
     "description": "from postman",
     "calories": 1000
 }'`
 
_update_  
    "id": 100003,  
    "dateTime": "2020-01-30T13:00:00",  
    "description": "Обед updated",  
    "calories": 1000  
`curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100003' \
 --header 'Content-Type: application/json' \
 --header 'Cookie: JSESSIONID=44C8920BB21257339A7EE1FF2E10A1FF' \
 --data-raw '{
     "id": 100003,
     "dateTime": "2020-01-30T13:00:00",
     "description": "Обед updated",
     "calories": 1000
 }'`