# ComboKing

## Building

- Install the Android SDK. You will have to use the *sdkmanager* command-line tool ([download command-line tools here](https://developer.android.com/studio#command-tools)) to install the Android SDK, version 30 or higher. Tested on version 30.
- Create a new file called *local.properties* in the project root directory, with the contents `sdk.dir=<path to Android SDK>`.
- Make sure the SDK used to build is JDK 11. I.e. set *Project Structure* > *Project SDK* to a locally installed version of JDK 11. Tested with OpenJDK 11.0.9.
- Do not use a JDK version above 11. It causes issues as Android and other libraries don't fully support those versions yet. See [this GitHub issue](https://github.com/fluttercommunity/flutter_workmanager/issues/287) and [this SO question](https://stackoverflow.com/questions/41265266/how-to-solve-inaccessibleobjectexception-unable-to-make-member-accessible-m/41265267), which includes an alternative solution that works with JDK 16.

## Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies. Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands. Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `android:lint`: performs Android project validation.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.