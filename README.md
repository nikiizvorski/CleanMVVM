# CleanMVVM

![alt text](https://i.imgur.com/NA77aOD.png)

# Structure

- API (Services)
- App (Application)
- DB (Room)
- DI (Dependency Injection)
- EXT (Constants)
- FACTORY (AppFactory)
- POJOS (DataStorage Models Room/Realm Integration)
- REPOS (NetworkRepo, RepoIntegration, PrefsRepo)
- UI (Views and ViewModels - Sample Integration with KTX) 
- UTILS (DeviceUtil and DeviceWorker Integration)

This project is for learning purpose for people as a starting point. There should be some better organization but the code can be used in production applications with small adjustments depending on the project.  Most of the example code inside is for that. 

# Business Logic

The following has been applied. Views and binding is in the view where it should. There shouldn't be any of that in there. You have ViewModels taking care of that. View are used with AndroidX Integration to remove and optimise boilerplate. Models and different actions as networking and db are well places as you can see in different Repository.

# Code Style and Linting

The code is structured well and have been done with Google Code Style and Sonar Lint for linting. Documentation is added on all. 

# Testing

Testing haven't been integrated in this project. But would be fairly simple to do that. Firebase Cloud, Espresso, Robo etc.

# CI/CD

The project doesn't have any CD integrated. But if anyone would like to have that i would provide a sample implementation to any of the following: BitBucket, Github, CircleCI, Travis or any other.

# Language

The language of the code would stay Kotlin. The architecture can be applied and rotated to MVP fairly easy and to Java after if you want. 

# Documentation

It has been years for me when i started in University adding code documentation and i would like to think and continue to do that as a good developers practise. SOLID

# Comments and thoghts

Here you can find a very clean arch that will be updated in the future with some small samples and cool integrations over the time.  If you want me to include support for Fragment Injection with ViewModel and the relation between the activity and DaggerActivity and Fragment. Please let me know. I would be adding some more about that soon when i can find some more time. 

# Update

Next update will probably have Fragment Integration with a shared ViewModel and Navigation Controller for Task Managment into the application.

# Licenze

Feel free to use. If you can add me to any credits would appreciate it. Thanks.

