## The Curse of Whitcombe Manor

The Curse of Whitcombe Manor is a Lovecraft-inspired text-based adventure. You are a supernatural investigator that has received an anonymous tip regarding what the sender claims is an authentic haunting. The address in the letter leads you to a decrepit mansion in the outskirts of the city. The gardens are overgrown and unkempt. As you approach the entrance, you notice a crumbling plaque through the vines that creep up the side of the wall. It reads "Whitcombe Manor". You knock on the heavy oak door and are surprised when it immediately opens with a deep groan. A tall pale man in a tattered black suit peers out of the darkness at you and then takes a step backward, beckoning you to enter. **Can you survive the horrors within?**

## Installation & Setup

### Linux

#### Setup

Pull this repo and create a folder called `lib` at its root directory. Download the following dependencies: 

 - [OpenJFX 19](https://download2.gluonhq.com/openjfx/19/openjfx-19_linux-x64_bin-sdk.zip)
 - [Jackson Core 2.13.4](https://jar-download.com/artifacts/com.fasterxml.jackson.core/jackson-core)
 - [Jackson Databind 2.13.4](https://jar-download.com/artifacts/com.fasterxml.jackson.core/jackson-databind)
 - [Jackson Annotations 2.13.4](https://jar-download.com/artifacts/com.fasterxml.jackson.core/jackson-annotations)

Into the `lib` folder, copy all the Jackson JAR files, and all the files within the `lib` folder in OpenJFX 19. 

Once you've set up all the dependencies, navigate to the root directory of this repo and type the following command: 

`javac @build/linux`

#### Run

If the compilation was successful, you can now run the game by running the following command (**This needs to be run from the root directory of the repo**). 

`cd build & java @../run/linux`

### Windows

#### Setup

Pull this repo and create two folders, `lib` and `bin` at its root directory. Download the following dependencies.

 - [OpenJFX 19](https://download2.gluonhq.com/openjfx/19/openjfx-19_windows-x64_bin-sdk.zip)
 - [Jackson Core 2.13.4](https://jar-download.com/artifacts/com.fasterxml.jackson.core/jackson-core)
 - [Jackson Databind 2.13.4](https://jar-download.com/artifacts/com.fasterxml.jackson.core/jackson-databind)
 - [Jackson Annotations 2.13.4](https://jar-download.com/artifacts/com.fasterxml.jackson.core/jackson-annotations)

In the `lib` folder, copy the JAR files for Jackson Core, Jackson Databind, Jackson Annotations, and all the files from the `lib` folder in OpenJFX 19. In the `bin` folder, copy all the files from the `bin` folder in OpenJFX 19. 

Once you've set up all the dependencies, navigate to the root directory of this repo and type the following command: 

`javac @build/windows`

#### Run

If the compilation was successful, you can now run the game by running the following command (**This needs to be run from the root directory of the repo**).

`cd build & java @../run/windows`

## Contributors

* Shafin Kamal (`src/Story`)
* Anqi Chang (`src/StoryParser`)
* Yanyan Liu (`src/SaveNLoad`)