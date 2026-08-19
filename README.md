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

<img width="366" height="775" alt="Screenshot_20250725_005606" src="https://github.com/user-attachments/assets/b2a07db7-49fc-4016-a645-2bc3eb54a8b2" />
<img width="366" height="775" alt="Screenshot_20250725_005741" src="https://github.com/user-attachments/assets/24262b29-b8c3-4232-91f1-49dc2c7978c0" />
<img width="366" height="775" alt="Screenshot_20250725_005938" src="https://github.com/user-attachments/assets/9396267d-443a-422b-a608-ca85cea8cedf" />
<img width="366" height="775" alt="Screenshot_20250725_010857" src="https://github.com/user-attachments/assets/36f878f7-8096-4242-9777-2fa367e7f29c" />

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
