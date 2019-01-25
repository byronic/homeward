## message-passing

### Messaging support for Java based game engine

100% test covered!

No Javadocs yet, so here's the quick-and-dirty:

You can use DefaultMessage and DefaultMessageController, or you can 
implement Message and MessageController yourself.

Note that DefaultMessage has a Builder that makes it easy to get
what you need out of a message. The DefaultMessage is a wrapper around
Map<String, Object> allowing you to _retrieve_ items in a message but
not _alter its contents_.

The DefaultMessageController wraps a Map<String, Set<Subscriber>> 
allowing subscribers to be _notified_ when new messages arrive on a given
String _topic_. The messaging system is topic based, allowing Subscribers
to decide what topics they care about (as well as how they handle them).

To get inserted into the message passing API, your downstream classes
will need to implement the Subscriber class.
