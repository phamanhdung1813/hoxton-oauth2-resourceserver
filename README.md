## Application Back-End URL 👇

## https://hoxton-oauth2-resourceserver.herokuapp.com/

## Read OAuth2 Authorization Server application to learn how to get access_token and transfer token between 2 servers 👇

## https://github.com/phamanhdung1813/hoxton-oauth2-authserver

![image](https://user-images.githubusercontent.com/71564211/147984201-77492e69-102d-47aa-9417-84d503586a3e.png)

* Copy the token then paste into the https://jwt.io/ to parse the JWT token OR make the access to Authorization Server with URL https://hoxton-oauth2-authserver.herokuapp.com/oauth/token_key (credential username client1 and password client1)

![image](https://user-images.githubusercontent.com/71564211/147984257-0b1bc780-56c2-4e98-8245-1870abb38b01.png)

* All resource urls server need Bearer JWT access_token released by authorization server 

![image](https://user-images.githubusercontent.com/71564211/147984761-5b4a1885-859b-49dc-9aaf-c39a5fb38c27.png)

## Test some data on entity CRUB with database

![image](https://user-images.githubusercontent.com/71564211/147984923-313bae65-8343-4c96-a6b7-41da252fc21f.png)

## POST 
## 👉 https://hoxton-oauth2-resourceserver.herokuapp.com/api/resources/post

![image](https://user-images.githubusercontent.com/71564211/147984947-011e321a-a3e4-43bd-a8d8-e2280c798aac.png)

## GET ALL 
## 👉 https://hoxton-oauth2-resourceserver.herokuapp.com/api/resources/all

![image](https://user-images.githubusercontent.com/71564211/147984996-f07c4858-84c6-4d60-9516-13e65b321c40.png)

## GET BY ID 
## 👉 https://hoxton-oauth2-resourceserver.herokuapp.com/api/resources/all/{id}

![image](https://user-images.githubusercontent.com/71564211/147985081-4a3337ed-87fc-494c-bd92-0e42294ce733.png)

## UPDATE BY ID 
## 👉 https://hoxton-oauth2-resourceserver.herokuapp.com/api/resources/put/{id}

![image](https://user-images.githubusercontent.com/71564211/147985209-833b80e2-6552-4ce8-b827-3aecc2a1804f.png)

## DELETE BY ID https://hoxton-oauth2-resourceserver.herokuapp.com/api/resources/delete/{id}

![image](https://user-images.githubusercontent.com/71564211/147985328-0379709a-b54c-41cc-93a3-43a0937a7db2.png)

## SECURITY ON OAUTH2 JWT TOKEN
* Try to access with manager account on oauth2 server 
* Check the authorites and permission, then use the this token to make request into CRUD URL above

![image](https://user-images.githubusercontent.com/71564211/147985484-b304db2d-c4ea-48db-91a6-4ac6afcd873e.png)

![image](https://user-images.githubusercontent.com/71564211/147985559-c694d2cd-466c-460b-a3e8-7897e97d0a23.png)

* Deny access because, manager dont have accuracy permission to access on this URL
![image](https://user-images.githubusercontent.com/71564211/147985681-fcf506fe-cabf-4eff-98e1-8ab27a252915.png)

* Success access because this URL is allow to access (further more security informaton, view the @PreAuthorized on controller folder)
![image](https://user-images.githubusercontent.com/71564211/147985984-c1e6d55e-2fca-4ecd-b2e6-ae5356ab9e42.png)




