# Getting Started: Scheduling Tasks

What you'll build
-----------------

This guide walks you through the steps for scheduling tasks with Spring.


What you'll need
----------------

 - About 15 minutes
 - {!include#prereq-editor-jdk-buildtools}

## {!include#how-to-complete-this-guide}


<a name="scratch"></a>
Set up the project
----------------------

{!include#build-system-intro}

{!include#create-directory-structure-hello}

### Create a Maven pom

    {!include:complete/pom.xml}

{!include#bootstrap-starter-pom-disclaimer}

<a name="initial"></a>
Create a scheduled task
-------------------------
Now that you've set up your project, you can create a scheduled task.


    {!include:complete/src/main/java/hello/ScheduledTasks.java}

The key components that make this code perform scheduled tasks are the `@EnableScheduling` and `@Scheduled` annotations. 

`@EnableScheduling` ensures that a background task executor is created. Without it, nothing gets scheduled. 

You use `@Scheduled` to configure when a particular method is run.
> **Note:** This example uses `fixedRate`, which specifies the interval between method invocations measured from the start time of each invocation. There are [other options](http://static.springsource.org/spring/docs/3.2.2.RELEASE/spring-framework-reference/html/scheduling.html#scheduling-annotation-support-scheduled), like `fixedDelay`, which specifies the interval between invocations measured from the completion of the task. You can also [use `@Scheduled(cron=". . .")` expressions for more sophisticated task scheduling](http://static.springsource.org/spring/docs/3.2.x/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html).

Make the application executable
-------------------------------

Although scheduled tasks can be embedded in web apps and WAR files, the simpler approach demonstrated below creates a standalone application. You package everything in a single, executable JAR file, driven by a good old Java `main()` method.

### Create a main class

Here you create a new `SpringApplication` and run it with the `ScheduledTasks` you defined earlier. This action creates a task executor and allows tasks to be scheduled."

    {!include:complete/src/main/java/hello/Application.java}

### {!include#build-an-executable-jar}


Run the service
-------------------------------------

Run your service with `java -jar` at the command line:

    java -jar target/gs-scheduling-tasks-0.1.0-SNAPSHOT.jar

Logging output is displayed. You should see your scheduled task fire every 5 seconds:

    [...]
    The time is now 13:10:00
    The time is now 13:10:05
    The time is now 13:10:10
    The time is now 13:10:15

Summary
-------

Congratulations! You created an application with a scheduled task. Heck, the actual code was shorter than the build file! This technique works in any type of application.

[zip]: https://github.com/springframework-meta/gs-scheduling-tasks/archive/master.zip
