# Deliverables for 9Gag

Any Design Pattern or arichitecture that you have adopted and why you are using this pattern:
- Followed the Android material design colours for Primary, Primary Dark 
and Color primiary using the color tool. 
- Followed Android material design for text Size (using sp) . 
- Encapsulation class GagsData.class to pass a modal type into a functions so as to avoid passing too many arguments into functions and for easier arrayList adding. 
- Asynchronous design pattern FetchDataAsync.class to handle background fetch of JSON feeds and parsing of Json. So as to not stop UI thread when user is scrolling.
- Used Fragments for HOT,Trending and Fresh. This is so that i can use backstack and lifecycle features such as caching Data feeds from one fragment page to another and or retain state of fragment. 
- Naming conventions were followed for code readability.   
- Made sure no class or functions does anything other than it was suppose to do to further improve code readability. 
- Added Glide for image handling in thread. Used parameters to maintain the Aspect Ratio of Image. 
-
Technical Designs for future improvements: 
- HttpRequest.class needs network detections and Http retry and error handling, perhaps I should have used OKHttp.
-Improve image loading time and pre-load / storage in sqlite or interal cache storage.
- Using RecyclerView instead of ListView. 
-Retry for failed loading of Images. Glide has callback and listeners.
- To think of a good architecture for allowing one Class to handle all three fragments Hot,Trending and fresh instead of three seperate Classes.
-Using a Uri.Builder for URL "https://mock-api.9gaginc.com/" instead of exposing it as a String. (privacy)