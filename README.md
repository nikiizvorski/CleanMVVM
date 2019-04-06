# CleanMVVM

Integration of the latest small tricks with Kotlin: CleanMVVM Architecture with Dagger, RxJava2, KotlinX, Retrofit, OkHttp, Room, Realm, Repos and more. The structure is represented here: 

![alt text](https://imgur.com/NxWnKDk)

And this is the following example: Views and binding is in the view where it should and not have to deal with mix bindings on the model which was a bad decision on the developer who though its good to remove the views from the view and mix views and business layer together. The Business logic is structured well and in place. Models and different actions as networking and db are well places as you can see. The code is structured well and have been done with Google Code Style and Sonar Lint for linting. Documentation is added on all. 

Here you can find a very clean arch that can be used in production without any issues. And not having to deal with the mess of implementing something on the web thats completely messed up and would get you even more confused on the way of dealing with your application.

If you want me to include support for Fragment Injection with ViewModel and the relation between the activity and DaggerActivity and Fragment. Please let me know.
