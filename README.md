# Introduction
This project is part of the assessment for the advertised job position. The main objective was to process pricing data for various accommodations from a given JSON file. The project involves extracting relevant information, performing data cleaning, and presenting the data in a structured and usable format upon a RestFUL http query.

# Project Steps and Implementation
## Data Extraction and Cleaning
The first step in the project involved extracting and cleaning data from two distinct files: `advertiser_100.yaml` and `advertiser_200.json`. Both files contain information about accommodation prices, but each file was provided by a different partner.

Some accommodations are present in both files, indicating that each partner is offering the same accommodation at different prices. Therefore, it is entirely appropriate to display varying prices for the same accommodation from different partners.

Here is an example of an acceptable HTTP response for inquiries about accommodation 17986:
```json
[
    {
        "partnerId": 100,
        "prices": [
            {
                "currency": "EUR",
                "price": 676.67
            }
        ]
    },
    {
        "partnerId": 200,
        "prices": [
            {
                "currency": "EUR",
                "price": 1144.98
            }
        ]
    }
]
```

I observed that some accommodations are repeated from a single partner, each with different prices in the same currency. Without timestamps to distinguish these prices, it appears to be an error. Therefore, I decided to remove these duplicate accommodations to maintain a clean and accurate database.

Some price entries were found to be in incorrect formats or marked as "n/a". These entries need to be standardized so that they can be stored in a relational database and manipulated in Java. Consequently, all such values are transformed to `null` in both Java and our database.

The reason I decided to retain these records is as follows: Our client might not be interested in the exact price but may want to know which partner is offering a particular accommodation. This allows the client to manually check the price by contacting the partner or by visiting their website for the latest information. Therefore, the following response is acceptable to me. It shows the exact price from one partner, while indicating that the client should contact the second partner manually for pricing information:
```json
[
    {
        "partnerId": 100,
        "prices": [
            {
                "currency": "EUR",
                "price": 100.67
            }
        ]
    },
    {
        "partnerId": 200,
        "prices": [
            {
                "currency": "EUR",
                "price": null
            }
        ]
    }
]
```


## Data Layer
For simplicity and ease of integration, an H2 database was used to store the cleaned content of the provided files. This lightweight, in-memory database is well-suited for the purposes of this project, allowing for quick setup and straightforward interaction with the data.

### Database Information
The H2 database can be accessed via a web interface at the following URL:
* URL: http://localhost:8080/h2-console/
* Username: `sa`
* Password: `sa`

###  Domain Entities
Upon investigating the file, I noticed two types of objects have identifiers: accommodation and partner. Therefore, they are annotated with `@Entity` in the Spring Boot project. The `Price` object is an embedded list within the `Accommodation` class, reflecting the fact that each accommodation can have multiple prices in different currencies.

*  **Accommodation Entity:** The Accommodation class represents the accommodations, with a composite key consisting of id and partnerId. It includes an embedded list of Price objects. Using a composite key for the Accommodation entity is essential in this context because each accommodation is identified not only by its own unique identifier (id) but also by the partner offering it (partnerId). This is important for several reasons:
    *  **Uniqueness Across Partners:** Each partner may offer the same accommodation at different prices. By using a composite key, we can uniquely identify an accommodation from a specific partner. For example, Accommodation ID 17986 offered by Partner 100 is distinct from Accommodation ID 17986 offered by Partner 200.
    *  **Data Integrity:** The composite key ensures that each record in the `Accommodation` table is unique and prevents duplicate entries for the same accommodation from the same partner. This prevents data inconsistencies where the same accommodation might appear multiple times with conflicting data.
    *  **Query Efficiency:** When querying for accommodations from a specific partner, the composite key allows for more efficient lookups. This is especially important in a relational database where join operations and index usage can significantly benefit from a well-defined composite key.
    *  **Relationship Management:** The composite key helps in managing relationships between entities. In this case, the `Price` entity is linked to the `Accommodation` through the composite key, ensuring that each price entry is correctly associated with the appropriate accommodation and partner combination.
  
