# Reservation System

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Git
- MariaDB Server
- Maven
- Java

### Installation

1. **Clone the project**

   Use the following command to clone the project:

git clone git@github.com:jcilacad/reservation-system.git


2. **Create a database**

Open your MariaDB server and create a new database named `reservation_system`:
```sql
CREATE DATABASE reservation_system;
```

3. Update application.properties Navigate to the application.properties file in the project directory and update the username and password fields with your MariaDB server credentials:

spring.datasource.username=root
spring.datasource.password=password

4. Update AdminSeeder class Navigate to the AdminSeeder class located at src/main/java/com/system/reservation/online/bootstrap/ and update the admin credentials as needed. (The name must have a middle name, follow the format in the code)

5. Start the application Use Maven to start the application:
mvn spring-boot:run


## Demo

### Admin Side
1. Once you have logged in using the admin credentials, go to `/admin` to view the admin's dashboard.
2. In the accounts tab, you can add, query, update, and delete students.
3. In the items tab, you can add, query, update, and delete items.
4. In the transactions tab, you can query, view, approve, cancel, and delete transactions.
   - Remarks:
     - Pending: The student has placed the reserve item(s).
     - Approved: The admin has approved the reserved item(s).
     - Completed: The item has already been given to the student by the admin.
     - Cancelled: The item has been cancelled by the admin/student.
     - Overdue: The item is overdue based on the date inputted by the student.
5. You can generate bulk reports by remarks or generate reports individually by student email.

### Student Side
Note: The default password for the student is their student number. You can change the password in the 'Change Password' module in the app.
1. Once logged in, the student will be redirected to the dashboard.
2. In the items tab, you can query, view, and reserve items.
3. You can view, query, generate, and cancel reserved items.

## Screenshots

### Login Module
![image](https://github.com/jcilacad/reservation-system/assets/74972204/2a37de0e-b8fe-42cd-85b1-3dc657bca69d)

### Change Password Module
![image](https://github.com/jcilacad/reservation-system/assets/74972204/99aaa2ef-8e1b-4f17-9b80-ebfdec1bc0af)

### Admin
#### Dashboard 
![image](https://github.com/jcilacad/reservation-system/assets/74972204/8abb238c-18f5-46f9-bbcd-a6af8fa1597a)

#### Accounts Tab
![image](https://github.com/jcilacad/reservation-system/assets/74972204/ec66053b-5794-4763-b25a-1140b28d8bff)

#### Accounts Tab (Add Student)
![image](https://github.com/jcilacad/reservation-system/assets/74972204/af6a117a-875c-4d2e-9fd0-993505f42dfc)

#### Accounts Tab (View)
![image](https://github.com/jcilacad/reservation-system/assets/74972204/6a22d140-9332-4e8b-a661-a87ee660cab0)

#### Accounts Tab (Update)
![image](https://github.com/jcilacad/reservation-system/assets/74972204/85e3cf00-c1c9-45bc-bb1c-75bbcb85a8b2)

#### Items Tab
![image](https://github.com/jcilacad/reservation-system/assets/74972204/10ac201e-9765-48f9-8b69-5839c4dc580e)

#### Items Tab (Add)
![image](https://github.com/jcilacad/reservation-system/assets/74972204/c59656a9-b2ce-4591-b149-f745c7d797f6)

#### Items Tab (Update)
![image](https://github.com/jcilacad/reservation-system/assets/74972204/96db8fde-0a85-4140-a138-c64918b57d64)

#### Transactions Tab
![image](https://github.com/jcilacad/reservation-system/assets/74972204/422a9dd3-8c82-44c8-899a-fc6db88470de)

#### Transactions Tab (View)
![image](https://github.com/jcilacad/reservation-system/assets/74972204/0a4d3edb-6896-4281-adb6-4f04ad5ad351)

#### Report Tab
![image](https://github.com/jcilacad/reservation-system/assets/74972204/483fa92f-46a9-40a4-911c-687e12c45612)

### Student
#### Dashboard
![image](https://github.com/jcilacad/reservation-system/assets/74972204/e2423d0b-83fe-450f-b8cc-a7fe1d23ac09)

#### Items Tab
![image](https://github.com/jcilacad/reservation-system/assets/74972204/90ba0d1b-ef19-4046-ba7e-59cbe9751938)

#### Items Tab (View)
![image](https://github.com/jcilacad/reservation-system/assets/74972204/51a7af01-31cf-4e0b-8f2a-abcc32d5b3c9)

#### Transactions Tab
![image](https://github.com/jcilacad/reservation-system/assets/74972204/6d1bc7f3-80cd-4a8c-b9eb-963304745ff2)

#### Transactions Tab (View)
![image](https://github.com/jcilacad/reservation-system/assets/74972204/730eefc7-cac5-42ec-ba90-870db809921b)

#### Transactions Tab (Report Generation based on status)
![image](https://github.com/jcilacad/reservation-system/assets/74972204/a16f4324-bfb4-4ad2-8050-6eed0081688d)




