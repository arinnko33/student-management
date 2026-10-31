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

CREATE TABLE students_courses (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  student_id INT NOT NULL,
                                  course_name VARCHAR(100) NOT NULL,
                                  start_date DATE NOT NULL,
                                  end_date DATE NOT NULL,
                                  FOREIGN KEY (student_id) REFERENCES students(id)
);

CREATE TABLE students_courses_status (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         student_course_id INT NOT NULL UNIQUE,
                                         status VARCHAR(30) NOT NULL,
                                         FOREIGN KEY (student_course_id) REFERENCES students_courses(id)
);