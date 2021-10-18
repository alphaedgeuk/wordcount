# Word Count Example Program
Author: Robert Brewer 
October 2021

Hello! Welcome to my word counter example program!

The project is divided into the following Gradle sub-modules:
* lib - This is the core WordCounter jar
* config - This is some common config e.g. location of chronicles
* common - This defines the common Chronicle message structure
* micro-service - This wraps the WordCounter jar in an event sourced chronicle micro-service
* web-service - This is the REST api to add words and query the count

The web-service submits commands via the command Chronicle queue. The micro-service reads the command, performs
the operation and emits a single event. A command always results in an event whether the operation is successful or
not.

When the micro-service starts up it reads all commands in the command queue to rebuild its state. Therefore, the 
count of words will persist until the queues found under ./DATA are deleted or moved.

To test out the web-service & micro-service interaction simply start the applications by running their respective
main methods and submit REST commands via curl on the command line as described in the examples below.


### Example: Adding a word

    rbrewer@wordcount$ curl -i -X POST localhost:7000/add/foo
    HTTP/1.1 200 OK
    Date: Mon, 18 Oct 2021 16:34:33 GMT
    Content-Type: text/plain
    Content-Length: 65
    
    !com.alphaedge.wordcount.common.events.WordAdded {
      word: foo
    }

### Example: Querying a word count

    rbrewer@wordcount$ curl -i -X GET localhost:7000/get/foo
    HTTP/1.1 200 OK
    Date: Mon, 18 Oct 2021 16:35:15 GMT
    Content-Type: text/plain
    Content-Length: 84
    
    !com.alphaedge.wordcount.common.events.WordCountQueried {
      count: 3,
      word: foo
    }

### Example: Error on adding word with non-alpha character

    rbrewer@wordcount$ curl -i -X POST localhost:7000/add/hello!
    HTTP/1.1 200 OK
    Date: Mon, 18 Oct 2021 16:44:03 GMT
    Content-Type: text/plain
    Content-Length: 100
    
    !com.alphaedge.wordcount.common.events.WordAddError {
      error: Unable to add word,
      word: hello!
    }
