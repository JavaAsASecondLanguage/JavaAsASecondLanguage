# Build

0. build Hello.java alone
    - `javac hello/Hello.java`
    - Oops
1. Dep.java -> Jar
    - `javac dep/Dep.java`
    - `jar -cf Dep.jar dep/`
2. Hello.java -> Jar
    - `javac -cp Dep.jar hello/Hello.java `
    - `jar -cf Hello.jar hello/`
3. run Hello.jar
    - Oops
    - Manifest:
        - Main-Class: hello.Hello
    - `jar -cfm Hello.jar Manifest.MF hello/`
    - Oops
        - class loading ...
        

1. gradle init
2. applying plugin
```
plugins {
    java
}
```
3. complex source set
```
java {
    sourceSets {
        main {
            java {
                setSrcDirs(
                        listOf("hello", "deps")
                )
            }
        }
    }
}
``` 

4. Main-class
```
val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "hello.Hello"
    }
}
```