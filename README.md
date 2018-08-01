# SafeMap
Experiments in producing Maps and other collections that are more typesafe than those in the Collections package.

This is currently in the earliest of stages, and will be a work in progress for a while. The purpose of this is to create a more type-safe version of the Java collecitions. Like most things in Java, the collections are very type-safe, but they're not as type-safe as they could be, for historical reasons. For example, the get() method in Map takes an Object as a key, instead of the generic key type. This means if I write a line of code that requests an object of the wrong type, instead of getting a compiler error, I get a run-time bug that doesn't even throw an exception. Granted, this doesn't happen very often, but I just got bit by this bug, so I'm thinking about ways to avoid it. This should be a compile time error. 

Why, you might ask, am I trying to solve such a minor problem? For two reasons. First, the Java Collecitons are some of their most important and widely used classes. Second is an observation I made a few years ago about the reliability of software: The difference between unreliable code and reliable code is often the difference between *usually* and *always*. 

Here's what I mean. Many software developers will be content with a method that will usually work. They don't worry about the rare special cases. This is understandable, but it's often not a good idea. Many times I've seen code that will usually work, but a few trivial changes will turn it into code that will always work. So they coder didn't saving much time by settling for *usually*. 

A software application is a huge assembly of many small pieces of code. If all of them *usually* work, then the overall reliability might not be very good at all. If you have 10000 elements, each of which has a 1 in a thousand chance of failing on any given day, then you'll actually see ten failures a day. So *usually* shouldn't be good enough, when *always* is just a few more lines of code.

How can we distinguish between *usually* and *always*? One way is to be aware of what I call the *Unnecessary Assumption Fallacy*.

## The Unnecessary Assumption Fallacy

Never make an assumption that you don't need to make. In writing software, we often make simplifying assumptions. If these assumptions are correct, we're fine, but if they're not, we have inadvertently introduced a bug into our code. And in my exprience, many of the assumptions we make are completely unnecessary. Never make an assumption that you don't need to make.

Here's an example.

Here's a method to find the lowest and highest values in a collection of integers. These number represent people's ages.

    private static Range determineRange(int[] data) {
      int ageMin = 110;
      int ageMax = 0;
    
      for (int i: data) {
        if (i < ageMin) {
          ageMin = i;
        }
        if (i > ageMax) {
          ageMax = i;
        }
      }
      return new Range(ageMin, ageMax);
    }
    
We know enough about the data to know that they should never be as high as 110, or lower than 0, so we set those as our limits. This is fine as long as our assumption is correct. For example, if we're looking at people's ages, we can reasonably set a lower limit of zero and an upper limit of about 120, an age very few people reach.
    
But do we really know this? Let's put aside the fact that there are very rare records of people living beyond 110. The real problem is that we're assuming our data is valid. There could be a bug in the code that calculates people's ages, and we could get wildly unreasonable values. This method to determine ranges is incapable of alerting us to this bug. Do we really need to make any assumptions about the data? Why not write the method like this?

    private static Range determineRange(int[] data) {
      int ageMin = Integer.MAX_VALUE;
      int ageMax = Integer.MIN_VALUE;
    
      for (int i: data) {
        if (i < ageMin) {
          ageMin = i;
        }
        if (i > ageMax) {
          ageMax = i;
        }
      }
      return new Range(ageMin, ageMax);
    }

Now it can't fail. If our data is invalid, this will let us know. It will work with any collection of integer values. The assumptions we made about people's ages are completely unnecessary. They offer us no advantage. And yeah, those assumptions are probably right, and the data will nearly always be valid. But do we gain anything by making those assumptions? We've gone from a method that will *usually* work to one that will *always* work, and we did so effortlessly. And yes, I've seen this issue come up in an actual project, where birth years were assumed to have 4 digits, but were being entered with 2 digits, so everyone was about 2000 years old. *Never make an assumption that you don't need to make.*
