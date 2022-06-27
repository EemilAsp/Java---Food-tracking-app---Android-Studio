# HotSoup
Android mobile application programmed with a group of three as a school project. Application is used to track persons daily calorie intake. The user can search differenet food items from THL api, create own meals, and keep track of the daily total nutritional intake (Protein/Carbs/Fats)
The application includes features such as

•	Utilizes object-oriented programming
•	At least 5 classes (GUI classes are not counted)
•	At least one API must be used, e.g. Climate diet: https://ilmastodieetti.ymparisto.fi/ilmastodieetti/swagger/ui/index
•	Application saves user inputs to log (JSON, XML etc.)
•	One has a way to view the log (plain text, with graphs etc.), so user has an option to check her inputs (e.g. own weight) development over time
•	Application is build from well designed UI-components
•	Application has a login function
•	Application can has several users (and one can create more), data is saved somewhere
•	Strong (at least one number, one special character, bot upper- and lowercase letters and is at least 12 chars long) login password is required
•	Password is hashed and salted
•	Another open data source is used (e.g. https://www.avoindata.fi/, https://www.europeandataportal.eu/fi  tai https://thl.fi/fi/tilastot-ja-data/aineistot-ja-palvelut/avoin-data/avoimet-rajapinnat), so that there are at least two data sources now implemented
•	User can input basic information of herself (length, weight, age, picture, city etc.) and these values are used in the application
•	Application collects data from user’s weight change (she inputs the data) ja shows graphical presentation of change over time
•	Application shows graphically how the output of climate diet api has changed over time (e.g. how meat consumption has decreased and CO2 footprint has changed)
•	Application tells risk factors in city, age group, etc. user is living (e.g. the percentage of smokers) based on THL data and users own inputs
•	Application combines data from at least two data sources and produces new information
•	Application remembers where user was before closing the app
•	Usage of asynchronous HTTP-calls when getting data
•	Use of fragments instead of activities when building GUI
•	Utilization of scoped storage when saving data (app does not require user permission to access mass media, but works in its own sandbox)
•	Responsive UI (It works well with different screen sizes)

