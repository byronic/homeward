package com.amalgamated_incorporated.messaging.api.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.amalgamated_incorporated.messaging.impl.DefaultMessage;
import com.amalgamated_incorporated.messaging.impl.DefaultMessageController;
import com.amalgamated_incorporated.messaging.impl.DefaultSubscriber;

public class TestMessagePassing {

  private DefaultSubscriber testSubscriber;
  private DefaultMessageController testController;

  @Before
  public void before() {
    testController = new DefaultMessageController();
    testSubscriber = new DefaultSubscriber(testController);
  }

  @Test
  public void testSubscriberHasNoContent() {
    assertEquals("Subscriber should have no message content", "", testSubscriber.getMessageContents());
    assertFalse("Subscriber should not have received message", testSubscriber.hasReceivedMessage());
    assertEquals("Subscriber should have no integer value", 0, testSubscriber.getReceivedInt());
  }

  @Test
  public void testSendMessageAndReceive() {
    testSubscriber.subscribe();
    testController.send(DefaultSubscriber.EXAMPLE_TOPIC,
        new DefaultMessage.Builder().set(DefaultSubscriber.EXAMPLE_KEY, null).set(DefaultSubscriber.EXAMPLE_INT, 3)
            .set(DefaultSubscriber.EXAMPLE_CONTENTS, DefaultSubscriber.EXAMPLE_CONTENTS).build());
    assertEquals("Subscriber should have message content", DefaultSubscriber.EXAMPLE_CONTENTS,
        testSubscriber.getMessageContents());
    assertTrue("Subscriber should have received message", testSubscriber.hasReceivedMessage());
    assertEquals("Subscriber should have integer value", 3, testSubscriber.getReceivedInt());
  }

  @Test
  public void testDuplicate() {
    testSubscriber.subscribe();
    DefaultSubscriber test2 = new DefaultSubscriber(testController);
    test2.subscribe();
    testController.send(DefaultSubscriber.EXAMPLE_TOPIC,
        new DefaultMessage.Builder().set(DefaultSubscriber.EXAMPLE_KEY, null).set(DefaultSubscriber.EXAMPLE_INT, 3)
            .set(DefaultSubscriber.EXAMPLE_CONTENTS, DefaultSubscriber.EXAMPLE_CONTENTS).build());
    assertEquals("Subscriber should have message content", DefaultSubscriber.EXAMPLE_CONTENTS,
        testSubscriber.getMessageContents());
    assertTrue("Subscriber should have received message", testSubscriber.hasReceivedMessage());
    assertEquals("Subscriber should have integer value", 3, testSubscriber.getReceivedInt());
    assertEquals("Second subscriber should have message content", DefaultSubscriber.EXAMPLE_CONTENTS,
        test2.getMessageContents());
    assertTrue("Second subscriber should have received message", test2.hasReceivedMessage());
    assertEquals("Second subscriber should have integer value", 3, test2.getReceivedInt());
  }

  @Test
  public void testUnsubscribe() {
    testSubscriber.subscribe();
    testSubscriber.unsubscribe();
    testController.send(DefaultSubscriber.EXAMPLE_TOPIC,
        new DefaultMessage.Builder().set(DefaultSubscriber.EXAMPLE_KEY, null).set(DefaultSubscriber.EXAMPLE_INT, 3)
            .set(DefaultSubscriber.EXAMPLE_CONTENTS, DefaultSubscriber.EXAMPLE_CONTENTS).build());
    assertEquals("Subscriber should have no message content", "", testSubscriber.getMessageContents());
    assertFalse("Subscriber should not have received message", testSubscriber.hasReceivedMessage());
    assertEquals("Subscriber should have no integer value", 0, testSubscriber.getReceivedInt());
  }

  @Test
  public void testCornerCases() {
    // trigger warning messages
    testSubscriber.unsubscribe();
    testController.send("UNUSED", new DefaultMessage.Builder().set("Key", "Value").build());
  }

}
