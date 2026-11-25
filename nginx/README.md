Assuming a windows machine, How to set local domains

0. Run XAMPP MySQL (don't turn on APACHE), Fake Twilio, and then Bluefit

1. Add to hosts file
~~~
127.0.0.1 bluefit.backend
127.0.0.1 bluefit.ui
127.0.0.1 twilio.fake
~~~
2. Install nginx, add to PATH, and run:
~~~
start.bat
~~~

3. Visit http://bluefit.ui

4. To stop, run:

~~~
stop.bat
~~~