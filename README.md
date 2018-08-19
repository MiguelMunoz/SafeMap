# SafeMap
Experiments in producing Maps and other collections that are more typesafe than those in the Collections package.

This is currently in the earliest of stages, and will be a work in progress for a while. The purpose of this is to create a more type-safe version of the Java collections. Like most things in Java, the collections are very type-safe, but they're not as type-safe as they could be, for historical reasons. For example, the get() method in Map takes an Object as a key, instead of the generic key type. This means if I write a line of code that requests an object of the wrong type, instead of getting a compiler error, it fails silently by returning null. So I get a run-time bug that doesn't even throw an exception. Granted, this doesn't happen very often, but I just got bit by this bug, so I'm thinking about ways to avoid it. Ideally, this should be a compile time error. 

Why, you might ask, am I trying to solve such a minor problem? For two reasons. First, the Java Collections are some of their most important and widely used classes. Second is what I call the nitpick principle. Basically, the principle is this: Nitpicking is worth it. The rest of this document is an explanation of why nitpicking is worth it, and how you can use it to improve the reliability of your application.

## The Nitpicking Principle
This principle arose out of an observation I made a few years ago about software reliability: The difference between unreliable code and reliable code is often the difference between *usually* and *always*.

Here's what I mean. When I'm maintaining a project, many of the bugs that come in are problems that clearly need to be fixed, but some of them are edge cases. Unusual data that wasn't anticipated gets handled poorly. The software was written to handle the common cases, and ignored the uncommon ones. So their code would *usually* work. The irony is that it was usually very little work to rewrite the code to handle the edge cases well. Every poorly-handled edge case generated an issue report, and passed through several hands as it moved through the bureaucracy, costing the company money. And these edge cases are often very easy to fix once they've been identified. So easy that it would have been worth it for the original developer to aim for *always works* rather than *usually works*.

Allow me to give you a fascinating example of this principle in action. To do so, I'll have to leave the field of software and take you to the field of medicine, where the stakes are much higher than increased maintenance costs. The example comes from what some doctors learned about treating cystic fibrosis. This example is drawn from **The Bell Curve**, an article in the December 6, 2004 issue of *The New Yorker*.

A certain clinic has a much better success rate than other clinics, and the secret of their success was partly due to the extraordinary effort they put into encouraging their patients into taking their medicine every single day, even when they were feeling healthy. Even when they were feeling great. Here's why. A healthy patient's chance of having a good day without medicine is 99.5%. That sounds pretty good. But with the medicine, it goes up to 99.95%. That seems like an insignificant difference. It hardly seems worth it. But that turns out to be a huge difference. There are 365 days in a year. When you multiply those odds through an entire year, the 99.95% comes to an 83% chance of making it through the year, while the 99.5% comes to only 16%. For cystic fibrosis patients, this can be the difference between living to be 30 versus living to be 46. If the patient has a child at age 20, this is the difference between dying when the child is 10 or 26. Some of the patients are reaching their 60s, which used to be unheard of for cystic fibrosis. So the medical staff at this clinic aims for the 99.95%. They aim for higher if there's a way. 99.5% is simply not good enough.

To get back to software, should we aim to write code that will usually work? It sounds reasonable, but it's often not a good idea. Many times I've seen code that will usually work, but a few trivial changes will turn it into code that will *always* work. So the coder didn't saving much time by settling for *usually*.

A software application is a huge assembly of many small pieces of code. If all of them *usually* work, then the overall reliability might not be very good at all. If you have 10000 elements, each of which has a 1 in a thousand chance of failing on any given day, then you'll actually see ten failures a day. So aiming for *usually works* instead of *alwasy works* is like aiming for 99.5% instead of 99.95%. So *usually* shouldn't be good enough, especially when *always* means just a few lines of code. Of course, this is a simplification. But the point is that aiming for 100% reliability is well worth the effort.

How can we distinguish between *usually* and *always*? One way is to be aware of what I call the *Unnecessary Assumption Fallacy*.

## The Unnecessary Assumption Fallacy

Never make an assumption that you don't need to make. In writing software, we often make simplifying assumptions. If these assumptions are *always* correct, we're fine, but if they're not, we have inadvertently introduced a bug into our code. Even if they're *usually* correct. And in my experience, many of the assumptions we make are completely unnecessary. Never make an assumption that you don't need to make.

Here's an example.

Here's a method to find the lowest and highest values in a collection of integers. These number represent people's ages.

    private static Range determineRange(List<Integer> data) {
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
    
We know enough about the data to know that they should never be as high as 110, or lower than 0, so we set those as our limits. This is fine as long as our assumption is correct. For example, if we're looking at people's ages, we can reasonably set a lower limit of zero and an upper limit of about 110, an age very few people reach.
    
But do we really know this? Let's put aside the fact that there are very rare records of people living beyond 110. The real problem is that we're assuming our data is valid. There could be a bug in the code that calculates people's ages, and we could get wildly unreasonable values. This method to determine ranges is incapable of alerting us to this bug. Do we really need to make any assumptions about the data? Instead of setting ageMin and ageMax to 110 and 0, why not set them to Integer.MAX_VALUE and Integer.MIN_VALUE?

This will draw outrageous but silly objections from other developers. They'll think you're assuming that people can live beyond a million years old. But you're not making that assumption. You're not assuming anything. You're not even assuming the data is valid. And more important, you now have a more generic method that will work for any kind of Integer data. The method will for for file sizes as easily as people's ages. 

The point is that, with a minor change, the code can't fail. If our data is invalid, this will let us know. The assumptions we made about people's ages offer us no advantage. And yeah, those assumptions are probably right, and the data will nearly always be valid. But do we gain anything by making those assumptions? 

Also, the change that takes us from *usually* works to *always* works was effortless. And yes, I've seen this issue come up in an actual project, where birth years were assumed to have 2 digits, but were being entered with 4 digits, so everyone was about 2000 years old. *Never make an assumption that you don't need to make.*

And you can avoid pointless arguments about million-year-old people by rewriting the method in a way that makes it clearer that you're not making any assumptions. You can simply set min and max to the first element of the array.

    private static Range determineRange(Iterable<Integer> data) {
      Iterator<Integer> iterator = data.iterator();
      if (!iterator.hasNext()) {
        throw new IllegalStateException("Empty array");
      }
      int min = iterator.next(); // We already called hasNext()
      int max = min;
    
      while (iterator.hasNext()) {
        int i = iterator.next();
        if (i < min) {
          min = i;
        } else if (i > max) {
          max = i;
        }
      }
      return new Range(min, max);
    }
