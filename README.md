# Catbrary

An app for keeping track of the cats you see around, and for getting info about multiple cat breeds.

<img src="https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white"></img>
<img src="https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=black"></img>
<img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?logo=compose&logoColor=white"></img>
<img src="https://img.shields.io/badge/Material%203%20Expressive-2196F3?logo=materialdesign&logoColor=white"></img>
<img src="https://img.shields.io/badge/Room-78C257"></img>
<img src="https://img.shields.io/badge/Navigation%20Compose-2D3748"></img>
<img src="https://img.shields.io/badge/Volley-2D3748"></img>
<img src="https://img.shields.io/badge/Coil-2D3748"></img>
<img src="https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white"></img>
<img src="https://img.shields.io/badge/Min%20SDK-33-3DDC84?logo=android&logoColor=white"></img>
<img src="https://img.shields.io/badge/License-MIT-green"></img>

## Features

- Track the cats you spot with their photos and details
- Favorite cats to keep a personal collection
- Search through your catalog
- Browse a catalog of cat breeds with photos, temperament, origin, and 1–5 trait ratings (adaptability, intelligence, affection, energy, grooming, and more)
- Adaptive layout: navigation rail on medium+ screens, bottom bar on compact ones
- Material 3 Expressive with a warm-amber theme, animated state transitions, and motion-scheme springs
- Data persisted locally with Room

## Screenshots
<img width="366" height="775" alt="Screenshot_20260819_164325" src="https://github.com/user-attachments/assets/44408814-fe9a-44ee-b978-d616929a0008" />
<img width="366" height="775" alt="Screenshot_20260819_164123" src="https://github.com/user-attachments/assets/e30e9d6c-f7ca-4780-99ba-c854f2b9ab04" />
<img width="366" height="775" alt="Screenshot_20260819_164412" src="https://github.com/user-attachments/assets/9f6532b8-4940-4c0f-9879-706744eb47da" />
<img width="366" height="775" alt="Screenshot_20260819_164112" src="https://github.com/user-attachments/assets/f5a17c97-424f-452b-80c9-89e5faf7d449" />


## Tech Stack

| Layer      | Technology                         |
|------------|------------------------------------|
| Language   | Kotlin (JVM target 11)             |
| UI         | Jetpack Compose + Material 3 Expressive |
| Data       | Room (SQLite), KSP                 |
| Networking | Volley (The Cat API)               |
| Images     | Coil                               |
| Navigation | Navigation Compose                 |
| Build      | Gradle + Android Gradle Plugin     |

## Installation

- Install the `.apk` file
- **NOTE:** Android 13+ (API 33) required

## Building from source

1. Set your The Cat API key in `~/.gradle/gradle.properties`:

   ```properties
   MY_API_KEY=your-key-here
   ```

2. Build the debug APK:

   ```bash
   ./gradlew :app:assembleDebug
   ```

3. The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

> Breed trait ratings come from the historical The Cat API dataset and are embedded in the app, since the live API no longer returns those fields.

## License

[MIT](https://choosealicense.com/licenses/mit/)
