Trick-knee  [![Build Status](https://travis-ci.org/jasonklarsen/trick-knee.png?branch=master)](https://travis-ci.org/jasonklarsen/trick-knee)
==========
*"My trick-knee been actin' up -- must be a storm a-comin'"*

The goal of this project is a simple bit of code to give you another 'sense' for your projects. You can think of it like a Build Monitor, but for those places where visual indicators don't make as much sense like the commandline.

This builds a core executable jar that can be integrated into other projects. The jar can also be used inside any other JVM-friendly project.

Usage
-----

As a pre-built Java jar:

    java -jar trick-knee-0.1.jar some.conf

The `some.conf` file can be a path to a configuration file in JSON (or technically anything supported by TypeSafe's [Config library](https://github.com/typesafehub/config)). 

A simple (self-)example would be:

    {
      twinges: [
        { type: "travis-ci", username: "jasonklarsen", repository: "trick-knee" }
      ]
    }

This sets up a "twinge" of the knee to be sensitive to the Travis-CI integration for this project. Other types of twinges are listed below.

Supported Twinges Types
----

 * [Travis CI](https://travis-ci.org) - Type is `travis-ci`, and the currently supported configuration parameters are `username` and `repository`. This checks the top level status.
 * *more to be developed!*

Supported Integration Patterns
----

 * As a standalone jar, see above example.
 * *More patterns to be developed!*

TODO
----

- Explanations of how to use in a JVM project
- Links to derived projects (sbt plugin?)