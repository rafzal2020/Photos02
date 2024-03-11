# photos
Project made By Rayaan Afzal and Junaid Ghani
A photos app created using JavaFX Library for CS213

Features:
Albums
- Users can create an Album in which photos will go
- Deletion of Albums
- Rename Albums

Photos
- Photos can only be in albums
- Photo Dates (utilizie java.util.Calendar)
- Location of photos (i.e. user should be able to define where these locations are and they should persist throughout the application locally)
- Add photos
- Remove photos
- Caption/recaption a photo
- Display the photo in a separate display area
- Add tags to photos, which will also be used as search queries
- delete tags
- Copy photos across albums
- Cut and paste a photo to another album
- Be able to swipe through photos in a sequence

Searching
- search for photos by range of parameters such as:
  - date
  - tags
  - tag-value pairs (i.e. person=user, location=US)
    - utilize conjunctive and disjunctive tag-value pairs (AND, OR)
    - Create an album containing the search results (i.e search for pictures of pizza, there should be a feature that puts all pictures of pizza into an album)

User Account System
- An Admin system for developer use
  - Should display list of users, creating new users, deleting existing users
- Login with just a username (a password is not required)
- Each user has photos and albums pertaining to their account.
- Logout function which ensures the photos are saved to disk

Quitting the App
- Quitting the app safely so that all changes users made in the app are saved for the next time the app is ran again.



CURRENT GOAL: Create a proper Login System
