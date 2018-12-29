Backend Challenge
=================

The backend service is responsible for taking data provided by the
frontend and performing the risk calculations. The algorithm is spelled
out at a high level in `/article/article.pdf` (loosely inspired by the
actual guidelines published by the NCCN).

You are **not** expected to implement the final criterion (“A close
relative meeting any of the above criteria.”). This is part of the
[algorithms challenge](ALGORITHMS.md).

We provided schemas in `/schemas`. `/schemas/input_schema.json`
represents the patient’s data, as provided by the frontend, and
`/schemas/output_schema.json` represents the results of the risk
analysis. We also provided validators for the output schema in
`/validators/output_validator_{linux,mac}`.

To use the JSON schema, you can either use an application like
[`AJV`](https://github.com/epoberezkin/ajv), or you can use an [online
validator](https://www.jsonschemavalidator.net/) (just paste the schema
into the schema pane, then put the JSON you want to validate in the
other pane).

Criteria
--------

We will evaluate your solution on three criteria:

1.  *Correctness*. You should be very confident that your code correctly
    implements the risk analysis algorithm. After all, health decisions
    could be made based on this code's output.
2.  *Simplicity*. This is important for two reasons. First, if bugs are
    found in the system, it needs to be easy to understand and modify.
    Second, new guidelines come out as time goes on, so the program
    needs to be able to adapt to them. The easier it is to understand,
    the easier it is to change in the future.
3.  *Integration*. Your code needs accept and produce data following the
    schemas provided. If any special conditions apply, you should
    document appropriately.

Additional Considerations
-------------------------

1.  Do we know anything about age of diagnosis even if it's not
    specified?

We threw in a small amount of medical jargon to give a sense of what
it's like to work in our domain---terms like "first-degree relative" and
"metachronous cancer." They should be easily Google-able. ("Male breast
cancer" is simply breast cancer in a male relative.)

Example
-------

Consider the example file `examples/input/patient_02.json`:

```json
{
    "id": "abc123",
    "firstName": "John",
    "lastName": "CancerIQ",
    "gender": "male",
    "dateOfBirth": "1950-03-25",
    "cancers": [{
        "cancerType": "brain",
        "ageOfDiagnosis": 54
    }],
    "motherId": null,
    "fatherId": null
}
```

The first set of criteria require the patient to have a Smith Syndrome
cancer, defined in the footnote. Here, the patient has brain cancer,
which counts. It was also diagnosed before the age of 60, so this person
meets the first criterion. We continue to see if there are any other
reasons this patient qualifies.

The person doesn't have two cancers, so the second criterion is out. The
patient only has one relative with cancer, so the fourth criterion is
out. Now we encounter the criteria that apply if the person *does not*
have cancer, so this entire set is out.

If you're reading this skeptically, this might bring up an issue of
interpretation: wouldn't it be more conservative to include these
criteria *regardless* of the patient's cancer status---after all, if
it's dangerous to have relatives with cancer when the patient *doesn't*
have cancer, isn't it *even more dangerous* if the patient has cancer?
If you run into implementation questions like this, make a note of them
in the README (if and only if you're completely blocked in you
implementation, feel free to reach out for clarification). In this
particular case, you can safely ignore those criteria if the patient
doesn't have cancer; this is an artifact of the coding challenge (which
is based on real criteria, but with things left out or moved around).
The real risk models raise interpretive questions like this, too, but
fortunately we can get in touch with subject-matter experts when we get
stuck.

In any case, the correct output (as shown in
`examples/output/patient_02.json`) is:

```json
{
    "meetsCriteria": true,
    "reasons": ["An individual with a Smith Syndrome cancer diagnosed at or before age 60"]
}
```

Dependencies
------------

Since you are not responsible for the frontend data collection or the
computations needed to check criteria for the patient's family members,
you can mock these components. Just make sure to document how the the
(fictional) engineers building the front-end and algorithmic components
would hook up their code to yours.

This may be more difficult in evaluating family members, but try to at
least describe an interface that your code could use to evaluate the
same risk criteria on family members. Would your code call another
service or program to filter family members by criteria and return
matching IDs? Maybe it would better for that code to call your risk
analysis code and aggregate the results?

Whatever you decide, make sure to document it well and design your code
with integration in mind.
