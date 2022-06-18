# NotesApplication
Application used to add Notes. It works in offline mode only.
Video Demo


https://user-images.githubusercontent.com/67363661/174386493-efe19ff5-3811-4c02-aae6-8db57887d4f3.mp4


This application using room database for storing the data inside it.

A simple flow diagram of this project


<p float="left">
  <img src="https://user-images.githubusercontent.com/67363661/174389267-eb6a9690-5a38-4e5b-9fbd-60be6961d024.png" width="400" height="640" />
</p>

How this works:
1. We have to create an account inorder to use this application.All the infomation is stored local storage Room Database. while creating the account the password is Encrypted using ASE algorithm.
2. After successfully creation of account we have to Log in into the system.
3. After login we can see a page which id home page where we can see a round button for adding notes by clicking this button we can add notes by just giving title , description and Images.
4. We can see our notes in the home page and by clicking those notes we can see the details of the notes.

Database Schema:

 Auth(uid[PK],password)
 
 User(id[pk],uid,name,phone,pass)
 
 Notes(id[pk],title,user[FK],desc,images)
 
 Here 'uid' of Auth table and 'user' are connected for retriving requied notes for a particular user.
