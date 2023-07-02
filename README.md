# Cloud storage RESTApi

Introducing the rest api for cloud storage on Spring boot. The application stores files in a postgresql database table in oid format.

## Requests: 
+ GET /users/:id - get user by id
+ GET /users/count - get count of users
+ POST /users - add new user
+ PUT /users/:id - change prime status
+ DELETE /users/:id - delete user by id
+ GET /files/:id/info - get file data by id in json format
+ GET /files/:id/download - download file by id
+ POST /files/:username - add file or files to user
+ PUT /files/:fileId - change file name by id
+ DELETE /files/:fileId - delete file by id

## Installing:

```cmd
docker-compose up --build
```

