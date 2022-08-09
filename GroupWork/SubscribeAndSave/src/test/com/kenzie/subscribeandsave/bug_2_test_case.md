Class: SubscriptionService
subscribe_serviceNotEligible_exceptionOccurs
* Description: Testing for invalid asin.
* GIVEN
    * Valid CustomerId
    * Ineligible SNS ASIN
    * Valid frequency
* WHEN
    1. Subscribe to an ASIN that's not eligible for SNS by calling 'subscribe()' with an ineligible SNS ASIN
* THEN
    * Check for IllegalArgumentException
    * Subscription is displayeds displayed to B07R5QD598, throw exception