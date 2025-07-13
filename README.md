Google Drive Based File Storage System
# ☁️ Google Drive Based File Storage System

A full-stack  file storage system that allows users to upload, download, delete files, and create folders — built with **React**, **Spring Boot**, and **MySQL**.

## 🚀 Features

- 📤 File upload with size/type tracking
- 📥 File download with direct access
- ❌ File delete from server and DB
- 📂 Folder creation (by user)
- ⚙️ REST APIs integrated with React UI

## 🛠️ Tech Stack

| Layer       | Tech                          |
|-------------|-------------------------------|
| Frontend    | React.js, Axios, HTML, CSS    |
| Backend     | Spring Boot, Spring Data JPA  |
| Database    | MySQL                         |
| File System | Local storage (`/uploads`)    |

---

## 📁 Folder Structure

google-drive-based-file-project/
├── google-drive-react/ # React frontend
├── File-storage-google-drive-based-project\Google-File-Cloud\Google-File-Cloud # Spring Boot backend
└── README.md
🔧 Setup Instructions

 ✅ 1. Clone this Repo
git clone https://github.com/your-username/google-drive-based-file-project.git
cd google-drive-based-file-project

✅ 2. Backend Setup (Spring Boot)
•	Open the backend project in your IDE.
•	Configure application.properties with your MySQL DB credentials.
•	Create a folder named uploads in the backend root.
•	Run the Spring Boot app.
✅ 3. Frontend Setup (React)
cd google-drive-react
npm install
npm start
________________________________________
🧪 API Endpoints
Method	Endpoint	Description
POST	/api/files/upload	Upload a file
GET	/api/files/download/{id}	Download a file by ID
DELETE	/api/files/delete/{id}	Delete a file by ID
POST	/api/folder	

