Algorithms Challenge
====================

This aspect of the coding challenge is somewhat more abstract than the
others, but it is based on the same scenario. The algorithm discussed in
`/article/article.pdf` (loosely inspired by the actual guidelines
published by the NCCN) has a number of criteria, which are applied to
individual patients to determine their risk for specific cancers.

The final criterion, which the [backend challenge](BACKEND.md) does not
have to implement, is to apply all of the other criteria to the
patient's family members.

As it turns out, this is something of a thorny problem. Your task is to
implement a generic method of evaluating risk criteria for any family
member and reporting which family member, if any, met the criteria.

You do not need to implement the other criteria. Rather, you should
implement a solution that will serve as a library for the developers
implementing the other criteria to filter by degree of relatedness. Your
solution could return data summarizing all relationships within the
family, or it could receive the desired relationship degrees as
parameters, returning only the IDs that match.

Also consider in your design other data that might be useful to filter
by—for example, side of the family, specific relationships, cancer types
and ages of diagnosis, etc. You do not need to implement those
capabilities as part of your solution, but imagine that the product team
told you that those features would be useful in future iterations.

Feel free to design any interface you want for the criteria, including
using higher-order functions or any feature of the language you use.
Just make sure to document it in your README.

Criteria
--------

Your solution will be evaluated on three criteria:

1.  *Correctness*. You should be very confident that your code correctly
    analyzes family structure and checks given criteria for each family
    member.
2.  *Simplicity*. Your code should be readable and modifiable. Consider
    that changes to criteria might require flexibility in filtering
    family members in new ways. Make sure your code could be changed to
    support this.
3.  *Complexity* (i.e. scaling). Your code should run in a reasonable
    amount of time, even for very large families. Approximate the
    worst-case and average-case complexity, if possible, and include
    justification in your README.

Additional Considerations
-------------------------

1.  What would you do if the names of the relatives—for example,
    paternal grandfather—were removed from the patient data?
2.  If you are invited to dinner with a friend and two of her paternal
    first cousins (about whom you know nothing else), do you know enough
    to say how the cousins are related to one another?

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

The first set of criteria just need to be evaluated on the patient. They
include criteria for the patient’s history as well as for first- and
second-degree relatives of the patient.

The last criterion compels us to consider all the other criteria again,
this time standing in the shoes of each of the patient’s first- and
second-degree relatives. That is, we should do the same computations for
each of these family members, rerooting the family tree on each family
member in turn.

For instance, in this case, we look at the patient:

```text
granny (breast, 50)
  |
  |
  +---+
      |
     papa ---|--- mama
             |
             |
           abc123 (brain)
```

In this case, `abc123` is the patient under examination, `mama` and
`papa` are first-degree relatives, and `granny` is a second-degree
relative.

We also need to “stand in the shoes” of each of these people and
evaluate the given criteria. For instance, for the father:

```text
granny (breast, 50)
   |
   |
   +------+
          |
         papa ---+
                 |
              abc123 (brain)
```

For the father, `granny` and `abc123` are first-degree relatives. Take a
look
[here](https://en.wikipedia.org/wiki/Coefficient_of_relationship#Human_relationships)
for more on relationship degrees. Notice how the coefficient of
relationship decreases with each generation traversed through the family
tree. Note, as well, that full siblings are related through *both*
parents. You don’t need to pay attention to the obscure relationships;
we don’t support things like consanguinity / cousin marriage / incest.
Our family trees don’t have cycles. So ¾ siblings and quadruple second
cousins aren’t something you need to worry about.
