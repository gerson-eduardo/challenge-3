## Proposal management system

This project is about creating microservices for a voting system. By using a microservice architecture the system needs to:
1. Create a system that accepts proposals for improvements in the company. 
2. Enable employees to send proposals to the system.
3. Limit the amount of times that an employee can  vote in each proposal.
4. Create a persistent  h2 database that stores all data about the proposals and votes
___
### How to use:

In the collection folder you can find the requests that my program accepts. There you will also find the environment variables necessary to use the microservices. The most used requests are described bellow each entity description.

###### Step by step guide:
1. Create employees by using the POST request to the EmployeeController.
2. Update an employee role to ADMIN so that they can start and end polls.
3. Create a proposal using any employee cpf.
4. Start the voting session by using the  start-poll POST request to the ProposalController  with a valid ADMIN cpf.
5. Start creating votes using the cpf of employees on the database .
6. When the time of the session run out create a POST  to the end-poll request on the ProposalController with a valid ADMIN cpf
7. Find the result of the proposals by sending GET requests to the ProposalController

___
### MS-EMPLOYEES
The ms-employees microservice is used to register the company employees , give then roles, check their permissions as most HR systems. When creating an employee their base role is automatically assigned to USER. To delete an employee form the database their role must be deleted first.


#### The employee entity:

| Employee | SaveEmployeeDto | FindEmployeeDto |
| - | - | - |
|Long id | String name | String name |
| String name |String cpf | String cpf |
| String cpf | String address | String address |
| String address | String email | String email |
| String email | String password | |
| String password | | |

The employee entity holds all the personal information's about the company employee. I decided to create the SaveEmployeeDto to create restrict model to be used when creating a database entry. For the FindEmployeeDto i decided to hide the id and passwords so that it can not be easily found by any person.

##### Most important methods:
	1. POST: SaveEmployeeDto -> http://localhost:8080/ms-employees/api/v1/employee
	2. GET: PathVariable cpf -> http://localhost:8080/ms-employees/api/v1/employee/{cpf}
	3. PUT: SaveEmployeeDto -> http://localhost:8080/ms-employees/api/v1/employee
	4. DELETE: PathVariable cpf -> http://localhost:8080/ms-employees/api/v1/employee/{cpf}

1. Save an employee by using an SaveEmployeeDto as body of the request.
2. Find an employee by using an cpf in the path
3. Update an employee by using an SaveEmployeeDto as body of the request.
4. Delete an employee by using an cpf in the path(the employee role must be deleted before the employee deletion)
##### The role entity:

| Role | SaveRoleDto | FindRoleDto|
| - | - | - |
|Long id | String cpf | String role |
|Employee employee | String role| String name |
| String role | | String cpf|

The role entity holds the access level of the employee on the project system. The role holds all informations about the employee, but the only information retrieved by the FindRoleDto is the employee name, cpf and their role.  To create a role i use a employee cpf to bind the two tables together.

##### Most important methods:
	1. POST: SaveRoleDto -> http://localhost:8080/ms-employees/api/v1/role
	2. GET: PathVariable cpf -> http://localhost:8080/ms-employees/api/v1/role/employee/{cpf}
	3. PUT: SaveRoleDto -> http://localhost:8080/ms-employees/api/v1/role
	4. DELETE: PathVariable cpf -> http://localhost:8080/ms-employees/api/v1/role/{cpf}

1. Save an role by using an SaveRoleDto as body of the request.
2. Find an role by using an employee cpf in the path
3. Update an role by using an SaveRoleDto as body of the request.
4. Delete an role by using an employee cpf in the path.
___
### MS-VOTES
The ms-votes is the microservice that stores the information's about the proposals and votes. The microservice performs most of the standard CRUD operations and calculates the result of a proposal based on the  number of votes. The proposal will be accepted if the number of approval vote are higher than 50% of the total votes.The microservice will check any POST request to validate if the employee exists on the database. 

#### The proposal entity:
| Proposal | SaveProposalDto | FindProposalDto |
| - | - | - |
|Long id | String name | String name |
|String name | String description | String description |
|String description | | creationDate |
|String creationDate | | endingDate |
|String endingDate | | Boolean aprooved |
|Boolean aprooved | | |

The proposal entity holds information about the objective of the proposal,  important time information and the result information(the value starts as null). When a proposal is created a creationDate is inserted in the entity object along with the proposal name and description. The endingDate is inserted on poll creation and, finally, the approved boolean value is received after ending the poll
##### Most important methods:
	1.POST: PathVariable proposal-name, Parameter cpf -> http://localhost:8080/ms-voting/api/v1/proposal/name/{proposal-name}/start-poll?cpf={admin-cpf}
	2.POST: PathVariable proposal-name, Parameter cpf -> http://localhost:8080/ms-voting/api/v1/proposal/name/{proposal-name}/end-poll?cpf={admin-cpf}
	3. POST: SaveProposalDto -> http://localhost:8080/ms-voting/api/v1/proposal
	4. GET: PathVariable name -> http://localhost:8080/ms-voting/api/v1/proposal/name/{name}
	5. DELETE: PathVariable name -> http://localhost:8080/ms-voting/api/v1/proposal/name/{name}

1. Start the voting session. The request needs the proposal name and an ADMIN cpf.
2. Ends the voting session. The request needs the proposal name and an ADMIN cpf.
3. Create the proposal by using an SaveProposalDto as the request body.
4. Find  an proposal by using the proposal name in the path of the request.
5. Delete a proposal by using the proposal name in the path.
#### The vote entity:
| Vote | VoteDto | 
| - | - |
| Long id | String name |
| Proposal proposal |String cpf |
| String cpf | Boolean approved |
| Boolean aproved | |

The vote entity holds important information about the votes. On creation the information inside the vote is checked to avoid any wrong input. The name is used to check if a proposal with equal name exists. The cpf if used to send a requesto to the ms-employees service to find if their database holds a role with equal cpf value. The boolean value is then used to store the approval value of the vote. 
##### Most important methods:
	1. POST: SaveVoteDto -> http://localhost:8080/ms-voting/api/v1/vote
	2. GET: PathVariable cpf -> http://localhost:8080/ms-voting/employee/{cpf}
	3. DELETE: PathVariable cpf -> http://localhost:8080/ms-voting/employee/{cpf}

1. Save a vote by using an SaveVoteDto as body of the request.
2. Find all employee votes by using an employee cpf in the path.
3. Delete all votes by an employee by using the employee cpf in the path.
#### Known issues:
1. In the moment it is possible to start multiple times polls with the same proposal.
2. In the moment any sting will be accepted as cpf


###### Created by Gerson Eduardo