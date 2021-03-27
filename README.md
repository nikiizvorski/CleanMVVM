# CleanMVVM

![alt text](https://i.imgur.com/NA77aOD.png)

Structure
---------------

- API (Services)
- App (Application)
- DB (Room / Realm)
- DI (Dependency Injection)
- EXT (Constants)
- POJOS (DataStorage Models Room/Realm Integration)
- REPOS (NetworkRepo, RepoIntegration, PrefsRepo)
- UI (Views and ViewModels - Sample Integration with ViewBindings and DataBinding) 
- UTILS (Extensions and Worker Integration)

This project is for learning purpose for people as a starting point for MVVM with Hilt and Android Developers example architecture. There should be some better organization but the code can be used in production applications with small adjustments depending on the project and the logic has to be cleared a bit. It contains mostly small example of how to properly use the new apis and updates and also simple integration and adaptation to the new versions of Android Jetpack also you could add Composable implementation from here also used with Navigation. Most of the example code inside is for that to be able to understand the flow and if you have troubles making your own implementation. I would higlhy recommend to ask me first for a more cleaner sample of this architecture and i could give you an updated version with an application in production so you could see how the new api is implemented. I don't have time to update the whole application and publish it now. If you would rather have a better implementation go and visit Android Developer website. 

- This project would probably be discontinued or would be updated on requests. I would currently focus to update https://github.com/nikiizvorski/SimpleMVI look into it. Don't hesitate to ask me anything.

Dissadvantage of the project
---------------
There could be quite a lot of issues happening with DataBinding and some people even might say that there is logic inside the views. But to achieve MVVM at this stage you have to use it since there is no way to achieve it except to provide two directional data binding that is why most people prefer to go for MVI as it might be looking cleaner with Undirection data flow. Views shouldn't grow too much when you separate your logic properly. But ViewModels do grow quite a lof if you use are using SharedViewModels and you need to most of the time. 

Advantages of this project
---------------
MVVM is quite simple to use and there is almost no problems with this architecture when you use it correclty. Also to mention MVI and MVVM could be thought the same way it really depends on the implementation. MVVM is also simple to test even now with the Hilt implementation becomes easier. Using coroutines and flow also give an advantage. MVI is quite cleaner but takes time to learn so once you grasp it in big projects it is a wonder. As mentioned by everyone there is no best architecture almost all projects can be made with either one. But best would be to consider your resource, team, future of your project. 

Business Logic
---------------
The following has been applied. Views and binding is in the view where it should. There shouldn't be any of that in there. You have ViewModels taking care of that. View are used with AndroidX Integration to remove and optimise boilerplate. Models and different actions as networking and db are using the Repository pattern. 

The flow in this project is:

- View observe data - ViewModel routes the data being observed and asks from repositories or model - model or repo get's the data and either passes to viewmodel or updates the observer itself which would be better to do to have undirectional data flow and logic separation. 

Code Style and Linting
---------------
The code is structured well and have been done with Google Code Style and Sonar Lint for linting. Documentation is added on all. 

Testing
---------------
Testing haven't been integrated in this project. But would be fairly simple to do that. Firebase Cloud, Espresso, Robo etc.

- Added all the libraries for base setup for AndroidX on request for testing setup soon. 

CI/CD
---------------
The project doesn't have any CD integrated. But if anyone would like to have that i would provide a sample implementation to any of the following: BitBucket, Github, CircleCI, Travis or any other.

- Added base Bitrise Integration

Bintray - Maven/JCenter
---------------
Center automation and Distribution can be added also the scripts can be added without any issues or incompatibility 

Gradle Scripts and Managment
---------------
The Gradle scripts and managment haven't been updated to production build since the project is unknown and the future requirements are also. Once someone has a proposal i can add it and update a base default one. Which most people have in their company.

Language
---------------
The language of the code would stay Kotlin. The architecture can be applied and rotated to MVP fairly easy and to Java after if you want. 

Documentation
---------------
It has been years for me when i started in University adding code documentation and i would like to think and continue to do that as a good developers practise. SOLID

InteliJ Plugins Used
---------------
Plugins that i have personally used in our company and in my personal work. 

Comments and Ideas
---------------
Here you can find a very clean arch that will be updated in the future with some small samples and cool integrations over the time. I would be adding some more about that soon when i can find some more time. 

Android Jetpack and Navigation Integration Added
---------------
You can check the gist for some example and some more info about the integration. The project have a base structure of navigation host, graph and a menu with the new stuff from jetpack. Quite interesting for the new versions. Also you will find a bottomNavigation integration.

https://gist.github.com/nikiizvorski/d33b4619997aee78cae099e95b5eb38e

Android Lifecycle Integration Added
---------------
You can check the DeviceTest and DeviceFragment to see the integration of LicecycleEvents and LicecycleObserver. It can be benefitial to some projects but it can also be not so appropriate about some projects and can create a lot of confusion and issues on the way. From what i have tested from them they have worked fine in the small uses cases. Here you can get integration and be able to realise how it works.

DiffUtil Integration Added
---------------
DiffUtil is a utility class that can calculate the difference between two lists and output a list of update operations that converts the first list into the second one. It can be used to calculate updates for a RecyclerView Adapter. See ListAdapter and AsyncListDiffer which can compute diffs using DiffUtil on a background thread. There is a better alternative please refer to documentation.

Update - check branch unstable was merged
---------------
Next update will probably have some more advanced stuff inside

- Hilt Integration - done

- Removed Duplicates, Added ViewBindings, Added DataBinding, Project Gradle Updated and Android Studio Support Added,
Gradle And Build Scripts updated, Libraries Updated to latest, KTX Updated

- Update for MongoDB Realm 10 and RxJava3 is next and done

Project Migrated to RxJava3 and Retrofit and Adapters have been updated also to latest. Realm has been updated to latest MongoDB realm too

- Motion Layout

- AppStartup Lirabry improvement

- ActivityResult Library intergration and test

- Openable integration for Navigation

- Updated Navigation and Item Fetch

- If time Composable Integration with Migration Strategy and Integration of standalone or custom View

- Gradle Instant Execution Integration

- Kotlin Multiplatform in mind

- If requested i would make a sample integration for Pagin 3.0 and above!

- KSP will be integrated at a later date whewn most libraries support it!

- MotionLayout can be simply added from ConvertView Tab if you are already using ConstraintsLayout.

- Implemented Newer version will be keep updated until stable. 

- Implemented Next Update will be with Koin implementation as alternative for people who prefer and like the usage - koinbranch.

- Implemented Kotlin Extensions, MediatorLiveData, Transformations

- Implemented and added examples for Kotlin Flow, Kotlin Flow Dispatchewrs, Kotlin Flow Collection, Kotlin Sequences and a new retrofit examples

- Next Update will include Kotlin Sealed Classes and example of how you could clean your UI state

License
---------------

Feel free to use. If you can add me to any credits would appreciate it. Thanks. A good base would be to have something like the new projects that i will be adding for MVI and KotlinMultiplatform with MVI. You could also adapt this project for KotlinMultiplatform.


