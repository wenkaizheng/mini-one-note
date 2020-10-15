## Note for development  
- For a new functionality, name it and put the name into NoteModel
.functionality, which is a enumeration type.
- The class in model package that keeps the data of new functionality should 
extend NoteShape and implements reDraw(), which enables the class to redraw 
itself entirely to view.

- Create a new class under view to store the code related to view.
- Update the update method in NoteView by inserting the functionality added 
in NoteModel. 

## 2019-4-23
- Finished view class for page and section.  

## 2019-4-22
- Add page and section class for model 
- The page and section will be showed by view

## 2019-4-18
- Added type function. User can type now.
- Added erasure function that can only erases drawing made by pen.
- Updated serializable process
- Updated the update method in NoteView, which now, uses enumeration types 
and switch.

## Before 2019-4-17  
- Created MVC  
- Users can draw on pages
- Can modify pen color and width
- All classes in view package only calls controller's method.
- Add enumeration types in model to indicate current functionality
- For every single line that user makes, we make a Path instance to 
represent it
- Path class can store color and width
- Process method and related methods in NotePen are static.
- Model is serializable

 