*  **Partner Entity:** The `Partner` class represents the partners providing accommodation data. It has a one-to-many relationship with `Accommodation`.

*  **Price Embeddable:** The `Price` class represents the price details for accommodations. It is embedded within the `Accommodation` class.


## Error Handling
To ensure all errors generated by our Spring Boot application follow a consistent format, I implemented the `HttpErrorResponse` class. This class is designed to standardize the error response format across the application. Additionally, an exception dispatcher class, `ExceptionDispatcher`, is used to handle exceptions and map them to appropriate HTTP responses.

In this application, when a client sends an accommodation ID that does not exist, an `AccommodationNotFoundException` is thrown. Although it is generally recommended to declare possible exceptions in the method signature, this task required not modifying the method signature. Therefore, `AccommodationNotFoundException` extends `RuntimeException`.

**HttpErrorResponse Class:** The HttpErrorResponse class defines the structure of the error response. It includes a timestamp, HTTP status, and a message, ensuring that all error responses have a uniform format.
**ExceptionDispatcher Class:** The `ExceptionDispatcher` class is annotated with `@RestControllerAdvice` and implements `ErrorController` to handle exceptions globally across the application. This class catches specific exceptions and returns a structured `HttpErrorResponse` object.

### Benefits of Centralized Error Handling Approach

1.  **Consistent Error Responses**: Predictable error format for client handling.
2.  **Centralized Exception Management**: Reduced duplicate code, easier maintenance.
3.  **Improved Logging**: Uniform error information in logs for better troubleshooting.
4.  **Separation of Concerns**: Cleaner code with distinct business logic and error handling.
5.  **Scalability**: Easy to extend with new exception handling methods.
6.  **Customization and Flexibility**: Tailor error messages and formats as needed.
7.  **Enhanced Security**: Prevent sensitive information exposure in error messages.
8.  **User-Friendly Error Messages**: Help users understand and resolve issues.
9.  **Global Exception Handling**: Gracefully handle unexpected exceptions.


# Questions
### How could a partner with a potentially slow REST interface be integrated?
To integrate a partner with a potentially slow REST interface in a Spring Boot application, several strategies can be employed to ensure the service remains responsive and performant. One effective approach is to use asynchronous REST calls. By utilizing Spring's `@Async` annotation, asynchronous calls to the partner's REST API can be made, allowing the main thread to remain unblocked. This approach enables other processes to continue running while waiting for the response, improving the overall efficiency of the application.

Another robust method is reactive programming with Spring WebFlux. WebFlux enables the creation of non-blocking, reactive REST calls by leveraging the Reactor project. This approach is particularly beneficial for handling slow or unpredictable responses from external services. Among all available methods, I prefer reactive programming because it simplifies asynchronous processing and includes features to cancel or drop connections after a certain amount of time, providing better control over resource management and timeout handling.


###  How could your solution scale for multiple thousand requests per second?
To scale a conventional Spring Boot application with a relational SQL database to handle multiple thousand requests per second, it is crucial to focus on several strategies across application architecture, database optimization, and infrastructure scaling.

Application architecture plays a fundamental role in handling high loads. One effective approach is to adopt asynchronous processing using Spring's `@Async` annotation or reactive programming with Spring WebFlux. This approach ensures that the application can manage a high number of concurrent requests without blocking threads, thereby improving responsiveness and throughput.

Optimizing database queries and ensuring appropriate indexing on frequently queried columns can significantly reduce query execution time and improve overall database performance. Furthermore, implementing caching mechanisms such as Redis or Memcached to store frequently accessed data in memory reduces the load on the database and speeds up response times for repeated requests. This approach is particularly beneficial for read-heavy applications where the same data is requested multiple times.

Leveraging cloud infrastructure services such as AWS, Azure, or Google Cloud can provide scalable and reliable infrastructure. These cloud platforms offer managed services for databases, caching, and load balancing, which can be easily adjusted based on the application's needs. Using managed services reduces the operational overhead and allows the development team to focus more on application logic rather than infrastructure management.
