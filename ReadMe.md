The objective of this project is to 

1) Perform TDD while building a rest service(Unit Test Case, Integration Test)
2) Use Rest "best" practices
3) Using HATEOS
4) Exceptional Handling best practice
5) rest validation


-----------------------------------------------------------
REST urls:

GET http://localhost:8300/users/2
GET http://localhost:8300/users

POST http://localhost:8300/users
Data: {
	"username":"Kitty",
	"password":"@Kit67",
	"userTypeValue":"admin"
}

DELETE http://localhost:8300/users/2

PUT http://localhost:8300/users/3
Data: {
	"username":"Kitty_change",
	"password":"@Kit67",
	"userTypeValue":"admin"
}

Filter Based on user type:
GET http://localhost:8300/users?user-type=admin


Add order for a user:
POST http://localhost:8300/users/8/orders
Data:{
        "order_Id": 1,
        "name": "Chello Kabab",
        "price": "110.25",
        "quantity": 1
    }
    
Get a order for the user:
GET http://localhost:8300/users/8/orders/1

Get all orders for the user:
GET http://localhost:8300/users/8/orders
------------------------------------------------------------------------------
Exception Handling:
Sample error:
{
    "ApiError": {
        "httpStatus": "Not Found",
        "httpStatusCode": 404,
        "httpMethod": "GET",
        "timestamp": "2018-04-19T11:15:23.372",
        "userMessage": "ORDER was not found for id - 6",
        "systemError": "javax.persistence.EntityNotFoundException: Unable to find com.rest.example.entity.Order with id 6",
        "path": "uri=/orders/6"
    }
}

-------------------------------------------------------------------------------------
Validation Added and Custom Validation Error message:
Sample Output:
{
    "ApiError": {
        "httpStatus": "Bad Request",
        "httpStatusCode": 400,
        "httpMethod": "POST",
        "timestamp": "2018-04-19T15:36:13.976",
        "userMessage": "Validation Failed",
        "systemError": "Validation Failed",
        "path": "uri=/orders",
        "validationCustomError": [
            {
                "ValidationCustomError": {
                    "timestamp": "2018-04-19T15:36:12.581",
                    "message": "Validation Failed",
                    "details": "Name should have atleast 2 characters"
                }
            }
        ]
    }
}
--------------------------------------------------------------------------------------

