Class: SubscriptionService
subscribe_unknownAsin_exceptionOccurs
* Description: Testing for invalid asin.
* GIVEN 
* Valid CustomerId 
* Invalid ASIN Valid frequency
* WHEN 
* Subscribe to an Invalid ASIN by calling 'subscribe()' with an invalid ASIN.
* THEN 
* Check for IllegalArgumentException
    