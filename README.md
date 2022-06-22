# Courier Company System
A simple courier company system written as a university project. We were focused on the backend so the frontend is really basic. We know that this project has some conceptual lacks and issues. But it was made only for learning how to make a web app. The backend is written in Java using spring boot. The frontend is written in TypeScript using angular 9. We also implemented sending emails with notification about parcels using Thymeleaf template engine. The parcels are automatically assigned to the warehouses using postal codes. The project has integrated PayPal payment method and oAuth2 authorization by Google.

In our system the following roles can be distinguished:
 - **admin**- Only one account with the "admin" login. 
	 - activating/deactivating users accounts
	 - managing the warehouse network
	 - registering employees
- **client**- company and individual clients
	- company users accounts must be activated by the administrator
	- individual clients can log in with google account
	- sending parcels
	- monitoring send parcels
- not-logged users can monitor and pay for parcels
- **courier**
	- picinig up parcels
	- marking parcels as paid
	- marking parcels as delivered/returned
	- handing parcels to warehouses
- **logistician**
	- browsing parcels assigned to the warehouse
	- assigning parcels to couriers
	- marking parcels to the couriers

# Project status
## Building
[![Build Status](https://dev.azure.com/dawidszymkiewicz/dawid_szymkiewicz/_apis/build/status/Haseoo.courier-company-system?branchName=master)](https://dev.azure.com/dawidszymkiewicz/dawid_szymkiewicz/_build/latest?definitionId=10&branchName=master)

[<img src="https://dawidhomeserver.ddns.net/sonar/api/project_badges/quality_gate?project=courier-cs">](https://dawidhomeserver.ddns.net/sonar/dashboard?id=courier-cs) 

## Measures
[<img src="https://dawidhomeserver.ddns.net/sonar/api/project_badges/measure?metric=sqale_rating&project=courier-cs">](https://dawidhomeserver.ddns.net/sonar/component_measures?id=courier-cs&metric=sqale_rating) [<img src="https://dawidhomeserver.ddns.net/sonar/api/project_badges/measure?metric=reliability_rating&project=courier-cs">](https://dawidhomeserver.ddns.net/sonar/component_measures?id=courier-cs&metric=reliability_rating)  [<img src="https://dawidhomeserver.ddns.net/sonar/api/project_badges/measure?metric=sqale_index&project=courier-cs">](https://dawidhomeserver.ddns.net/sonar/component_measures?id=courier-cs&metric=sqale_index) [<img src="https://dawidhomeserver.ddns.net/sonar/api/project_badges/measure?metric=ncloc&project=courier-cs">](https://dawidhomeserver.ddns.net/sonar/component_measures?id=courier-cs&metric=ncloc)

[<img src="https://dawidhomeserver.ddns.net/sonar/api/project_badges/measure?metric=coverage&project=courier-cs">](https://dawidhomeserver.ddns.net:/sonar/component_measures?id=courier-cs&metric=coverage) [<img src="https://dawidhomeserver.ddns.net:/sonar/api/project_badges/measure?metric=code_smells&project=courier-cs">](https://dawidhomeserver.ddns.net/sonar/component_measures?id=courier-cs&metric=code_smells) [<img src="https://dawidhomeserver.ddns.net/sonar/api/project_badges/measure?metric=bugs&project=courier-cs">](https://dawidhomeserver.ddns.net/sonar/component_measures?id=courier-cs&metric=bugs)
