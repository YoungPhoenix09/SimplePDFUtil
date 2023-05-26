# Simple PDF Utility

This is a utility application designed for doing simple page operations on PDF files.  

## Running the app
In a terminal or command prompt window, execute the following:
```shell
./gradlew build #only needs to be done once to build the jar
java -jar build/libs/SimplePDFUtil-1.0-SNAPSHOT.jar
```

## Commands

### "Add" Command
Add one of more files to be staged for operations.  Once a file is staged  
you will reference it in other commands by its file ID number
```
Specify command: add /path/to/file.pdf, /path/to/other_file.pdf

Current file list:
1. file.pdf - 3 page(s)
2. other_file.pdf - 2 page(s)
```  
### "Remove" Command
Remove one or more files from being staged for operations.
```
Current file list:
1. file.pdf - 3 page(s)
2. other_file.pdf - 2 page(s)

Specify command: remove 1

Current file list:
1. other_file.pdf - 2 page(s)
```  
### "Merge" Command
Allows you to merge multiple PDF files into one new file. The new file will be added  
to the list of staged files.
```
Current file list:
1. file.pdf - 3 page(s)
2. other_file.pdf - 2 page(s)

Specify command: merge 1, 2

Please specify the file name of the new file: /path/to/the/new_merged_file.pdf

Pages were merged into new file /path/to/the/new_merged_file.pdf.
Current file list:
1. file.pdf - 3 page(s)
2. other_file.pdf - 2 page(s)
3. new_merged_file.pdf - 5 page(s)
```  
### "Extract" Command
Use to take pages from one or multiple piles and combine them into a new file  
that is added to the list of staged files upon completion. You can specify ranges and/or  
lists of page numbers.
```
Current file list:
1. file.pdf - 3 page(s)
2. other_file.pdf - 2 page(s)

Specify command: extract 1, 2

Please list the pages/ranges for file ID 1: 1,3
Please list the pages/ranges for file ID 2: 1-2
Please specify the file name of the new file: /path/to/the/new_combined_file.pdf

Pages were extracted into new file /path/to/the/new_combined_file.pdf.
Current file list:
1. file.pdf - 3 page(s)
2. other_file.pdf - 2 page(s)
3. new_combined_file.pdf - 4 page(s)
```  
### "Delete" Command
Delete pages from a given file. Only supports one page at a time.
```
Current file list:
1. file.pdf - 3 page(s)
2. other_file.pdf - 2 page(s)

Specify command: delete 1

Please list the pages/ranges for file ID 1: 1-2

Delete operation was successful.
Current file list:
1. file.pdf - 1 page(s)
2. other_file.pdf - 2 page(s)
```  
### "Re-Order" Command
Rearrange the order of pages in a specified file. You can specify ranges and/or lists  
of page numbers. Any pages not specified will be added after the pages that were specified.  
Only supports one file at a time.
```
Current file list:
1. file.pdf - 3 page(s)
2. other_file.pdf - 2 page(s)

Specify command: reorder 1

Please list the pages/ranges for file ID 1: 2,1

Pages were successfully reordered.
Current file list:
1. file.pdf - 3 page(s)
2. other_file.pdf - 2 page(s)
```
### "Help" Command
Displays the list of available commands.
```
Specify command: help


Command List:
add - Add file(s) to the list of files that can be operated on.
remove - Remove file(s) from the list of operable files.
merge - Merge all pages from specified files into a new file.
extract - Take specific pages from specified files and combine them into one file.
delete - Delete pages from a given file.
help - Displays the list of available commands. View README for more details.
exit - Close the application

Current file list:
(No files)

Specify command:
```
### "Exit" Command
Exits the application.
```
Specify command: exit
```  
