# LocationServiceAndroid
Retrieval of user's location using location service

In this app while the phone is not connected to the cable It does a few background tasks. 
- It retrieves the user's location every 5 seconds when the user has traversed 10 meters minimum and then stores it in database using a Content Provider.
- As soon as the phone gets connected to the cable, the location service stops.

>Location service is implemented in a Service class
>There is one main activity which informs the user if he's connected or not.
