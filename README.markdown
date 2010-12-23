# Scale Monitor

This project provides a simple application that will monitor and
record weight data from electronic scales connected to serial ports
(including USB-to-serial-port adapters). It is written in Java and
uses the Swing toolkit to provide a friendly GUI interface.

## Requirements

This project is pretty self-contained. You will need to have Java
installed as well as [Apache Ant](http://ant.apache.org/).

Serial port support is provided by the
[RXTX](http://users.frii.com/jarvi/rxtx/) library. Presently Windows,
Mac OS X and Linux are all supported.

This project uses [Launch4J](http://launch4j.sourceforge.net/) to
create an executable that will start the application on Windows. It
also uses [IzPack](http://izpack.org/) to create a cross-platform
installer.

You will need to install both of these packages and then set the
appropriate paths in the "build.properties" file. A sample properties
file is provided, simply copy this file and customize its values.

## Building the Project

Building the project is easy. Once you have installed the
pre-requisites and customized the "build.properties" file, run Ant
from the root directory. All of the other required libraries are
bundled with the project.

I know that checking the library files into the project is a bad idea,
but I haven't had time to set the project up with [Maven
2](http://maven.apache.org/). This is on my to-do list!

## Running the Application

Once you have built the application, all of the interesting bits will
be in the "dist" folder.

* "ScaleMonitor" contains a copy of the Java distribution. This
  includes the main JAR file and all of the required libraries. There
  will also be a ZIP archive with the same contents.

* "scalemonitor-windows" contains the Windows distribution, including
  a Windows launcher. There will also be a ZIP archive with the same
  contents.

* If you are building on Mac OS X, there will be a "ScaleMonitor.app"
  folder that contains the Macintosh distribution. There will also be
  a ZIP archive with the same contents.

The Mac OS X distribution is only built when you're building on
Macintosh. The build process copies a stub file for launching the
application that is only present on Macintosh.

If you are running the application from the Java distribution you will
need to set the library path.

    java -Djava.library.path=resources -jar ScaleMonitor.jar

The launchers on Windows and Macintosh set the library path for you
already.

## Other Notes

I used IntelliJ IDEA 7 to design the Swing forms. This worked great,
but to make any changes to the forms you'll need to continue to use
IDEA (or port the forms to another tool, i.e. NetBeans). This isn't as
bad as it sounds, there is a ["Community
Edition"](http://www.jetbrains.com/idea/download/) of IDEA
available. I will need to port these forms, it's unlikely they will
cleanly build under IDEA 10 (but you never know).

## Fork at Will!

If you find this project useful and make any changes, please fork and
send me a pull request! I wrote this application for a good friend of
mine that had a bunch of scales attached to a proprietary interface
that had ceased functioning. She uses the AND EW 300A and that's the
only supported scale right now. I'm sure this application would work
with other serial port scales, but right now this is the only one she
uses, so it's the only one I have available for testing.
