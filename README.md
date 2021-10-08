# 🍽Restaurant
> Final project for EPAM Java external courses
<p align="center"><img src="https://i.imgur.com/5rgGOW5.png" width="500" /></p><br>

### User roles: 
- **Client** can view catalog of dishes with paginating, sorting and filtering, add them to cart, change their count or delete them from cart.
Can make an order and observe status of order.
- **Manager** can view all orders with filtering, change their status. Can delete a user or change his role (manager/client).
## Database schema
<p align="center"><img src="https://i.imgur.com/eMN5L6K.png" width="800"></p>

## Used technologies
- Java EE
  - Servlets
  - Filters
  - Listeners
  - JSP
- MySql (used standart JDBC for connection)
## Used patterns
- MVC
- DAO
- Abstract factory for dao for different databases
- Singleton for ConnectionPool
- Builder
## Set up project
> The app uses MySql database. If you don't have it, you should download it <a href="https://dev.mysql.com/downloads/">here</a>
- Clone current repository
- Set your database connect rules in ``` restaurant/src/main/webapp/META-INF/context.xml ```
- Run ``` restaurant/sql/db-create.sql ``` to set up database on your devise
- Run app using servlet container (Recommended Tomcat v.9.0.52)
- Use app in your browser
## Screenshots
### For user
<details>
<summary>Show</summary>

<img src="https://i.imgur.com/5rgGOW5.png" width="800" /><br>
<img src="https://i.imgur.com/9Z1nZ8r.png" width="800" /><br>
<img src="https://i.imgur.com/cpD4zIR.png" width="800" /><br>
<img src="https://i.imgur.com/i8ZRQDm.png" width="800" /><br>
<img src="https://i.imgur.com/njq99op.png" width="800" /><br>
</details>

### For admin

<details>
<summary>Show</summary>

<img src="https://i.imgur.com/gyISR6P.png" width="800" /><br>
<img src="https://i.imgur.com/6ItFJI0.png" width="800" /><br>
<img src="https://i.imgur.com/53QKOh5.png" width="800" /><br>
<img src="https://i.imgur.com/RyFbk4s.png" width="800" /><br>
</details>

## License
Copyright (c) 2021 Denys Bondarenko <br>
<a href="./LICENSE">MIT License</a>
