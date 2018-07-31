# SafeMap
Experiments in producing Maps and other collections that are more typesafe than those in the Collections package.

This is currently in the earliest of stages, and will be a work in progress for a while. The purpose of this is to create a more type-safe version of the Java collecitions. Like most things in Java, the collections are very type-safe, but they're not as type-safe as they could be, for historical reasons. For example, the get() method in Map takes an Object as a key, instead of the generic key type. This means if I write a line of code that requests an object of the wrong type, instead of getting a compiler error, I get a run-time bug that doesn't even throw an exception. Granted, this doesn't happen very often, but I just got bit by this bug, so I'm thinking about ways to avoid it. This should be a compile time error. 

Why, you might ask, am I trying to solve such a minor problem? For two reasons. First, the Java Collecitons are some of their most important and widely used classes. Second is an observation I made a few years ago about the reliability of software: The difference between unreliable code and reliable code is often the difference between *usually* and *always*. 

Here's what I mean. Many software developers will be content with a method that will usually work. They don't worry about the rare special cases. This is understandable, but it's often not a good idea. Many times I've seen code that will usually work, but a few trivial changes will turn it into code that will always work. So they coder didn't saving much time by settling for *usually*. 

A software application is a huge assembly of many small pieces of code. If all of the *usually* work, then the overall reliability might not be very good at all. If you have 10000 elements, each of which has a 1 in a thousand chance of failing on any given day, then you'll actually see ten failures a day. So *usually* shouldn't be good enough, when *always* is just a few more lines of code.

Here's an example:
