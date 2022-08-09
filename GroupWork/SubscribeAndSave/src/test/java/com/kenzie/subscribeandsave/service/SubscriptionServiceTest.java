package com.kenzie.subscribeandsave.service;

import com.kenzie.subscribeandsave.App;
import com.kenzie.subscribeandsave.types.Subscription;
import com.kenzie.subscribeandsave.util.SubscriptionRestorer;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class SubscriptionServiceTest {
    private static final String ASIN = "B01BMDAVIY";
    private static final String CUSTOMER_ID = "amzn1.account.AEZI3A0633629PUR8GGGS9ZSC3DO";
    private static final String SUBSCRIPTION_ID = "81a9792e-9b4c-4090-aac8-28e733ac2f54";

    private SubscriptionService classUnderTest;

    /**
     * The entry point, which results in calls to all test methods.
     *
     * @param args Command line arguments (ignored).
     */
    public static void main(String[] args) {
        SubscriptionServiceTest tester = new SubscriptionServiceTest();

        // clean up subscriptions before/after running runAllTests(), for when tests are invoked via main(),
        // rather than through a build
        tester.restoreSubscriptions();
        tester.runAllTests();
        tester.restoreSubscriptions();
    }

    @Test
    public void runAllTests() {
        classUnderTest = new SubscriptionService(App.getAmazonIdentityService(), App.getSubscriptionDAO(),
                App.getAmazonProductService());
        boolean pass = true;

        pass = subscribe_newSubscription_subscriptionReturned();
        pass = getSubscription_existingSubscription_subscriptionReturned() && pass;
        pass = subscribe_unknownCustomer_exceptionOccurs() && pass;
        pass = subscribe_unknownAsin_ExceptionOccurs() && pass;
        pass = subscribe_serviceNotEligible_ExceptionOccurs() && pass;
        pass = CustomerId_Asin_Swapped() && pass;

        if (!pass) {
            String errorMessage = "\n/!\\ /!\\ /!\\ The SubscriptionService tests failed. Test aborted. /!\\ /!\\ /!\\";
            System.out.println(errorMessage);
            fail(errorMessage);
        } else {
            System.out.println("The SubscriptionService tests passed!");
        }
    }

    public boolean getSubscription_existingSubscription_subscriptionReturned() {
        // GIVEN - a valid subscriptionId
        String subscriptionId = SUBSCRIPTION_ID;

        // WHEN - get the corresponding subscription
        Subscription result = classUnderTest.getSubscription(subscriptionId);

        // THEN - a subscription object is returned, with a matching id
        if (result == null) {
            System.out.println("   FAIL: Getting a subscription for a valid if should return the subscription.");
            return false;
        }
        if (!subscriptionId.equals(result.getId())) {
            System.out.println("   FAIL: Subscription returned when getting subscription by id has mismatching id " +
                    "value");
            return false;
        }

        System.out.println("  PASS: Getting a subscription for a valid id succeeded.");
        return true;
    }

    public boolean subscribe_newSubscription_subscriptionReturned() {
        // GIVEN - a customerId to make a subscription for, asin to subscribe to, and the frequency to receive
        // subscription
        String customerId = CUSTOMER_ID;
        String asin = ASIN;
        int frequency = 1;

        // WHEN - create a new subscription
        Subscription result = classUnderTest.subscribe(customerId, asin, frequency);

        // THEN a subscription should be returned and the id field should be populated
        if (result == null) {
            System.out.println("   FAIL: Creating subscription should return the subscription.");
            return false;
        }
        if (StringUtils.isBlank(result.getId())) {
            System.out.println("   FAIL: Creating subscription should return a subscription with a populated id " +
                    "field.");
            return false;
        }

        System.out.println("  PASS: Creating a new subscription succeeded.");
        return true;
    }

    public boolean subscribe_unknownCustomer_exceptionOccurs() {
        // GIVEN - an invalid customerId to make a subscription for
        String customerId = "12345678";
        String asin = ASIN;
        int frequency = 1;

        // WHEN/THEN - try to create a new subscription, catch IllegalArgumentException
        try {
            Subscription result = classUnderTest.subscribe(customerId, asin, frequency);
        } catch (IllegalArgumentException w) {
            System.out.println("  PASS: Cannot subscribe with invalid customerId.");
            return true;
        }

        System.out.println("   FAIL: An exception should have occurred when subscribing invalid customer.");
        return false;
    }

    //bug 1
    public boolean subscribe_unknownAsin_ExceptionOccurs() {
        //GIVEN invalid ASIN
        String customerID = CUSTOMER_ID;
        String asin = "B00306IEJB";
        int frequency = 1;
        //WHEN/THEN
        assertThrows(IllegalArgumentException.class, () -> classUnderTest.subscribe(CUSTOMER_ID, asin, frequency), "Service not eligible for subscribe and save.");
        return true;
    }

    //bug 2
    public boolean subscribe_serviceNotEligible_ExceptionOccurs() {
        // GIVEN valid customer ID, ineligible product = B07R5QD598, valid frequency
        String customerID = CUSTOMER_ID;
        String asin = "B07R5QD598";
        int frequency = 1;

        // WHEN/THEN subscribe to B07R5QD598, throw exception
        assertThrows(IllegalArgumentException.class, () -> classUnderTest.subscribe(CUSTOMER_ID, asin, frequency), "Service not eligible for subscribe and save.");
        return true;
    }


    // PARTICIPANTS: Rename and fill in the example test below after fixing Bug 3 - refactor as needed
    public boolean CustomerId_Asin_Swapped() {
        // GIVEN
        String subscriptionId = "1dcf9a20-d1c6-41e3-8efd-caa542e231c1";
        String asin = "B00ILBUEVK";
        String customerId = "amzn1.account.AEZI3A063427738YROOFT8WCXKDE";
        int frequency = 1;

        // WHEN
        //Query shows customerId and asin are switched places
        if (classUnderTest.getSubscription(subscriptionId).getAsin().equals(customerId) && classUnderTest.getSubscription(subscriptionId).getCustomerId().equals(asin)) {
            System.out.println("  PASS: CustomerId and Asin are switched places.");
            return true;
        }
        // THEN
        //  customerId and asin are switched places
        System.out.println("   FAIL: CustomerId and Asin are not switched places.");
        return false;
    }


    @BeforeEach
    @AfterEach
    public void restoreSubscriptions() {
        SubscriptionRestorer.restoreSubscriptions();
    }
}
