# Java Programming Exam
### _Orvyl F. Tumaneng_

Take home exam for Delivery Engineering Chapter Lead application.

## Repository
- [Github]

## Tech

- Spring Boot
- Drools (as rules engine)
- Cucumber (for testing)

## API

Without voucher
```sh
POST /parcel/cost
{
    "weight_kg": 40.8,
    "height_cm": 4,
    "width_cm": 4,
    "length_cm": 7
}

200 OK
{
    "type": "Heavy Parcel",
    "cost": 816.0,
    "volume": 112.0
}
```

With voucher (Discounted)
_NOTE: Unable to connect in the vaoucher service provided, hence, I prepared a simple DTO int he code representing retreived voucher_
```sh
POST /parcel/cost?voucher-code=mynt
{
    "weight_kg": 40.8,
    "height_cm": 4,
    "width_cm": 4,
    "length_cm": 7
}

200 OK
{
    "type": "Heavy Parcel",
    "cost": 815.0,
    "volume": 112.0,
    "voucher": {
        "code": "mynt",
        "discount": 1.0,
        "expiry": "2024-01-19"
    }
}
```

[Github]: <https://github.com/orvyl/java-programming-exam>
