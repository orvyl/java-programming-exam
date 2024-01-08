Feature: Compute Parcel Fee

  Scenario Outline: Client makes call to compute parcel service
    When a valid request sent with voucher "<voucher>"
      |weight_kg    |height_cm    |width_cm   |length_cm    |
      |<weight_kg>  |<height_cm>  |<width_cm> |<length_cm>  |
    Then it should compute correctly with type "<type>" and cost <cost>
    Examples:
      |weight_kg  |height_cm  |width_cm |length_cm  |voucher  |type             |cost   |
      |40         |4          |4        |7          |         |Heavy Parcel     |800.0  |
      |40         |4          |4        |7          |MYNT     |Heavy Parcel     |799.0  |
      |90         |4          |4        |7          |MYNT     |Rejected Parcel  |0.0    |