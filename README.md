# Articles

## Task

Java Spring Boot RESTful service. Implemented simple add, update, retrieve operations on custom database.

## Getting Started

### Prerequisites

- IDE: IntelliJ IDEA
- Java: JDK 11
- Build tool: Maven 3.1.0

### Dependencies

- Spring Boot 2.2.6
- Mockito 3.0.0

## Endpoints

> GET http://{localhost}/api/v1/categories/top-three

> POST http://{localhost}/api/v1/articles

> PUT http://{localhost}/api/v1/articles

## Examples
```
POST http://{localhost}/api/v1/articles

Body:

{
    "userID": "user",
    "articleDtos": [
        {
            "title": "Article 100",
            "category": "Sport",
            "like": true
        },
        {
            "title": "Article 101",
            "category": "Science",
            "like": true
        },
        {
            "title": "Article 102",
            "category": "Science",
            "like": true
        },
        {
            "title": "Article 103",
            "category": "Sport",
            "like": true
        }
    ]
}
```

```
PUT http://{localhost}/api/v1/articles

Body:

{
    "userID": "user",
    "articleDtos": [
        {
            "title": "Article 100",
            "category": "Other",
            "like": true
        },
        {
            "title": "Article 101",
            "category": "Other",
            "like": true
        },
        {
            "title": "Article 102",
            "category": "Other",
            "like": true
        },
        {
            "title": "Article 103",
            "category": "Other",
            "like": true
        }
    ]
}
```

```
GET http://{localhost}/api/v1/categories/top-three

Query Params:
userId:user
```

## Requirements

- More than 3 categories should be added in order to retrieve the three best.

### Quickstart

- Run "ArticlesApp" class
- Test it with PostMan following examples above

### Tests

REST API endpoint tests are located in "ArticleTest" class.

## Author

[![Ivan Parnarev Linkedin](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/ivan-parnarev/)
