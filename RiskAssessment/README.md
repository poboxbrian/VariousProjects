CancerIQ Coding Challenge
=========================

The CancerIQ coding challenge is one of the final steps in the CancerIQ
engineering hiring process. Congratulations on making it this far!

This challenge allows you to work on some of the problems that we had to
solve for a few of our current product offerings. It is split into three
parts, of which we'd like you to complete **one**.

-   Frontend, the application's user interface
-   Backend, the application's data-processing component
-   Algorithms, the more purely mathematical aspect of the application

Read the scenario below and pick one of the three components that you
think best highlights your unique skillset. Build that component and
submit a merge request to this repository.

Scenario
--------

CancerIQ's software, among other things, calculates the cancer risk for
patients based on their medical history and the medical histories of
their family members. If the patient's history shows that they are at
elevated risk, they may be eligible for genetic testing (by sending a
sample of their DNA to a laboratory).

Our software includes questionnaires that patients fill out about their
medical history and the medical histories of their family members. The
software also implements algorithms described in papers and other
documents written by medical researchers. We then apply the algorithms,
called "risk models," to the history captured by the questionnaires. If
the patient meets the criteria of the risk model, we alert their
provider that they may qualify for testing.

Your challenge is to write part of a small application to calculate and
display data about a patient's risk. Imagine that two other engineers
are working on the project as well, each implementing one of the other
components of the challenge.

The article at `/article/article.pdf` spells out the algorithm at a high
level, (loosely inspired by the actual guidelines published by the
NCCN). `/schemas/input_schema.json` represents the patient's data, as
provided by the frontend, and `/schemas/output_schema.json` represents
the results of the risk analysis. Validators for the output schema are
provided `/validators/output_validator_{linux,mac}`.

To use the JSON schema, you can either use an application like
[`AJV`](https://github.com/epoberezkin/ajv), or you can use an [online
validator](https://www.jsonschemavalidator.net/) (just paste the schema
into the schema pane, then put the JSON you want to validate in the
other pane).

Requirements
------------

1.  You must open a merge request against this repository with your
    solution. You'll find an issue assigned to you with instructions on
    how to proceed.
2.  You can write your solution in any language.
3.  Your project must include a README giving the instructions necessary
    to run your application. You can expect that a person with coding /
    DevOps knowledge will set up the application on a server or will
    launch the application.

Timing / what we review
-----------------------

We think a good coding challenge should have some easy stuff and some
hard stuff. We also know that different people have different amounts of
time to spend. Addressing everything in this challenge would probably
take several days. If you don't have that kind of time, it is very
helpful for you to show us that you have spotted the complications and
explain to us how you would handle them if you had some more time to
spend.

As mentioned, we build client-facing web applications, so we're looking
for skills relevant to that. We like code that's readable and
well-tested. We have neither the performance requirements nor the
resources of companies like Netflix or Google, so if there's a tradeoff
between readability and wringing out the last iota of performance, go
with readability (though an exquisitely readable search algorithm that
runs in factorial time isn't ideal).

Git and Gitlab are critical to our everyday operations, so we have
included those technologies in the coding challenge. We expect that you
can follow requirement \#1 and open a merge request so that we can
review your submission. This allows us to communicate with you on a line
by line basis, evaluate minimum working knowledge of git, and gives you
a platform to ask questions. If you get stuck we suggest you first read
[Gitlab basics](https://docs.gitlab.com/ce/gitlab-basics/README.html)
before asking us any git-related questions in the issue.

Choose A Challenge
------------------

[Frontend](FRONTEND.md) \| [Backend](BACKEND.md) \|
[Algorithms](ALGORITHMS.md)

Example
-------

Consider the example file `patient_03.json`:

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

The first set of criteria require the patient to have a Smith Syndrome
cancer, defined in the footnote. Here, the patient has brain cancer,
which counts. We can't say for certain that this cancer was diagnosed at
or before age 60, so the first criterion is out. The person doesn't have
two cancers, so the second criterion is out. But the patient's
mother---a first-degree relative---was diagnosed with a Smith cancer at
age 50, so the person just qualifies for the third criterion.

We continue to see if there are any other reasons this patient
qualifies. The patient only has one relative with cancer, so the fourth
criterion is out. Now we encounter the criteria that apply if the person
*does not* have cancer, so this entire set is out.

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

Let's wrap up this example: the last criterion compels us to consider
all the other criteria again, this time standing in the shoes of each of
the patient's first- and second-degree relatives. Hint: this is one of
the hardest parts of the challenge, and it's something you should keep
in mind from the very beginning as you consider how you'll structure
your solution. If you have difficulty implementing this part, you should
at least address in your README possible paths to a solution. In this
case, when we stand in the mother's shoes, we see that she meets the
first criterion: Smith Cancer diagnosed by age 60. If you look at the
JSON schema for the output data, you'll see all the whitelisted strings
you should use in your output. In this case, it's "First-degree
relative: An individual with a Smith Syndrome cancer diagnosed at or
before age 60"

So the output looks like this:

```json
{
    "meetsCriteria": true,
    "reasons": ["First-degree relative: An individual with a Smith Syndrome cancer diagnosed at or before age 60", "An individual with a Smith Syndrome cancer and one or more first-degree relatives with a Smith Syndrome cancer diagnosed at or before age 50"]
}
```
