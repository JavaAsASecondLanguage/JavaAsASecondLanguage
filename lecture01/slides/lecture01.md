## Java As A Second Language
### Lecture 01

---
## What is Advanced Java?

---
## Kotlin

---
## Now serious

---
## About me
- Sergei    
- Software Engineer
- [https://github.com/rybalkinsd](https://github.com/rybalkinsd)

- **Люблю зеленые билды**  

^^^
## About me
- Sasha
- Software Engineer
- [https://github.com/Al-p-i](https://github.com/Al-p-i)  

- **Люблю смотреть, как другие работают**  

---
## Agenda
1. How to pass this course
1. How to set up Java environment
1. How to submit your practice and homework
1. JShell
1. Hello World, the dark side
1. JDK, JRE, and JVM
1. Practice!

---
# Why Java?

---
## Java domain
- Web applications
- Middleware (Kafka/Spark/Flink)
- Android
- Java is lingua franca

---
# Орг информация

---
## План курса
1. Basics, git, gradle
2. Classes, OOP
3. Collections, Exceptions, Generics, Streams
4. Web client, HTTP, REST  
5. Web server, Annotations  
6. Java+SQL, JDBC
7. Basic concurrency, Critical section  
8. Basic concurrency, Concurrent data structures  
9. GC, Heap, Object layout
10. Experiments

---
## Оценка
- 10 занятий: теория + практика
- домашние задания после каждого занятия
- экзамена не будет
- оценка ставится в зависимости от процента сдачи домашних заданий  
  50% ─ 3  
  65% ─ 4  
  80% ─ 5  
  
---
## Домашние задания
Все домашние задания сдаются в виде **pull request**'ов в репозиторий курса
[github.com/JavaAsASecondLanguage](https://github.com/JavaAsASecondLanguage/JavaAsASecondLanguage)

Срок сдачи - день следующей лекции

---
# Setup

---
## Install JDK 14
With sdkman
https://sdkman.io/install

---
### Mac/Linux example:
```shell script
curl -s "https://get.sdkman.io" | bash
```
- open new terminal and check if installation is successful
```shell script
sdk version
```
- install JDK 14.0.2-adpt
```shell script
sdk install java 14.0.2-adpt 
```
---
## Install IntelliJ IDEA Community
https://www.jetbrains.com/ru-ru/idea/download

---
## Install git
https://git-scm.com/

^^^
# что вы хотите получить от курса?

---
## Gradle
- gradle - is a build system
- gradlew, gradle.bat - standalone gradle distributions, you do not need to install anything
- lecture01/build.gradle.kts defines build options and dependent libraries


---
# Lets dive in!

---
## JShell
```shell script
jshell
```
<img src="https://i.pinimg.com/originals/49/36/9d/49369d389233522a425f1327d8efecf2.jpg" alt="drawing" width="300"/>

---
## Where to get documentation
- Oracle official docs
https://docs.oracle.com/en/java/javase/
- play with *jshell*
- Google Style guide
https://google.github.io/styleguide/javaguide.html

---
## Hello World!
```java
public class Hello{
  public static void main(String[] args){
    System.out.println("Hello, " + args[0] + "!");
  }
}
```
```shell script
javac Hello.java
```
```shell script
java Hello world 
```
---
## Byte code
```shell script
javap -c Hello.class

Compiled from "Hello.java"
public class Hello {
  public Hello();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: getstatic     #7                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: aload_0
       4: iconst_0
       5: aaload
       6: invokedynamic #13,  0             // InvokeDynamic #0:makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;
      11: invokevirtual #17                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      14: return
}
```

---
## .class contains bytecode
Project with several java files will be compiled to a bunch of .class files.

To link several .class files use -cp option.
```shell script
java HelloWorld -cp SomeClassINeed.class SomeClassINeed2.class
```
Classpath contains a collection of .class files.

---
## .jar contains a bunch of .class file + resources
A standard form of distribution is *jar* - java archive.


*jar* utility can package them into a single .jar file, which can be executed or added to the classpath.


---
## Practice 1. make Pull Request to project repository
 - Make new branch from lecture01
```shell script
checkout -b practice1 upstream/lecture01
```
 - Un-ignore tests lecture01/practice1
 - Fix the code
 - Make sure tests run successfully with gradle
 - Make pull request to the [project repository](https://github.com/JavaAsASecondLanguage/JavaAsASecondLanguage) **branch lecture01**

---
## Gradle how to
Enter lecture01 directory
```shell script
cd ./lecture01
```
Build artifacts
```shell script
./gradlew clean assemble
```

Build and run tests
```shell script
./gradlew clean check
```

---
## How to submit practice or homework?

---
## 1. Fork the repository
[Course repository](https://github.com/JavaAsASecondLanguage/JavaAsASecondLanguage)

---
## 2. Clone your fork
После форка в вашем github появится несинхронизованная копия (**fork**), **склонируем** ее и получим **рабочую копию** форка
```bash
git clone https://github.com/YOUR_USERNAME/JavaAsASecondLanguage.git
```

---
## 3. Connect your working copy with course repository
Свяжем **рабочую копию вашего форка** с **репозиторием курса**, 
чтобы вы могли их синхронизировать и работать со свежей версией кода и проверим, что это сработало
```bash
cd JavaAsASecondLanguage
git remote add upstream https://github.com/JavaAsASecondLanguage/JavaAsASecondLanguage.git
git remote -v
origin  https://github.com/YOUR_USERNAME/JavaAsASecondLanguage.git (fetch)
origin  https://github.com/YOUR_USERNAME/JavaAsASecondLanguage.git (push)
upstream https://github.com/JavaAsASecondLanguage/JavaAsASecondLanguage.git (fetch)
upstream https://github.com/JavaAsASecondLanguage/JavaAsASecondLanguage.git (push)
```

^^^
Теперь ваш fork будет известен git-у как **origin** (по умолчанию)  
а репозиторий курса - как **upstream** (только что настроили)  

---
## 4. Make pull request with practice or homework

^^^

1. Update local cache of course repository
```shell script
git fetch upstream
```
2. Create new branch from upstream version
```shell script
git checkout -b lecture01 upstream/lecture01
```
^^^
## Make changes and push to your fork
```shell script
git pull --rebase upstream lecture01
//...make your changes
git add MyFixedFile1.java MyFixedFile2.java
git commit -m 'Fixed all bugs and added new'
git push -u origin lecture01
```

^^^
# Make pull request

^^^

## git branch commands
Посмотреть текущую ветку
```bash
> git branch
master
```
взять последние сведения о ветках из **вашего форка**
```bash
> git fetch origin
```
взять последние сведения о ветках из **репозитория курса**
```bash
> git fetch upstream
```
переключиться на ветку **lecture1**
```bash
> git checkout lecture1
```
Создать ветку **new-branch**
```bash
> git checkout -b new-branch
```

^^^
## git commit commands
посмотреть состояние **рабочей копии**
```shell script
git status
...
```
добавить файл к будущему коммиту (stage)
```shell script
git add changed_file
```
зафиксировать изменения в **локальном репозитории**
```shell script
git commit -m 'Сообщение с пояснением коммита'
```
послать изменения в **ваш форк** на github
```shell script
git push origin branch-to-push
```

^^^
## git update commands
взять новые изменения из **вашего форка**
```shell script
git pull --rebase origin master
```
взять новые изменения из **репозитория курса**
```shell script
 git pull --rebase upstream master
```
**--rebase** заставляет **git** переносить ваши изменения поверх изменений других людей в этой ветке, которые они сделали, пока вы работали над этой веткой локально  
(возможны конфликты)

^^^
## Git editor setup
Для некоторых интерактивных действий (например изменение описания коммита) git использует редактор    
Редактор по умолчанию - **vim**  
Для тех, кто не знает, [как выйти из вима](https://stackoverflow.com/questions/11828270/how-to-exit-the-vim-editor), и пользуется **windows**, простой путь - сделать редактором notepad
```bash
git config --global core.editor notepad
```

---
## Getting back to Java

---
## Java Facts
- Java is cross-platform - 'Write Once, Run Anywhere' (WORA)
- Java is compiled to Byte Code, which is executed by Java Virtual Machine (JVM)
- automatic memory management (GC)
- Java is object-oriented, class-based
- static strong safe typization
- concurrent

---
## JDK/JRE/JVM
JDK - Java Development Kit
JRE - Java Runtime Environment
JVM - Java Virtual Machine
![JDK/JRE/JDM](https://gitpitch.com/pitchme/cdn/github/rybalkinsd/atom/lecture01/247D7B1E71608DAC7DA101FEA4A15E47DB76572E7B6CCF18EE72259B9FD843A725CBA98C0863316AEEE68A6E3D1AAD5F24CD996A9AE24F6965B66E828F65D3A10B6C056F53B14756197F66A494E7B96831ADBF0F585661AB/lecture01/presentation/assets/img/jdk-jre2.png)

---
## Let's look inside your JDK distribution
- javac - java compiler
- java - java runtime (JVM)
- javadoc - documentation generator
- javap - .class flie disassembler


---
## Licenses
[OpenJDK](https://openjdk.java.net/) - is a free implementation of JDK  
[Oracle JDK](https://www.oracle.com/ru/java/technologies/javase-downloads.html) - proprietary build based on OpenJDK with additional commercial tools and features. Free for development, paid for production  
[AdoptOpenJDK](https://adoptopenjdk.net/) - free OpenJDK build supported by Eclipse Foundation  
There are JDK more builds from different vendors, in most cases it is OpenJDK builds (with support and additional features)  

[HotSpot](https://openjdk.java.net/groups/hotspot/) - implementation of JVM inside OpenJDK

---
## Versioning
- New version is released each 6 months  
- LTS (Long Term Support) version is released each 3 Years. Current LTS release is Java 11  

Java Usage Statistics (2020)
https://www.jrebel.com/blog/2020-java-technology-report#JDK-distribution

---
## Practice 2
## Make a Pull Request to the project repository
 - Make a new branch from lecture01
```shell script
checkout -b practice2 upstream/lecture01
```
 - Un-ignore tests lecture01/practice2
 - Implement your solution
 - Make sure tests run successfully with gradle
 - Make pull request with to [project repository](https://github.com/JavaAsASecondLanguage/JavaAsASecondLanguage) branch lecture01

---
## More to learn
- make sure you understand basic syntax, for example
https://www.w3schools.com/java/default.asp
- Walk through JShell
https://medium.com/swlh/how-to-use-jshell-to-improve-your-java-skills-170557d1d680
- Make sure you understand git branches
https://www.atlassian.com/git/tutorials/using-branches
- JDK 15 is General Availability
https://openjdk.java.net/projects/jdk/15/

---
## Today I Learned
1. How to pass this course
1. How to set up Java environment
1. How to submit practice and homework
1. About JDK, JRE, and JVM

