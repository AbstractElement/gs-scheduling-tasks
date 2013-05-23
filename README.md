<!-- See expanded [macro:...] values at https://github.com/springframework-meta/springframework.org/tree/master/doc/gs-macros.md -->

# Getting Started: Scheduling Tasks

What you'll build
-----------------

This guide will walk you through the steps needed to scheduled some tasks using Spring.

What you'll need
----------------

 - About 15 minutes
 - A favorite text editor or IDE
 - [JDK 6][jdk] or better
 - [Maven 3.0][mvn] or later

[macro:how-to-complete-this-guide]

<a name="scratch"></a>
Set up the project
----------------------

[macro:build-system-intro]

Create the directory structure
------------------------------

In a project directory of your choosing, create the following subdirectory structure; for example, with `mkdir -p src/main/java/hello` on *nix systems:

    └── src
        └── main
            └── java
                └── hello

Create a Maven POM
------------------

[macro:maven-project-setup-options]

`pom.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.springframework</groupId>
    <artifactId>gs-scheduling-tasks</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.bootstrap</groupId>
        <artifactId>spring-bootstrap-starters</artifactId>
        <version>0.5.0.BUILD-SNAPSHOT</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.bootstrap</groupId>
            <artifactId>spring-bootstrap-starter</artifactId>
            <version>0.5.0.BUILD-SNAPSHOT</version>
        </dependency>
    </dependencies>
    
    <!-- TODO: remove once bootstrap goes GA -->
    <repositories>
        <repository>
            <id>spring-snapshots</id>
            <name>Spring Snapshots</name>
            <url>http://repo.springsource.org/snapshot</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>spring-snapshots</id>
            <name>Spring Snapshots</name>
            <url>http://repo.springsource.org/snapshot</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </pluginRepository>
    </pluginRepositories>
</project>
```

[macro:bootstrap-starter-pom-disclaimer]

<a name="initial"></a>
Create a scheduled task
-------------------------
With our project set up, let's create a scheduled task.


`src/main/java/hello/ScheduledTasks.java`

```java
package hello;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }
}
```

The two key components that makes our code perform scheduled tasks are the `@EnableScheduling` and `@Scheduled` annotations. 

`@EnableScheduling` ensures that a background task executor is created. Without it, nothing will get scheduled. 

`@Scheduled` is used to configure when a particular method is run.
- **Note:** This example uses `fixedRate`, which measures the time interval at the beginning of the task. There are [other options](http://static.springsource.org/spring/docs/3.2.2.RELEASE/spring-framework-reference/html/scheduling.html#scheduling-annotation-support-scheduled), like `fixedDelay`, which measures the time interval starting at the end of the task. It's also possible to [schedule things using `@Scheduled(cron=". . .")` expressions](http://static.springsource.org/spring/docs/3.2.x/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html) for more sophisticated scheduling.

Creating an executable main class
---------------------------------

The only left to do is create a runnable class!

`src/main/java/hello/Application.java`

```java
package hello;

import org.springframework.bootstrap.SpringApplication;

public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ScheduledTasks.class);
    }
}
```

We are creating a new Spring application context and feeding the class with our scheduled task. This will cause a task executor thread to start up and begin processing automatically scheduled tasks until we terminate the process.


Build an executable JAR
--------------------------

Add the following to your `pom.xml` file (keeping any existing properties or plugins intact):

`pom.xml`
```xml
    <properties>
        <start-class>hello.Application</start-class>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

The `start-class` property tells Maven to create a `META-INF/MANIFEST.MF` file with a `Main-Class: hello.Application` entry. This entry enables you to run the jar with `java -jar`.

The [Maven Shade plugin][maven-shade-plugin] extracts classes from all the jars on the classpath and builds a single "über-jar", which makes it more convenient to execute and transport your service.

Now run the following to produce a single executable JAR file containing all necessary dependency classes and resources:

    mvn package

Running the service
-------------------------------------

Run your service with `java -jar` at the command line:

    java -jar target/gs-scheduling-tasks-1.0-SNAPSHOT.jar

Logging output is displayed and you begin seeing your scheduled task fire every 5 seconds as expected:

    [...]
    The time is now 13:10:00
    The time is now 13:10:05
    The time is now 13:10:10
    The time is now 13:10:15

Summary
-------

Congratulations! You have created an application with scheduled tasks. Heck, the actual code was shorter than the build file! Suffice it to say, this technique works inside any type of application, web or command-line.

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[zip]: https://github.com/springframework-meta/gs-scheduling-tasks/archive/master.zip
[mvn]: http://maven.apache.org/download.cgi
