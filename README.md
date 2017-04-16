# UMKCRoobots

Project Submission for UMKC Hackathon Spring 2017

## Team Members

* Raj Anantharaman (rah59@mail.umkc.edu)
* Bill Capps (bjctgc@mail.umkc.edu)
* Adam Carter (arcbpc@mail.umkc.edu)
* Amy Lin (clthf@mail.umkc.edu)

## Project Overview

For our project, we chose the CoxAutomotive/VinSolutions Problem, which presented the following use case:

```
Natural Language Processing (NLP) has many uses in both academia and the business world. Parsing the user messages/mails could be one of the applications. We would like you to develop an appointment detection system just like the one you see in smartphones from GMAIL and Office 365. The goal is to allow the user to type a message to another user. When that user opens the message content via a browser, the system should detect texts that infer a meeting invitation.
```

This problem statement defined the following tasks:
* The application should allow users to type a message to a different user.
* The application should allow users to read the messages they have received from other users.
* The application should highlight, via hyperlink, any text you have detected as a possible appointment.
* When users click on that hyperlink, it should display a popup screen that pre-populates with the detected meeting details (When, Who). Do not worry about location (Where).

## Our Solution

Our solution comprises the following elements:

* MongoDB for data storage
* REST Service written in Java using the Spring Boot Framework.
* NLP Date Parsing in Java using the Natty Engine  (We should mention the python stuff here too)
* Web Client using HTML5, CSS, Javascript, and whatever else we are using

