CREATE TABLE students (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nickname VARCHAR(50),
                          name VARCHAR(50) NOT NULL,
                          furigana VARCHAR(100),
                          age INT,
                          gender VARCHAR(20),
                          email VARCHAR(100),
                          region VARCHAR(100),
                          note VARCHAR(1000),
                          delete_flag INT DEFAULT 0
);