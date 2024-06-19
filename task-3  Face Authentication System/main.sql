-- Create tables for Users and FaceData

CREATE TABLE Users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL -- Store hashed passwords securely
);

CREATE TABLE FaceData (
    userId INTEGER,
    faceTemplate BLOB,
    FOREIGN KEY (userId) REFERENCES Users(id)
);
