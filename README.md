# Cloud storage RESTApi

Introducing the rest api for cloud storage on Spring boot. 
The application stores files in a postgresql database table in oid format.
Application uses http basic authentication.

## Requests: 
+ GET /users - get all users with files (only for admins)
+ GET /users/:id - get user by id (only for admins)
+ GET /users/count - get count of users (only for admins)
+ GET /users/profile - get authenticated user data
+ POST /users - add new user
+ PUT /users/:id - change prime status (only for admins)
+ DELETE /users/:id - delete user by id (only for admins)
+ GET /files/:id/info - get authenticated user file data by id
+ GET /files/:id/download - download authenticated user file by id
+ POST /files - add file or files to authenticated user
+ PUT /files/:fileId - change authenticated user file name by id
+ DELETE /files/:fileId - delete authenticated user file by id

## Installing:

```cmd
docker-compose up --build / make startapp (if you have make)
```

