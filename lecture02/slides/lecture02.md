## Java As A Second Language
### Lecture 02
### Classes and Objects

---
## Agenda
1. Gradle howto
1. Classes and objects
1. Comparing objects
1. Inheritance
1. Interface and abstract class
1. Practice 1
1. Class Object
1. Enum
1. Java OOP Style
1. Practice 2

---
## Gradle How To

---
### Flashback
- Java is **object-oriented**, **class-based**
- Java has static strong typization 


---
### Static strong typization
- Static == compile time
    + fast runtime
    + errors in compile time
    - more time on prototyping
- strong typization - *no strict definition*, example:
    ```java
    long num = 42; // <-- legal
    int mindTheGap = 42L; // <-- compilation error
    ```


---
### Object oriented
- Everything is an object (except primitives)
- No code outside class

---
## Classes and objects
1. Gradle howto
1. **[Classes and objects]**
1. Comparing objects
1. Inheritance
1. Interface and abstract class
1. Practice 1
1. Class Object
1. Enum
1. Java OOP Style
1. Practice 2

---
### `class` Definition
```java
class TreeNode {
    TreeNode left; 
    TreeNode right; 
    int value;
}
``` 

---
## Ok in reality
```java
class TreeNode {
    private TreeNode left; 
    private TreeNode right; 
    private int value;
    public TreeNode getLeft(){
        return left;
    }
    public TreeNode getRight(){
        return right;
    }
    public int getValue(){
        return value;
    }
    public void setLeft(TreeNode left){
        this.left = left;
    }
    public void setRight(TreeNode right){
        this.right = right;
    }
    public void setValue(int value){
        this.value = value;
    }
}
``` 
---
### Instantiation
```java
TreeNode node1 = new TreeNode();
var node2 = new TreeNode();
```

---

## What happen when you create an object
<img src="allocation.png" alt="me" style="width: 750px;"/>
 
**pOne != pTwo**
 
**pTwo == winner**


---
### Where class can be defined
1. Public class in file (only one)
1. non-public class in file (any number)
1. inside other class (**nested class**)
1. inside method (**inner class**)
  
Be simple, use single public class in file


---
## Record classes
Real projects actually have many "data carrier" classes  
"classes which are transparent holders for shallowly immutable data"
    
So starting from Java 14 we have compact syntax for those cases  
https://openjdk.java.net/jeps/359

