# **Inventory Management Project**

### *i0p2b* Personal Project

## **What Will This Application Do?**

For my CPSC 210 project this term, I will be designing a program that will keep track of a given
inventory. With this program, users will be able to store and retrieve items in an inventory. Each item stored in
the inventory will have its own...
  
  - Name
  - Barcode(unique identifier)
  - Quantity
  - Category
  - Description
  
Through this application, users will be able to keep their inventory in an organized list of items. These items
will be able to be sorted by their name, quantities, or categories. Users will also be able to search
for items in their inventory by name or barcode, and when selected, users will be able to see all descriptors of
that item in detail.

## **Who Will Use This Application?**

Individuals who may find this application useful are people who run companies and need an easy way to keep track of what
goes in and out of their inventories. Several examples of these companies could be...
 
  - Restaurants who need to manage their ingredients, and beverages
  - Shipping companies who want to store the packages going in and out of their system
  - Retail clothing stores that need to keep track of quantities of each style of clothing

In short, I believe that this application will be used by any businesses or individuals who need a method of storing and
organizing items that are currently in their possession.

## **Why Does This Project Interest Me?**

My reasoning and interest in creating an application that manages inventory stems from one of my previous part-time
jobs. Although proper inventory management seems like something every modern business should have access to, it was not
the case with this particular business. The inventory management present at this business was unorganized, overly
complicated, and in some instances didn't even work properly. Due to the absence of a proper inventory system
, employees needed to do a lot of extra unnecessary work correcting errors made by the system. For these reasons, I
aim to create an inventory manager with more detailed descriptors, and a more intuitive interface to provide ease of
access to every item stored in the inventory.


## **User Stories**

As a user, I want to be able to ...

  - Create an item and add it into the inventory system
  - View a list of items currently in the inventory system
  - Search for items via item's name and view selected item's descriptors in detail
  - Search for items via item's barcode and view selected item's descriptors in detail
  - Remove the desired quantity of an item from the inventory system (*just added, not marked)
  
  
As a user phase 2, I want to be able to ...

  - Save my inventory to a file so that I can re-access it in the future
  - Load previous inventory so that I can interact with it again
  - Be reminded to save my inventory when I quit the application
  - Be given the option to load a previous inventory from a file upon starting application
  
  
As a user phase 3, I want to be able to (implemented phase 1/2 stories) ...

  - Create an item and add it into the inventory system
  - View a list of items currently in the inventory system
  - Search for items via item's name and view selected item's descriptors in detail
  - Search for items via item's barcode and view selected item's descriptors in detail
  - Save my inventory to a file so that I can re-access it in the future
  - Load previous inventory so that I can interact with it again
  - Be reminded to save my inventory when I quit the application
  - Be given the option to load a previous inventory from a file upon starting application


## **Phase 4: Task 2**

For task two of the project, the Java construct that I have included in my project is the use of a Map interface in
my code. However, I haven't actually changed anything in my code because the use of a Map interface has been present
since I began this project. The class containing the Map interface is the Inventory class, and it makes use of Java's
LinkedHashMap. This is because when I first started the project, I wanted to be able to store Item's within the
Inventory, but I also wanted to be able to find Items based on their barcode. For this reason, I made it so that my
Inventory was a LinkedHashMap where the key for every Item in the Map could be set to the Item's barcode. Once I
overrode the equals and hashcode in my Item class, this made it very easy to interact with Items within my Inventory.


## **Phase 4: Task 3**

Although when looking at my UML Class Diagram, I am able to understand the reasoning behind the association,
aggregation, and extension relationships, I feel that there are many design aspects that I could potentially improve
on. If I had more time to work on my project, I believe that I would definitely refactor parts of the code to
improve the overall simplicity, efficiency, and design of the code. Below is a list of code I would potentially
refactor...

  - In order to have the inventory visually update in the GUI, I created a class to convert my Inventory into a
   DefaultListModel<Item>. However, I feel like rather than using a DefaultListModel, I could have used Observers
   that would repaint the panels once it detected a change in the Inventory.
   
  - Similarly, I created a method in RightPanel that created a LeftPanel so that the LeftPanel could be accessed by
   the RightPanel. Instead of this, I think that I could refactor it so that the relationship between InventoryGUI
   and LeftPanel(respectively RightPanel) were bi-directional. That way, the RightPanel would be able to access the
   LeftPanel through their bi-directional relationship with the InventoryGUI class.
  
  - In the ui class, the ItemDetailsPanel and ItemFoundPanel are almost the same except for some small details
  . When I tried to use a single class called DetailsPanel for both occurrences of the panel, the itemUpdate method would
   not work properly which stopped the GUI from updating properly when a new Item got inputted. However, I think that
   I could refactor this part of the code by creating an abstract class and having the ItemDetailsPanel and
   ItemFoundPanel extend this class so that I wouldn't have two classes with duplicate code.
  
Overall, these are the 3 main refactors that I can think of when looking at my UML Class Diagram. However, based on
my experience writing the code, I feel that there are many more aspects that I could improve on throughout my code.
I think that I am currently not knowledgeable enough to incorporate more efficient methods into my design.  