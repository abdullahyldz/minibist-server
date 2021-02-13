# minibist-server is the Java server of [Android application](https://github.com/abdullahyldz/minibist) 

## Technical details
* Server is written in Java, managed by Maven
* Deployed to Amazon Ec2 micro instance
* Reentrantlock is used for concurrent file operations

## How to configure server?
Go to main function App.java under `src/main/java/org` and edit PORT as you wish

```
public static Integer PORT = 8080;
public static Integer PORT = {YOUR_PORT};
```

## How to run server on your local machine?

* Change your folder to `org.minibist`
* Create JAR package
* Run JAR package locally

```
cd .\org.minibist\
mvn clean compile assembly:single
java -jar .\target\org.minibist-1.0-SNAPSHOT.jar
```
