# APOD Browser Android

Astronomy Picture of the Day client app for Android.

![APOD-Browser-Git-Hub-Hero.png](https://i.postimg.cc/90k7FR08/APOD-Browser-Git-Hub-Hero.png)

### What is APOD?
[APOD](https://apod.nasa.gov) stands for *Astronomy Picture of the Day*, a website created by NASA in collaboration with Michigan Technological University (MTU). Each day, it showcases a astronomy-related image accompanied by a brief explanation written by a professional astronomer.

### Main features
- __Stay Updated__: Follow the latest APODs from the past week.
- __Bookmarks__: Save your favorite APODs for easy access anytime, anywhere.
- __Calendar__: Explore a rich archive of 30 years of images.
- __Random Pick__: Discover a random image from the APOD's extensive library.
- __Image Downloads__: Download full-resolution APOD images.

### API
This app makes use of the [APOD API](https://github.com/nasa/apod-api), which is part of the [open APIs provided by NASA](https://api.nasa.gov/).

### Build instructions
1. Download and install the [Android Studio IDE](https://developer.android.com/studio).
2. Clone the project.
3. Open the project on Android Studio.
4. Select `Run > Run 'app'`.

OBS: By default, the app utilizes the `DEMO_KEY`, a public API key intended for testing purposes, allowing access to all functionalities. If you prefer to use your own API key, which can be generated at [NASA's Open APIs website](https://api.nasa.gov/), simply open the `ApodService.kt` file and replace the value of the `API_KEY` constant.

#### Staging build
This project includes a staging build that allows you to test most of the app's features without relying on the actual APOD API. The staging build supports testing by providing the necessary data for the Latest screen.
<p>Follow these steps to run the staging build:</p>

1. Navigate to Build in the menu and select Select Build Variant....
2. From the dropdown menu, choose the staging option.
3. Go to Run in the menu and select Run 'app'.

### Development technologies
This app is developed using [Android Views](https://developer.android.com/develop/ui/views/layout/declaring-layout) and follows the architectural principles described in the [Android Developers' guide to app architecture](https://developer.android.com/topic/architecture).

#### Libraries used
- [Kotlin Flow](https://developer.android.com/kotlin/flow)
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Material Components](https://github.com/material-components/material-components-android)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Navigation](https://developer.android.com/guide/navigation/)
- [Coil](https://coil-kt.github.io/coil/)
- [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [Retrofit](https://github.com/square/retrofit)
- [SwipeRefreshLayout](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout)
- [Room](https://developer.android.com/training/data-storage/room/)
- [PhotoView](https://github.com/Baseflow/PhotoView)
- [Preference](https://developer.android.com/jetpack/androidx/releases/preference/)

### License
```
Copyright 2025 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