[It's a preview feature](https://openjdk.java.net/jeps/12)
---
## Record class
```shell script
record TreeNode(TreeNode left, TreeNode right, int value){}
``` 
---
## Records: What is under the hood
 
```shell script
javac TreeNode.java --release 14 --enable-preview
```
```shell script
javap TreeNode.class
```

 
---
## What is generated for record
- A private final field for each component of the state description
- A public read accessor method for each component of the state description
- A public constructor, whose signature is the same as the state description
- Implementations of equals and hashCode
- An implementation of toString

---
### `null` literal

`null` is a default value for reference type.

```java
String str = null;

TreeNode node = null;

assertFalse(node instanceOf TreeNode); // <-- OK
assertFalse(null instanceOf AnyClass); // <-- OK 
```

---
### Null is bad, right?
Tony Hoare: "null is a billion dollar mistake"
  
check java.util.Optional


---
### quiz

```java
System.out.println(null == null);

// 1. `false` in output
// 2. `true`  in output
// 3. NullPointerException
```

[Read more about `null`](http://javarevisited.blogspot.ru/2014/12/9-things-about-null-in-java.html)


---
### Constructor & `this` keyword
```java
class TreeNode {
    private TreeNode left;
    private TreeNode right;
    private int value;   

    public TreeNode(TreeNode left, TreeNode right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }
    
    public TreeNode(int value) {
        this(null, null, value);
    }
}
```
[Read more about `this`](https://docs.oracle.com/javase/tutorial/java/javaOO/thiskey.html)


---
### Default constructor

**NO** default constructor is generated, if custom constructor is present.

[Read more in official docs](https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html)

[Read more on Stack Overflow](http://stackoverflow.com/questions/4488716/java-default-constructor)

---
## Comparing objects
1. Gradle howto
1. Classes and objects
1. **[Comparing objects]**
1. Inheritance
1. Interface and abstract class
1. Practice 1
1. Class Object
1. Enum
1. Java OOP Style
1. Practice 2
---

## Two ways to compare objects
1. **==**  
Compares that references point to the same object in memory  
1. **equals()**  
Custom object equivalence check (by default works as **==**) 

---
## How to check equality?
Equals or == ?
- String ? String 
- int ? int 
- Integer ? Integer 
- TreeNode ? TreeNode 

---
## Inheritance
1. Gradle howto
1. Classes and objects
1. Comparing objects
1. **[Inheritance]**
1. Interface and abstract class
1. Practice 1
1. Class Object
1. Enum
1. Java OOP Style
1. Practice 2


---
### Inheritance
#### Is-a relation

```java
class Message { 
    private String content;
}

class TitledMessage extends Message {
    private String title;
}
```
Titled message **is a** Message

Single class – single superclass


---
## Java does not support multiple inheritance

---
### Access modifiers

1. **private** - only from class code
    ```java
    private Object topSecret; 
    ```
1. **default** (package) - as private + within package
    ```java
    int number = 42;
    ```
1. **protected** - as default + from subclasses
    ```java
    protected Boolean секретик;
    ```
1. **public** - worldwide
    ```java
    public String getMe;
    ```
1. Modules visibility

[Read more in official docs](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)

---
## `instanceof` operator

```java
Message message = new Message();

assertTrue(message instanceof Message); // <-- OK
```

---
## `Object` class #1
Everything* is instance of `Object`.

```java
// Informally
class Message extends Object { }
```

```java
assertTrue(message instanceOf Object); // <-- OK
```

---
## Constructors and inheritance

I want:
```java
TitlesMessage message = new TitledMessage(title, content);
```


---
## Constructors and inheritance

```java
class Message {
    private String content;
    
    public Message(String content) {
        this.content = content;
    }
}
```


---
## Constructors and inheritance

```java
class TitledMessage extends Message {
    private String title;
    
    public TitledMessage(String title, String content) {
        // ..........
    }
}

class Message {
    private String content;
    
    public Message(String content) {
        this.content = content;
    }
}
```


---
## super
```java
class TitledMessage extends Message {
    private String title;
    
    public TitledMessage(String title, String content) {
       super(content);
       this.title = title;
    }
}

class Message {
    private String content;
    
    public Message(String content) {
        this.content = content;
    }
}
```


---
## What about init order?
When you instantiate class with complex inheritance - what will happen in which order  
@See instantiation example


---
## Methods
Declaration
```java
class Message {
    private String content;

    public String getContent() {
        return content;
    }
    
    public Message(String content) {
        this.content = content;
    }
}
```
---
## Methods
```java
var message = new Message("my content");
message.getContent();

assertEquals("my content", message.getContent())); // <-- OK
```

---
### Methods, overloading

Let's add some "pagination"
```java
class Message {
    private static final int CHARS_PER_PAGE = 256;
    private String content;
    
    private String getContent() {
        return content;
    }
    
    private String getContent(int pageNum) {
        if (pageNum < 0) {
            throw new IllegalArgumentException(
                    "Page number should be >= 0, got " + pageNum);
        }
            
        return content.substring(pageNum * CHARS_PER_PAGE, 
            (pageNum + 1) * CHARS_PER_PAGE);
    }
    
    // ...
}
```


---
## `static` keyword

static - "common for all class instances"

Definition
```java
class Utils {
    public static final int DEFAULT_MAX = 0;
    public static int getMax(int[] values) {
        if (values == null || values.length == 0) {
            return DEFAULT_MAX;
        }
        
        return Arrays.stream(values)
                .max()
                .getAsInt();    
    }
}
```
---
## `static` keyword

```java
int max = Utils.getMax(new int[] {1, 2, 3});
System.out.println(Utils.DEFAULT_MAX);

```


---
## Methods, polymorphism
```java
class TitledMessage extends Message {
    private String title;
    
    @Override
    public String getContent() {
        return "Title: " + this.title + ".\n" + getContent();
    }
    // ...
}
```


---
## Override definition

Instance method in a subclass with the **same signature** (name, plus the number and the type of its parameters) 
and **return type** as an instance method in the superclass **overrides** the superclass's method.

[Read more in official docs](https://docs.oracle.com/javase/tutorial/java/IandI/override.html)

**Note:** `@Override` is **just an annotation to declare** your intentions to override method 

---
## Override vs overload note

**Override** resolves method in **runtime**  
**Overload** resolves method in **compile-time**

---
## Encapsulation wisdom

```java
Message message = new TitledMessage("Awesome title", "Perfect content");
message instanceOf TitledMessage // <-- It is true 
```
Software engineering wisdom:  
**Do not** disclose the details of implementation (without need).
Use "interface" wherever you can.  
  
*btw why?*

---
## Final

---
## `final` field

constant declaration 
```java
class Utils {
    public final int DEFAULT_MAX = 0;
}
```
    
    
--- 
## final method (forbidden override)
```java
class Message { 
    public final String getContent() { 
        return content;    
    } 
}
```
---
## final class (forbidden to override)
```java
final class Message {
    public String getContent() { 
       return content; 
    } 
}
```
See Sealed classes in Java15
https://openjdk.java.net/jeps/360

---
## Immutable TreeNodes
```java
class TreeNode {
    //Note: final only make reference immutable, not the content
    private final TreeNode left; 
    private final TreeNode right; 
    private final int value;
    
    public TreeNode(TreeNode left, TreeNode right, int value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }
}
```

---
## Encapsulation wisdom
Use immutable (**final**) where possible  
  
*btw why?*

---
## Interface and Abstract class
1. Gradle howto
1. Classes and objects
1. Comparing objects
1. Inheritance
1. **[Interface and abstract class]**
1. Practice 1
1. Class Object
1. Enum
1. Java OOP Style
1. Practice 2


---
## `interface` definition

```java
interface Storable {
    void saveTo(File file); 
}
```


---
## `interface` usage

```java
class Message implements Storable {
    private String content;
    
    @Override
    public void saveTo(File file) {
        // some stuff to save Message to file
    }  
    
    // ...
} 
```

```java
Storable smthToSave = new Message("Perfect content");
smthToSave.saveTo(new File("path to file"));

assertTrue(smthToSave instanceOf Message); // <-- OK
assertTrue(smthToSave instanceOf Storable); // <-- OK
```

---
## Single class - multiple interfaces

```java
class Message implements Storable, Sendable, Readable {
}
```


---
## Interface inheritance

```java
interface FaultTolerantStorable extends Storable, Serializable {
    void handleStoreErrors();
    
    default boolean checkStored(File file) {
        return file != null && file.exists();
    }
    
}
```

---
## `abstract` class
```java
public abstract class AbstractHuman {
    protected String name;
    public abstract String sayHi();
}

public class Englishman extends AbstractHuman {
    @Override
    public String sayHi() {
        return "Hi, I'm" + name;
    }
}
```


---
## abstract class vs interface

|                   | Interface                 | Abstract class                |
|:----------------- |:--------------------------| :-----------------------------|
| Inheritance       | implement many            | extend one                    |
| Fields            | public static only        | no limits                     |
| Methods           | public / public static    | no abstract private methods   |
| Constructors      | no constructors           | no limits                     |

---
## Practice 1
QuantTree

---
---
## Class Object
1. Gradle howto
1. Classes and objects
1. Comparing objects
1. Inheritance
1. Interface and abstract class
1. Practice 1
1. **[Class Object]**
1. Enum
1. Java OOP Style
1. Practice 2

---
## Methods inside `Object`
```java
class Object {
    public String toString() {/**/}
    public boolean equals(Object obj) {/**/}
    public native int hashCode();
    public final native Class<?> getClass();
    protected native Object clone() throws CloneNotSupportedException;
    protected void finalize() throws Throwable { }
    //...
}
```
---
## toString()
```java
class Message {
    private String content;
    
    @Override
    public String toString() {
        return content;
    }
}
```

---
## Two ways to compare objects
1. **==**  
Compares that references point to the same object in memory  
1. **equals()**  
Custom object equivalence check (by default works as **==**)  

---
## equals()
```java
public class TreeNode {
    private TreeNode left;
    private TreeNode right;

    private int value;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeNode node = (TreeNode) o;
        return ((TreeNode) o).value == this.value;
    }
}
```
---
## hashCode()
Returns hash code of an object
Must be consistent with equals()
Does it change?
What are the requirements for hash code?

---
## getClass()
Reflection allows you to get Class/method/field metadata and analyze it in compile and runtime  
check java.lang.Class

---
## clone()
Allows you to implement custom copy logic.  
Is rearly used in real applications  
Why?  
Do you know any other options?  

---
## ~~finalize~~()
**Deprecated**  
Is called before object is deleted 
**Do not rely on this method in any practical application**

---
## Enum
1. Gradle howto
1. Classes and objects
1. Comparing objects
1. Inheritance
1. Interface and abstract class
1. Practice 1
1. Class Object
1. **[Enum]**
1. Java OOP Style
1. Practice 2

---
## Enum
```java
enum Gender {
    Male,
    Female,
    Other    
}
```

No inheritance for enums.

Interfaces are allowed.

---
## Java OOP Style
1. Gradle howto
1. Classes and objects
1. Comparing objects
1. Inheritance
1. Interface and abstract class
1. Practice 1
1. Class Object
1. Enum
1. **[Java OOP Style]**
1. Practice 2

---
## Java OOP Style
- Keep it simple
- Naming matters
- Composition over inheritance

---
## Java OOP Style
1. Gradle howto
1. Classes and objects
1. Comparing objects
1. Inheritance
1. Interface and abstract class
1. Practice 1
1. Class Object
1. Enum
1. Java OOP Style
1. **[Practice 2]**

---
## Practice 2
IterableTree

---
## TIL
1. class "Object" is root of all java Classes
1. Java do not have multiple inheritance
1. Compare objects using equals(), primitives with ==
1. Keep equals(), hashCode() and compareTo() consistent
1. Use single public class within file where possible
1. null is dangerous, checking for null is important
1. When making OOP - Keep it simple
