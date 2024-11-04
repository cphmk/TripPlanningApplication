
3.3.3 As a minimum you should request all endpoints once to get all trips,
get a trip by id, adding a trip, updating a trip, and delete a trip. Also add a guide to a trip.
For each request, document the response in your README.md file by copying the response.


The documented response in my README.md file:


Testing started at 12:19 ...
GET http://localhost:3030/api/auth/test/

HTTP/1.1 404 Not Found
Date: Mon, 04 Nov 2024 11:19:35 GMT
Content-Type: text/plain
Content-Length: 34


Response file saved.
> 2024-11-04T121935.404.txt

Response code: 404 (Not Found); Time: 13ms (13 ms); Content length: 34 bytes (34 B)


POST http://localhost:3030/api/auth/register/
Content-Type: application/json

{
"username": "user1",
"password": "test12345"
}

HTTP/1.1 404 Not Found
Date: Mon, 04 Nov 2024 11:19:35 GMT
Content-Type: text/plain
Content-Length: 39


Response file saved.
> 2024-11-04T121935-1.404.txt

Response code: 404 (Not Found); Time: 9ms (9 ms); Content length: 39 bytes (39 B)


POST http://localhost:3030/api/auth/login/
Content-Type: application/json

{
"username": "user12",
"password": "test12345"
}

HTTP/1.1 404 Not Found
Date: Mon, 04 Nov 2024 11:19:35 GMT
Content-Type: text/plain
Content-Length: 36


Response file saved.
> 2024-11-04T121935-2.404.txt

Response code: 404 (Not Found); Time: 8ms (8 ms); Content length: 36 bytes (36 B)


Script finished
GET http://localhost:3030/api/protected/user_demo/
Accept: application/json
Authorization: Bearer

HTTP/1.1 401 Unauthorized
Date: Mon, 04 Nov 2024 11:19:35 GMT
Content-Type: application/json
Content-Length: 156


Response file saved.
> 2024-11-04T121935.401.json

Response code: 401 (Unauthorized); Time: 19ms (19 ms); Content length: 156 bytes (156 B)


GET http://localhost:3030/api/protected/admin_demo/
Accept: application/json
Authorization: Bearer

HTTP/1.1 401 Unauthorized
Date: Mon, 04 Nov 2024 11:19:36 GMT
Content-Type: application/json
Content-Length: 156


Response file saved.
> 2024-11-04T121936.401.json

Response code: 401 (Unauthorized); Time: 14ms (14 ms); Content length: 156 bytes (156 B)


POST http://localhost:3030/api/auth/user/addrole/
Content-Type: application/json
Authorization: Bearer

{
"role": "ADMIN"
}

HTTP/1.1 404 Not Found
Date: Mon, 04 Nov 2024 11:19:36 GMT
Content-Type: text/plain
Content-Length: 43


Response file saved.
> 2024-11-04T121936.404.txt

Response code: 404 (Not Found); Time: 8ms (8 ms); Content length: 43 bytes (43 B)


POST http://localhost:3030/api/trips
Content-Type: application/json
Authorization: Bearer

{
"name": "Hans Foster",
"startTime": "2024-3-12",
"endTime": "2024-27-12",
"startPosition": "Copenhagen",
"price": "2000",
"category": "snow"
}

HTTP/1.1 401 Unauthorized
Date: Mon, 04 Nov 2024 11:19:36 GMT
Content-Type: text/plain
Content-Length: 30


Response file saved.
> 2024-11-04T121936.401.txt

Response code: 401 (Unauthorized); Time: 10ms (10 ms); Content length: 30 bytes (30 B)


POST http://localhost:3030/api/trips/1/guides
Content-Type: application/json
Authorization: Bearer

{
"firstName": "james",
"lastName": "Cone",
"email": "jcone@hotmail.com",
"phone": "55693248"
}

HTTP/1.1 404 Not Found
Date: Mon, 04 Nov 2024 11:19:36 GMT
Content-Type: text/plain
Content-Length: 39


Response file saved.
> 2024-11-04T121936-1.404.txt

Response code: 404 (Not Found); Time: 6ms (6 ms); Content length: 39 bytes (39 B)


GET http://localhost:3030/api/trips
Accept: application/json

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:19:36 GMT
Content-Type: application/json
Content-Length: 873


Response file saved.
> 2024-11-04T121936.200.json

Response code: 200 (OK); Time: 18ms (18 ms); Content length: 873 bytes (873 B)


GET http://localhost:3030/api/trips/1
Accept: application/json

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:19:36 GMT
Content-Type: application/json
Content-Length: 142


Response file saved.
> 2024-11-04T121936-1.200.json

Response code: 200 (OK); Time: 32ms (32 ms); Content length: 142 bytes (142 B)


PUT http://localhost:3030/api/trips/3
Content-Type: application/json
Authorization: Bearer

{
"name": "jens Otto",
"startTime": "2024-5-11",
"endTime": "2024-1-12",
"startPosition": "Copenhagen",
"price": "2000",
"category": "snow"
}

HTTP/1.1 401 Unauthorized
Date: Mon, 04 Nov 2024 11:19:36 GMT
Content-Type: text/plain
Content-Length: 30


Response file saved.
> 2024-11-04T121936-1.401.txt

Response code: 401 (Unauthorized); Time: 7ms (7 ms); Content length: 30 bytes (30 B)


DELETE http://localhost:3030/api/trips/1
Authorization: Bearer
Accept: application/json

HTTP/1.1 404 Not Found
Date: Mon, 04 Nov 2024 11:19:36 GMT
Content-Type: application/json
Content-Length: 156


Response file saved.
> 2024-11-04T121936.404.json

Response code: 404 (Not Found); Time: 13ms (13 ms); Content length: 156 bytes (156 B)



3.3.5 Theoretical question: Why do we suggest a 
PUT method for adding a guide to a trip instead of a POST method? 
Write the answer in your README.md file.

answer: PUT is idempotent, it means that multiple identical requests will 
not change the outcome beyond the initial application. 
If you add the same guide to the trip multiple times with PUT, 
it won’t create duplicates or cause issues. It is more suitable for adding a guide
to a trip instead of a POST method  

POST is different, however, it is non-idempotent. 
It means each request could create a new association or resource,
which is less predictable in this scenario.
PUT is used to update an existing resource. 


8.3 Adding security roles to the endpoints will make the corresponding Rest Assured Test fail.
Now the request will return a 401 Unauthorized response. 
Describe how you would fix the failing tests in your README.md file, 
or if time permits, implement the solution so your tests pass.

Answer: 
Rest Assured tests may fail with a 401 Unauthorized error. 
This occurs because the endpoints now require authentication and authorization.
Here’s how to update the tests to include valid authentication credentials: