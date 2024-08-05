# Spring Boot 3 weather-api Project with Spring Security and API-Docs

This project is a SpringBoot application and an HTTP REST API that reads a set of XML event files, extracts a set of
elements (fields), stores them into DB, filters the events based on a set of criteria, and
reports the events in JSON Format.

# Problem Statement
Trade Reporting Engine
Description:
Create a Java program (SpringBoot) that reads a set of XML event files, extracts a set of
elements (fields), stores them into DB, filters the events based on a set of criteria, and
reports the events in JSON Format.
The event.xml files are included in the email and instructions. When reading the event XML
files, keep the Java code simple, consider using the following XML parser and Xpath reader
included in the JDK: javax.xml.parsers.DocumentBuilder, javax.xml.xpath.Xpath. Once the
information is read from XML. We need to store them in DB via JPA. And then we query the
information based on the following criteria and return them as the HTTP response. During
the design, we need to consider how to extend or add more criteria later without impacting
the existing filters.
The following XML elements should be used for the filter criteria and then only these fields
should be included in the response.
EMU_BANK,LEFT_BANK,100.0,AUD
Xml elements (fields) to use Format: xpath expression from event file =&gt; column header
name
//buyerPartyReference/@href =&gt; buyer_party
//sellerPartyReference/@href =&gt; seller_party
//paymentAmount/amount =&gt; premium_amount
//paymentAmount/currency =&gt; premium_currency
Filter Criteria Only report events to JSON response if the following 3 criteria are true:
 (The seller_party is EMU_BANK and the premium_currency is AUD) or (the seller_party
is BISON_BANK and the premium_currency is USD)
 The seller_party and buyer_party must not be anagrams Only events that match all
criteria should be reported.

1. new API is needed as the triggering point.
2. When implementing this topic, we would better to consider the scalability and
   maintainability when the business wants to change the condition/logic of extracting events

# General Requirements:
1. An introduction to your design and implementation, no more than one page.
2. Clear and concise instructions about how to execute the solution.
3. Upload the source code into public repo or email them with .zip file.
4. Use/follow proper design patterns and class structure.
5. Demonstrate the skills of writing different types of testcases/TDD Discipline.
6. Feel free to use any third-party library.
7. Be careful about the license and make sure the resulting code demonstrates good
   OOP skill.
8. Explain any assumptions or trade-offs you have made.
9. Only need to choose one topic to implement.

## Prerequisites

- Java 17 or higher
- Maven 3 or higher

## Getting Started

1. Clone this repository.
   ```
   git clone https://github.com/shreyaspatil1008/trade-reporting-engine
   
   ```

2. Navigate to the project folder.
    ```
   cd weather-api
   ```

3. Build the project.
    ```
   mvn clean install
   ```

4. Run the project.
    ```
   mvn spring-boot:run
   ```

5. Access the API-Docs at [http://localhost:8080/api-docs].
6. Input File Location is: src/main/resources/files/eventXML/input
7. Rules are in the file src/main/resources/rules.csv

   Sample of Rules:

RULE_NUMBER,FIELD,VALUE,PRIMARY_CONDITION,SECONDARY_CONDITION,IS_APPLICABLE,IS_CUSTOM
1,seller_party,EMU_BANK,AND,OR,YES,NO
1,premium_currency,AUD,AND,OR,YES,NO
2,seller_party,BISON_BANK,AND,OR,YES,NO
2,premium_currency,USD,AND,OR,YES,NO
3,seller_party,ANAGRAM,buyer_party,AND,YES,YES


More rules can be added dynamically.
   
  

## Features

- Spring Security for authentication and authorization.
- Swagger UI for API documentation using OpenAPI 3.

## Usage

1. Run the application.
2. Access the Swagger API-Docs at [http://localhost:8080/api-docs].
3. Sample cURL to test:
   curl --location 'http://localhost:8080/api/events' \
   --header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
   --header 'Cookie: JSESSIONID=CC8FB1CD81F1A6669A98AF0D54749066'
