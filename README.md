# Gehtsoft Technical Assessment Solution

## Project Description

This application provides two main functionalities:

1. Caesar Cipher encryption/decryption
2. Arithmetic expression evaluation

## How to Compile and Run

### Requirements:
- Java installed (JDK 8 or higher)
- Development environment IntelliJ IDEA

### Steps:
1. Clone the project from the repository.
2. Open cloned project in IntelliJ IDEA.
3. Locate the ``GehtsoftAssessment.java`` file in the ``src`` directory (this is the main class).

4. Run the project:

   In IntelliJ IDEA: Right-click on ``GehtsoftAssessment.java`` and select ``"Run 'GehtsoftAssessment.main()'"``


## My Approach and Assumptions

### This application provides two main functionalities:

#### 1. Caesar Cipher Implementation

- Multilingual Support: Handles both Russian (Cyrillic) and English (Latin) alphabets
- Flexible Input: Accepts text from console input or file
- Bidirectional Operations: Supports both encryption and decryption
- **Can decrypt without knowing the shift value**
- Character Preservation: Maintains case sensitivity and leaves non-alphabetic characters unchanged
Wrap-around Support: Handles alphabet boundaries correctly (e.g., 'z' + 3 = 'c')


#### 2. Arithmetic Expression Evaluator

- Mathematical Operations: Supports addition (+), subtraction (-), multiplication (*), division (/)
- Order of Operations: Follows PEMDAS/BODMAS rules
- Parentheses Support: Handles nested parentheses correctly
- Number Support: Works with both integers and decimal numbers
- Negative Numbers: Properly handles negative number inputs
- Error Handling: Manages division by zero and invalid expressions

## Usage Examples
### Encryption and Decryption
#### Encryption
``` bash
   java GehtsoftAssessment
   
   Please choose an option:
   
1. Enter your choice: 1
2. Enter text to encrypt: Hello World
3. Enter shift value: 3
4. Result: Khoor Zruog

Continue? (y/n): 

   Please choose an option:
   
1. Enter your choice: 1
2. Enter text to encrypt: Привет Мир
3. Enter shift value: 3
4. Result: "Тулезх Плу"

Continue? (y/n): 

```
#### Decryption
``` bash
  Please choose an option:
   
1. Enter your choice: 2
2. Enter text to decrypt: Khoor Zruog
3. Enter shift value: 3
4. Result: "Hello World"

Continue? (y/n): 

  Please choose an option:

1. Enter your choice: 2
2. Enter text to decrypt: Тулезх Плу
3. Enter shift value: 3
4. Result: "Привет Мир"

Continue? (y/n): 
```
### Arithmetic Expression Evaluation
``` bash
   Please choose an option:

1. Enter your choice: 3
2. Enter arithmetic expression: 2 * (3 + 4) - 1
3. Input: "2 * (3 + 4) - 1"
4. Output: 13

Continue? (y/n): 
  ```
I am a beginner developer and I’m learning to write clean and understandable code. This project is part of my learning. ^_^

