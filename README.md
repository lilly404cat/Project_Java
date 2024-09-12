# HospitalStocks
A Client-Server App that gives the possibility to manage the medical stocks and departments of a hospital through a simple and intuitive interface.

## Technologies

- **Java**
- **React**
- **MySql**

## Features

- signup/signin into the platform
- view all departments in a hospital and its stocks of medicines
- view statistics about the existing medicines
- manage the existing departments/stocks by adding/deleting/updating the data

## Running the project

To run the project locally, follow these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/lilly404cat/Project_Java.git
    cd Project_Java
   ```

2. **Run Server**:

   - Navigate into the Server folder:
    ```bash
      cd Server
    ```
   - Run the application from the Run button
   

3. **Run Client**:

   - Navigate into the Client folder:
    ```bash
      cd client
    ```
   - Install dependencies:
    ```bash
    npm install
    ```
   - Start the application:
   ```bash
    npm start
    ```
   - Open the app:
     Visit `http://localhost:3000` to see the app in action.
   

## Project Structure

```bash
├── client/             
   ├── public/            
   ├── src/
   │   ├── assets/
   │   ├── pages/    
   │   │   ├── departments/
   │          └── page.jsx
   │   │   ├── header/
              └── page.jsx
           ├── manage_supplies/
              └── page.jsx
           ├── signin/
              └── page.jsx
           ├── signup/
              └── page.jsx
            ├── statistics/
              └── page.jsx
       ├── index.js  
       ├── index.css  
       ├── App.js
   └──package.json       
├── db_script/      
└── Server/
    ├── src/ 
        ├── main/    
            ├── java/
            ├── resources/
        └── test/ 
    ├── target/  
    └── pom.xml       
    
```

## Video



