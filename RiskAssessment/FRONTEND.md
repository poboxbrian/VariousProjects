Frontend Challenge
==================

The frontend application should collect user data and serialize it into
a JSON payload to be sent to the backend, which is the responsibility of
a different (imaginary) team.

The interface will be used in a home, rather than clinical, setting. The
workflow looks something like this:

1.  A patient schedules an appointment.
2.  The clinician sends the patient a link to the application.
3.  The user accesses the application. (This is where your responsiblity
    begins.)
4.  The user interacts with the application until all the necessary data
    is collected.
5.  The application sends the data to the backend. See the discussion on
    mocking below.
6.  The user closes the application. (This is where your responsibility
    ends.)
7.  The clinician gets the results and is prepared for the user's
    appointment.

That is to say, a user accessing your application knows they are being
evaluated for cancer risk, but knows nothing about the process and is
not necessarily a technically skilled person. Your interface should
guide them through entering all the necessary data, and should be
accessible to anyone who knows how to use a computer at any level.

Criteria
--------

We will evaluate your solution on three criteria:

1.  *Accessibility*. This frontend is meant to be friendly to end-users
    and should guide them through the process of entering their data in
    an intuitive way. Consider that some patients may have disabilities,
    speak English as a second language, or feel uncomfortable with
    technology.
2.  *Simplicity*. Our needs evolve rapidly, so we prefer software that
    is easy to read and refactor.
3.  *Correctness*. You should have a high level of confidence that your
    software is correct, and that it will continue to be correct when
    changed to meet new requirements, and you should be ready to justify
    that confidence.

The JSON schema in `schemas/input_schema.md` represents the required
data. Perhaps the most complex part of the schema is the list of family
members; consider that part of your interface carefully.

Your application should collect the following data:

-   Patient name, first and last
-   Patient date of birth
-   Patient sex (at birth)
-   Any diagnoses of cancer, and the ages at which they occurred
-   The patient's family information, including:
    -   Age of each family member
    -   Sex of each family member
    -   Any diagnoses of cancer, and the ages at which they occurred,
        for each family member
    -   The relationship of each family member to the patient

A major component of the correctness criterion for this challenge is
ensuring that your solution emits only data that conforms to the schema.
In addition, it should validate data, and assuming the input data passes
your validations, the output should represent all user-entered data
correctly.

Additional Considerations
-------------------------

1.  Think carefully about how to handle unknowns and optional fields.
    The risk model can operate with a reasonable degree of uncertainty,
    but some fields are absolutely required.
2.  Recording information on family members is likely to be more
    difficult than data on the patient. Try not to overthink this part;
    the data that need to be collected are very specific.
3.  You won't be building the backend of this system, but if it were to
    be implemented, it would send data back that conformed to the
    `schemas/output_schema.json`.

Example
-------

If a user were to input the data for John CancerIQ, born March 25, 1950;
diagnosed with brain cancer at an unknown age; whose mother was
diagnosed with breast cancer at age 50; and whose father and paternal
grandmother unaffected by cancer; your code should produce the following
payload (this is `examples/input/patient_03.json`):

```json
{
    "id": "abc123",
    "motherId": "mama",
    "fatherId": "papa",
    "firstName": "John",
    "lastName": "CancerIQ",
    "gender": "male",
    "dateOfBirth": "1950-03-25",
    "cancers": [{
        "cancerType": "brain"
    }],
    "family": [{
            "id": "papa",
            "motherId": "granny",
            "fatherId": null,
            "relationship": "father",
            "gender": "male"
        },
        {
            "id": "mama",
            "age": 77,
            "motherId": null,
            "fatherId": null,
            "relationship": "mother",
            "gender": "female",
            "cancers": [{
                "cancerType": "breast",
                "ageOfDiagnosis": 50
            }]
        },
        {
            "id": "granny",
            "motherId": null,
            "fatherId": null,
            "relationship": "paternal grandmother",
            "gender": "female"
        }
    ]
}
```

Note that since IDs are purely referential, there is no need to name
them according to relationship. We do so in the examples to make
debugging easier.

Dependencies
------------

The frontend is expected to send data to the backend. You aren't
responsible for writing the backend, but you should set up and document
a method by which the (fictional) engineers responsible for building
that portion of the system would recieve data from your frontend and
configure your system to connect to the backend server.
